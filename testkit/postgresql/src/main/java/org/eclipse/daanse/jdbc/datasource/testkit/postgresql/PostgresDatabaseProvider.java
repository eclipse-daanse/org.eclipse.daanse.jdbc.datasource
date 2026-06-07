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
package org.eclipse.daanse.jdbc.datasource.testkit.postgresql;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.eclipse.daanse.jdbc.datasource.testkit.api.ActiveDatabase;
import org.eclipse.daanse.jdbc.datasource.testkit.api.DatabaseProvider;
import org.eclipse.daanse.jdbc.db.dialect.api.Dialect;
import org.eclipse.daanse.jdbc.db.dialect.api.DialectInitData;
import org.eclipse.daanse.jdbc.db.dialect.db.postgresql.PostgreSqlDialect;
import org.postgresql.ds.PGSimpleDataSource;
import org.testcontainers.containers.PostgreSQLContainer;

/**
 * PostgreSQL provider backed by a Testcontainers container. A single container
 * is shared for the JVM lifetime; {@link #activate(String)} returns a
 * schema-isolated {@link ActiveDatabase} per key (CREATE SCHEMA + DataSource
 * with {@code currentSchema=<key>}).
 */
public class PostgresDatabaseProvider implements DatabaseProvider {

    private static final String IMAGE = "postgres:16-alpine";
    private static final String DEFAULT_KEY = "__default__";

    private static volatile PostgreSQLContainer<?> container;
    private static final Object LOCK = new Object();

    private final ConcurrentMap<String, ActiveDatabase> dbsByKey = new ConcurrentHashMap<>();

    @Override
    public String id() {
        return "postgres";
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
        PostgreSQLContainer<?> c = sharedContainer();
        String schema = sanitize(key);
        try (Connection admin = openAdmin(c); Statement st = admin.createStatement()) {
            st.execute("CREATE SCHEMA IF NOT EXISTS \"" + schema + "\"");
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to create PG schema " + schema, e);
        }
        PGSimpleDataSource ds = new PGSimpleDataSource();
        // Append currentSchema so all statements default to the per-key schema.
        String url = c.getJdbcUrl();
        url += (url.contains("?") ? "&" : "?") + "currentSchema=" + schema;
        ds.setUrl(url);
        ds.setUser(c.getUsername());
        ds.setPassword(c.getPassword());
        Dialect dialect;
        try (Connection conn = ds.getConnection()) {
            dialect = new PostgreSqlDialect(DialectInitData.fromConnection(conn));
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to build PostgreSQL dialect for key " + key, e);
        }
        return new ActiveDatabase(ds, dialect);
    }

    private static Connection openAdmin(PostgreSQLContainer<?> c) throws SQLException {
        PGSimpleDataSource admin = new PGSimpleDataSource();
        admin.setUrl(c.getJdbcUrl());
        admin.setUser(c.getUsername());
        admin.setPassword(c.getPassword());
        return admin.getConnection();
    }

    /** PG identifier: alphanumeric + _; lowercased; max 63 chars. */
    private static String sanitize(String k) {
        StringBuilder sb = new StringBuilder();
        for (char ch : k.toLowerCase().toCharArray()) {
            sb.append(Character.isLetterOrDigit(ch) || ch == '_' ? ch : '_');
        }
        String s = sb.toString();
        return s.length() > 63 ? s.substring(0, 63) : s;
    }

    @SuppressWarnings("resource")
    private static PostgreSQLContainer<?> sharedContainer() {
        PostgreSQLContainer<?> c = container;
        if (c != null) {
            return c;
        }
        synchronized (LOCK) {
            if (container == null) {
                @SuppressWarnings("rawtypes")
                PostgreSQLContainer pg = new PostgreSQLContainer(IMAGE);
                pg.start();
                container = pg;
                Runtime.getRuntime().addShutdownHook(new Thread(pg::close, "daanse-pg-stop"));
            }
            return container;
        }
    }
}
