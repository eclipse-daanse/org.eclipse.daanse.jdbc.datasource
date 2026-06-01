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

import javax.sql.DataSource;

import org.eclipse.daanse.jdbc.datasource.testkit.api.ActiveDatabase;
import org.eclipse.daanse.jdbc.datasource.testkit.api.DatabaseProvider;
import org.eclipse.daanse.jdbc.db.dialect.api.Dialect;
import org.eclipse.daanse.jdbc.db.dialect.api.DialectInitData;
import org.eclipse.daanse.jdbc.db.dialect.db.mariadb.MariaDBDialect;
import org.mariadb.jdbc.MariaDbDataSource;
import org.testcontainers.containers.MariaDBContainer;

/**
 * MariaDB provider backed by a Testcontainers container. Shared JVM-wide.
 */
public class MariaDbDatabaseProvider implements DatabaseProvider {

    private static final String IMAGE = "mariadb:11";

    private static volatile MariaDBContainer<?> container;
    private static final Object LOCK = new Object();

    private DataSource dataSource;
    private Dialect dialect;

    @Override
    public String id() {
        return "mariadb";
    }

    @Override
    public synchronized ActiveDatabase activate() {
        if (dataSource != null) {
            return new ActiveDatabase(dataSource, dialect);
        }
        MariaDBContainer<?> c = sharedContainer();
        try {
            MariaDbDataSource ds = new MariaDbDataSource();
            ds.setUrl(c.getJdbcUrl());
            ds.setUser(c.getUsername());
            ds.setPassword(c.getPassword());
            try (Connection conn = ds.getConnection()) {
                this.dialect = new MariaDBDialect(DialectInitData.fromConnection(conn));
            }
            this.dataSource = ds;
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to build MariaDB dialect", e);
        }
        return new ActiveDatabase(dataSource, dialect);
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
                m.start();
                container = m;
                Runtime.getRuntime().addShutdownHook(new Thread(m::close, "daanse-mariadb-stop"));
            }
            return container;
        }
    }
}
