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
 *   SmartCity Jena, Stefan Bischof - initial
 */
package org.eclipse.daanse.jdbc.datasource.testkit.mariadb;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Consumer;

import org.eclipse.daanse.jdbc.datasource.testkit.api.ActiveDatabase;
import org.eclipse.daanse.jdbc.datasource.testkit.api.DatabaseProvider;
import org.eclipse.daanse.sql.dialect.api.Dialect;
import org.eclipse.daanse.sql.dialect.api.DialectInitData;
import org.eclipse.daanse.sql.dialect.db.mariadb.MariaDBDialect;
import org.mariadb.jdbc.MariaDbDataSource;

import com.github.dockerjava.api.command.CreateContainerCmd;

import org.testcontainers.containers.MariaDBContainer;

/**
 * MariaDB provider backed by a shared Testcontainers container.
 * {@link #activate(String)} returns per-key DATABASE-isolated
 * {@link ActiveDatabase}.
 */
public class MariaDbDatabaseProvider implements DatabaseProvider {

    /** Follows the current long-term release rather than pinning a version. */
    private static final String IMAGE = "mariadb:lts";

    /**
     * Replaces the {@code my.cnf} Testcontainers mounts; see that file for what
     * it sets.
     */
    private static final String CONFIG_OVERRIDE = "daanse-mariadb-conf";

    /**
     * The container's memory ceiling. MariaDB has no
     * {@code innodb_dedicated_server}, so the buffer pool is stated in the
     * configuration file as 62.5 % of this — keep the two in step.
     */
    private static final long MEMORY_LIMIT = 4L << 30;

    private static final String DEFAULT_KEY = "__default__";

    private static volatile MariaDBContainer<?> container;
    private static final Object LOCK = new Object();

    private final ConcurrentMap<String, ActiveDatabase> dbsByKey = new ConcurrentHashMap<>();

    @Override
    public String id() {
        return "mariadb";
    }

    @Override
    public ActiveDatabase activate() {
        return activate(DEFAULT_KEY);
    }

    @Override
    public ActiveDatabase activate(String isolationKey) {
        return dbsByKey.computeIfAbsent(isolationKey, this::newDatabaseForKey);
    }

    private ActiveDatabase newDatabaseForKey(String key) {
        MariaDBContainer<?> c = sharedContainer();
        String dbName = sanitize(key);
        try (Connection admin = openAdmin(c); Statement st = admin.createStatement()) {
            st.execute("CREATE DATABASE IF NOT EXISTS `" + dbName + "`");
            // The container's own user has rights on the container's own database
            // only. Granted here per database, and globally besides, so that a
            // consumer can CREATE SCHEMA - a database on this server - of its own.
            st.execute("GRANT ALL PRIVILEGES ON `" + dbName + "`.* TO '" + c.getUsername() + "'@'%'");
            st.execute("GRANT ALL PRIVILEGES ON *.* TO '" + c.getUsername() + "'@'%' WITH GRANT OPTION");
            st.execute("FLUSH PRIVILEGES");
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to create MariaDB database " + dbName, e);
        }
        try {
            MariaDbDataSource ds = new MariaDbDataSource();
            ds.setUrl(jdbcUrlWithDb(c, dbName));
            ds.setUser(c.getUsername());
            ds.setPassword(c.getPassword());
            Dialect dialect;
            try (Connection conn = ds.getConnection()) {
                dialect = new MariaDBDialect(DialectInitData.fromConnection(conn));
            }
            return new ActiveDatabase(ds, dialect, ActiveDatabase.settingsFor(key));
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to build MariaDB dialect for key " + key, e);
        }
    }

    /**
     * Connects as root: creating a database and granting rights on it is beyond
     * what the container's own user may do. Testcontainers gives root the same
     * password as that user.
     */
    private static Connection openAdmin(MariaDBContainer<?> c) throws SQLException {
        MariaDbDataSource admin = new MariaDbDataSource();
        admin.setUrl(c.getJdbcUrl());
        admin.setUser("root");
        admin.setPassword(c.getPassword());
        return admin.getConnection();
    }

    private static String jdbcUrlWithDb(MariaDBContainer<?> c, String dbName) {
        String url = c.getJdbcUrl();
        int q = url.indexOf('?');
        String base = q == -1 ? url : url.substring(0, q);
        String tail = q == -1 ? "" : url.substring(q);
        int slash = base.lastIndexOf('/');
        if (slash > "jdbc:mariadb://".length()) base = base.substring(0, slash + 1) + dbName;
        return base + tail;
    }

    private static String sanitize(String k) {
        StringBuilder sb = new StringBuilder();
        for (char ch : k.toCharArray()) {
            sb.append(Character.isLetterOrDigit(ch) || ch == '_' ? ch : '_');
        }
        String s = sb.toString();
        return s.length() > 64 ? s.substring(0, 64) : s;
    }

    /** Typed here because a lambda on the raw {@link MariaDBContainer} would be erased. */
    private static Consumer<CreateContainerCmd> memoryLimit() {
        return cmd -> cmd.getHostConfig().withMemory(MEMORY_LIMIT).withMemorySwap(MEMORY_LIMIT);
    }

    @SuppressWarnings("resource")
    private static MariaDBContainer<?> sharedContainer() {
        MariaDBContainer<?> c = container;
        if (c != null) {
            return c;
        }
        synchronized (LOCK) {
            if (container == null) {
                @SuppressWarnings("rawtypes")
                MariaDBContainer m = new MariaDBContainer(IMAGE).withDatabaseName("rt").withUsername("rt")
                        .withPassword("rt");
                m.withConfigurationOverride(CONFIG_OVERRIDE);
                m.withCreateContainerCmdModifier(memoryLimit());
                m.start();
                container = m;
                Runtime.getRuntime().addShutdownHook(new Thread(m::close, "daanse-mariadb-stop"));
            }
            return container;
        }
    }
}
