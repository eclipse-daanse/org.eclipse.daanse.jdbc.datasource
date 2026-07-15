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

/**
 * H2 in-memory provider. {@link #activate()} returns the provider's single
 * default database. {@link #activate(String)} returns one isolated database
 * per distinct key — backed by independent memFS UUID URLs.
 */
public class H2DatabaseProvider implements DatabaseProvider {

    private static final String DEFAULT_KEY = "__default__";

    private final ConcurrentMap<String, ActiveDatabase> dbsByKey = new ConcurrentHashMap<>();

    @Override
    public String id() {
        return "h2";
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
            String url = "jdbc:h2:memFS:" + UUID.randomUUID() + ";DATABASE_TO_UPPER=false";
            JdbcDataSource ds = new JdbcDataSource();
            ds.setUrl(url);
            ds.setUser("sa");
            ds.setPassword("sa");
            Dialect dialect;
            try (Connection c = ds.getConnection()) {
                dialect = new H2Dialect(DialectInitData.fromConnection(c));
            }
            return new ActiveDatabase(ds, dialect);
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to start H2 datasource", e);
        }
    }
}
