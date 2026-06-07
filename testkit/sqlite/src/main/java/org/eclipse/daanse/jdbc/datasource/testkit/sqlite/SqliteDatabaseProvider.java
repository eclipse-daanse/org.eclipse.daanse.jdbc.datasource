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
package org.eclipse.daanse.jdbc.datasource.testkit.sqlite;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.eclipse.daanse.jdbc.datasource.testkit.api.ActiveDatabase;
import org.eclipse.daanse.jdbc.datasource.testkit.api.DatabaseProvider;
import org.eclipse.daanse.jdbc.db.dialect.api.Dialect;
import org.eclipse.daanse.jdbc.db.dialect.api.DialectInitData;
import org.eclipse.daanse.jdbc.db.dialect.db.sqlite.SqliteDialect;
import org.sqlite.SQLiteDataSource;

/**
 * SQLite in-memory provider. {@link #activate(String)} returns one isolated
 * shared-cache in-memory DB per distinct key (UUID-tagged URL).
 */
public class SqliteDatabaseProvider implements DatabaseProvider {

    private static final String DEFAULT_KEY = "__default__";

    private final ConcurrentMap<String, ActiveDatabase> dbsByKey = new ConcurrentHashMap<>();

    @Override
    public String id() {
        return "sqlite";
    }

    @Override
    public ActiveDatabase activate() {
        return activate(DEFAULT_KEY);
    }

    @Override
    public ActiveDatabase activate(String isolationKey) {
        return dbsByKey.computeIfAbsent(isolationKey, k -> newDatabase());
    }

    private ActiveDatabase newDatabase() {
        try {
            String url = "jdbc:sqlite:file:daanse-test-" + UUID.randomUUID() + "?mode=memory&cache=shared";
            SQLiteDataSource ds = new SQLiteDataSource();
            ds.setUrl(url);
            Dialect dialect;
            try (Connection c = ds.getConnection()) {
                dialect = new SqliteDialect(DialectInitData.fromConnection(c));
            }
            return new ActiveDatabase(ds, dialect);
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to start SQLite datasource", e);
        }
    }
}
