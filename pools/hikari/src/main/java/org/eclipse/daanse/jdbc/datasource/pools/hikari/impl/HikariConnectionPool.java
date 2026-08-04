/*
* Copyright (c) 2026 Contributors to the Eclipse Foundation.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
*
* Contributors:
*   Stefan Bischof (bipolis.org) - initial
*/
package org.eclipse.daanse.jdbc.datasource.pools.hikari.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.sql.DataSource;

import org.eclipse.daanse.jdbc.datasource.pools.api.ConnectionPool;
import org.eclipse.daanse.jdbc.datasource.pools.api.PoolSettings;
import org.eclipse.daanse.jdbc.datasource.pools.api.Constants;
import org.eclipse.daanse.jdbc.datasource.pools.hikari.api.ocd.DsConfig;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * A {@link ConnectionPool} backed by HikariCP.
 * <p>
 * HikariCP pools the {@link DataSource} it is handed, rather than going through
 * {@code ConnectionPoolDataSource}/{@code PooledConnection}. That matters here:
 * not every driver fires the close event a {@code PooledConnection}-based pool
 * reclaims on - MariaDB's does not, and a pool built that way leaks a physical
 * connection per checkout. Wrapping the plain DataSource behaves the same on
 * every driver.
 * <p>
 * No {@code @Modified}: a configuration change replaces the component, and the
 * old pool is closed in {@link #deactivate()}.
 */
@Designate(ocd = DsConfig.class, factory = true)
@Component(service = ConnectionPool.class, scope = ServiceScope.SINGLETON, //
        configurationPid = org.eclipse.daanse.jdbc.datasource.pools.hikari.api.Constants.PID_CONNECTION_POOL, //
        property = Constants.POOL_PROPERTY_KIND + "="
                + org.eclipse.daanse.jdbc.datasource.pools.hikari.api.Constants.KIND)
public class HikariConnectionPool implements ConnectionPool {

    private static final Logger LOGGER = LoggerFactory.getLogger(HikariConnectionPool.class);

    private final HikariDataSource pool;
    private final Duration acquireTimeout;
    /** Only used by the deadline-bounded acquire path; virtual threads keep it cheap. */
    private final ExecutorService acquireExecutor = Executors.newVirtualThreadPerTaskExecutor();

    /**
     * The Declarative Services entry point. Configuration Admin hands its
     * properties as a map, and this is the only place that has to know their
     * names.
     */
    @Activate
    public HikariConnectionPool(@Reference DataSource dataSource, Map<String, Object> config) {
        this(dataSource, PoolSettings.from(config, LOGGER::warn));
    }

    public HikariConnectionPool(DataSource dataSource, PoolSettings settings) {
        HikariConfig cfg = new HikariConfig();
        cfg.setDataSource(dataSource);
        cfg.setPoolName(settings.poolName().isBlank() ? "daanse-hikari" : settings.poolName());
        cfg.setMaximumPoolSize(settings.maximumPoolSize());
        cfg.setMinimumIdle(settings.minimumIdle());
        cfg.setConnectionTimeout(settings.connectionTimeout().toMillis());
        cfg.setIdleTimeout(settings.idleTimeout().toMillis());
        cfg.setMaxLifetime(settings.maxLifetime().toMillis());
        // Reports a connection held longer than this; unlike dbcp2 hikari never takes
        // it back, so this is a warning about a caller that forgot to close, nothing more.
        cfg.setLeakDetectionThreshold(settings.leakThreshold().toMillis());

        this.acquireTimeout = Duration.ofMillis(cfg.getConnectionTimeout());
        this.pool = new HikariDataSource(cfg);
        LOGGER.info("pool {} up: max={} idleMin={} acquireTimeout={}ms", cfg.getPoolName(), cfg.getMaximumPoolSize(),
                cfg.getMinimumIdle(), cfg.getConnectionTimeout());
    }

    @Deactivate
    public void deactivate() {
        close();
    }

    @Override
    public void close() {
        acquireExecutor.shutdownNow();
        pool.close();
    }

    @Override
    public Connection getConnection() throws SQLException {
        return pool.getConnection();
    }

    @Override
    public Connection getConnection(Duration timeout) throws SQLException {
        if (timeout == null || timeout.compareTo(acquireTimeout) >= 0) {
            return pool.getConnection();
        }
        // Hikari fixes its wait at construction time and offers no per-call deadline,
        // so wait on another thread and give up on schedule here. A connection that
        // turns up after we gave up is closed rather than leaked.
        CompletableFuture<Connection> pending = CompletableFuture.supplyAsync(() -> {
            try {
                return pool.getConnection();
            } catch (SQLException e) {
                throw new CompletionException(e);
            }
        }, acquireExecutor);
        try {
            return pending.get(timeout.toMillis(), TimeUnit.MILLISECONDS);
        } catch (TimeoutException e) {
            pending.thenAccept(HikariConnectionPool::closeQuietly);
            throw new SQLException("no pooled connection within the caller's budget of " + timeout, e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            pending.thenAccept(HikariConnectionPool::closeQuietly);
            throw new SQLException("interrupted while waiting for a pooled connection", e);
        } catch (ExecutionException e) {
            Throwable cause = e.getCause();
            throw cause instanceof SQLException sql ? sql : new SQLException(cause);
        }
    }

    private static void closeQuietly(Connection connection) {
        if (connection == null) {
            return;
        }
        try {
            connection.close();
        } catch (SQLException e) {
            LOGGER.warn("could not return a connection that arrived after its caller gave up", e);
        }
    }

    @Override
    public DataSource dataSource() {
        return pool;
    }

    @Override
    public Duration acquireTimeout() {
        return acquireTimeout;
    }

    @Override
    public int maxSize() {
        return pool.getMaximumPoolSize();
    }

    @Override
    public int activeSize() {
        return pool.getHikariPoolMXBean() == null ? 0 : pool.getHikariPoolMXBean().getActiveConnections();
    }



}
