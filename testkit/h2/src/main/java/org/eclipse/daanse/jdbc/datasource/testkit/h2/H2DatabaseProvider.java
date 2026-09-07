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
package org.eclipse.daanse.jdbc.datasource.testkit.h2;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.eclipse.daanse.jdbc.datasource.testkit.api.ActiveDatabase;
import org.eclipse.daanse.jdbc.datasource.testkit.api.DatabaseProvider;
import org.eclipse.daanse.sql.dialect.api.Dialect;
import org.eclipse.daanse.sql.dialect.api.DialectInitData;
import org.eclipse.daanse.sql.dialect.db.h2.H2Dialect;
import org.h2.jdbcx.JdbcDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * H2 in-memory provider. {@link #activate()} returns the provider's single
 * default database. {@link #activate(String)} returns one isolated database
 * per distinct key — backed by independent in-memory UUID URLs.
 */
public class H2DatabaseProvider implements DatabaseProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(H2DatabaseProvider.class);

    private static final String DEFAULT_KEY = "__default__";

    private final ConcurrentMap<String, ActiveDatabase> dbsByKey = new ConcurrentHashMap<>();

    @Override
    public String id() {
        return "h2";
    }

    @Override
    public void close() {
        for (String key : dbsByKey.keySet()) {
            close(key);
        }
    }

    /**
     * Closes and forgets just {@code key}'s database, leaving any others under
     * this provider untouched. A no-op if {@code key} was never
     * {@link #activate(String) activate}d, or was already closed.
     *
     * <p>H2 drops an in-memory database's data as soon as its last open
     * connection closes (no {@code DB_CLOSE_DELAY} is set), so closing the
     * pool — which otherwise keeps at least one idle connection open for the
     * life of the provider — is all that is needed to free it.
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
            LOGGER.warn("closing an h2 connection pool failed", e);
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
        try {
            // The in-memory engine, isolated by a database name carrying a UUID.
            String url = "jdbc:h2:mem:" + UUID.randomUUID() + ";DATABASE_TO_UPPER=false";
            JdbcDataSource ds = new JdbcDataSource();
            ds.setUrl(url);
            ds.setUser("sa");
            ds.setPassword("sa");
            Dialect dialect;
            try (Connection c = ds.getConnection()) {
                dialect = new H2Dialect(DialectInitData.fromConnection(c));
            }
            return new ActiveDatabase(ds, dialect, ActiveDatabase.settingsFor(key));
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to start H2 datasource", e);
        }
    }
}
