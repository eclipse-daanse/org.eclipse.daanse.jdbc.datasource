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
 *   SmartCity Jena - initial
 */
package org.eclipse.daanse.jdbc.datasource.testkit.derby;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.sql.DataSource;

import org.apache.derby.jdbc.EmbeddedDataSource;
import org.eclipse.daanse.jdbc.datasource.testkit.api.ActiveDatabase;
import org.eclipse.daanse.jdbc.datasource.testkit.api.DatabaseProvider;
import org.eclipse.daanse.sql.dialect.api.Dialect;
import org.eclipse.daanse.sql.dialect.api.DialectInitData;
import org.eclipse.daanse.sql.dialect.db.derby.DerbyDialect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Embedded Apache Derby, in-memory. {@link #activate(String)} returns one isolated database per distinct key
 * ({@code memory:daanse-<UUID>}), as H2 does: with one database for every key, the second fixture found the tables of
 * the first ("Table/View 'sales_fact_1997' already exists", database run 20.10.). An in-memory database lives for the
 * whole JVM regardless of open connections.
 */
public class DerbyDatabaseProvider implements DatabaseProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(DerbyDatabaseProvider.class);

    private static final String DEFAULT_KEY = "__default__";

    private final ConcurrentMap<String, ActiveDatabase> dbsByKey = new ConcurrentHashMap<>();

    @Override
    public String id() {
        return "derby";
    }

    @Override
    public void close() {
        for (String key : dbsByKey.keySet()) {
            close(key);
        }
    }

    /**
     * Closes and drops just {@code key}'s in-memory database, leaving any others under this provider untouched. A
     * no-op if {@code key} was never {@link #activate(String) activate}d, or was already closed.
     *
     * <p>Derby keeps an in-memory database for the whole JVM until it is dropped, its connections closed or not: a
     * caller that opens one database per key fills the heap with every key it ever used unless it gives them back
     * here.
     */
    @Override
    public void close(String key) {
        ActiveDatabase database = dbsByKey.remove(key);
        if (database == null) {
            return;
        }
        try {
            database.connectionPool().close();
        } catch (RuntimeException e) {
            LOGGER.warn("closing a derby connection pool failed", e);
        }
        if (database.dataSource() instanceof EmbeddedDataSource embedded) {
            EmbeddedDataSource drop = new EmbeddedDataSource();
            drop.setDatabaseName(embedded.getDatabaseName());
            drop.setConnectionAttributes("drop=true");
            try (Connection never = drop.getConnection()) {
                // a drop answers with an exception, not with a connection
            } catch (SQLException e) {
                // 08006: the database is dropped
                if (!"08006".equals(e.getSQLState())) {
                    LOGGER.warn("dropping the derby database {} failed", embedded.getDatabaseName(), e);
                }
            }
        }
    }

    /**
     * Derby reads these once, when the engine boots on the first connection, so they have to be
     * in place before {@link EmbeddedDataSource#getConnection()} is called. The page cache
     * defaults to 1000 pages (~4 MB) — FoodMart does not fit, so every aggregate scan walks back
     * through the pager. {@code durability=test} skips the log syncs, which buy nothing for a
     * database that lives and dies with the JVM. A hash join may hold 64 MB of a table instead of
     * 1 MB: below that Derby joins a derived table of some ten thousand rows by nested loops, once
     * per row of the other side -- one join of 60 000 facts to such a table ran past an hour, with
     * the room it took two seconds.
     */
    private static void bootProperties() {
        setIfAbsent("derby.storage.pageCacheSize", "20000");
        setIfAbsent("derby.system.durability", "test");
        setIfAbsent("derby.language.statementCacheSize", "500");
        setIfAbsent("derby.language.maxMemoryPerTable", "65536");
    }

    private static void setIfAbsent(String key, String value) {
        if (System.getProperty(key) == null) {
            System.setProperty(key, value);
        }
    }

    @Override
    public ActiveDatabase activate() {
        return activate(DEFAULT_KEY);
    }

    @Override
    public ActiveDatabase activate(String isolationKey) {
        return dbsByKey.computeIfAbsent(isolationKey, this::newDatabase);
    }

    private ActiveDatabase newDatabase(String key) {
        long tActivate = System.nanoTime();
        bootProperties();
        EmbeddedDataSource dataSource = new EmbeddedDataSource();
        dataSource.setDatabaseName("memory:daanse-" + UUID.randomUUID());
        dataSource.setCreateDatabase("create");
        try (Connection connection = dataSource.getConnection()) {
            Dialect dialect = new DerbyDialect(DialectInitData.fromConnection(connection));
            // embedded: db-ready = the (tiny) connect duration, for comparability with docker DBs
            long ms = (System.nanoTime() - tActivate) / 1_000_000;
            LOGGER.warn("DBTIMING db={} phase=db-ready ms={}", id(), ms);
            LOGGER.warn("DBTIMING db={} phase=context-first ms={} detail=fresh,epoch={}", id(), ms,
                    System.currentTimeMillis() / 1000);
            return new ActiveDatabase(dataSource, dialect, ActiveDatabase.settingsFor(key));
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to start a Derby database for key " + key, e);
        }
    }
}
