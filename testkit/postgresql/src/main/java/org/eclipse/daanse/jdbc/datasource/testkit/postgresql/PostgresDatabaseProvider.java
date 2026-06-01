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

import javax.sql.DataSource;

import org.eclipse.daanse.jdbc.datasource.testkit.api.ActiveDatabase;
import org.eclipse.daanse.jdbc.datasource.testkit.api.DatabaseProvider;
import org.eclipse.daanse.jdbc.db.dialect.api.Dialect;
import org.eclipse.daanse.jdbc.db.dialect.api.DialectInitData;
import org.eclipse.daanse.jdbc.db.dialect.db.postgresql.PostgreSqlDialect;
import org.postgresql.ds.PGSimpleDataSource;
import org.testcontainers.containers.PostgreSQLContainer;

/**
 * PostgreSQL provider backed by a Testcontainers container. A single container
 * is shared for the JVM lifetime; lazily started on first {@link #activate()}.
 */
public class PostgresDatabaseProvider implements DatabaseProvider {

    private static final String IMAGE = "postgres:16-alpine";

    private static volatile PostgreSQLContainer<?> container;
    private static final Object LOCK = new Object();

    private DataSource dataSource;
    private Dialect dialect;

    @Override
    public String id() {
        return "postgres";
    }

    @Override
    public synchronized ActiveDatabase activate() {
        if (dataSource != null) {
            return new ActiveDatabase(dataSource, dialect);
        }
        PostgreSQLContainer<?> c = sharedContainer();
        PGSimpleDataSource ds = new PGSimpleDataSource();
        ds.setUrl(c.getJdbcUrl());
        ds.setUser(c.getUsername());
        ds.setPassword(c.getPassword());
        try (Connection conn = ds.getConnection()) {
            this.dialect = new PostgreSqlDialect(DialectInitData.fromConnection(conn));
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to build PostgreSQL dialect", e);
        }
        this.dataSource = ds;
        return new ActiveDatabase(dataSource, dialect);
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
