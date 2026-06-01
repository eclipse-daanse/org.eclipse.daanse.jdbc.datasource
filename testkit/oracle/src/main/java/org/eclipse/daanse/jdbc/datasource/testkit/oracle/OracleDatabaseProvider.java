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
package org.eclipse.daanse.jdbc.datasource.testkit.oracle;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import oracle.jdbc.pool.OracleDataSource;
import org.eclipse.daanse.jdbc.datasource.testkit.api.ActiveDatabase;
import org.eclipse.daanse.jdbc.datasource.testkit.api.DatabaseProvider;
import org.eclipse.daanse.jdbc.db.dialect.api.Dialect;
import org.eclipse.daanse.jdbc.db.dialect.api.DialectInitData;
import org.eclipse.daanse.jdbc.db.dialect.db.oracle.OracleDialect;
import org.testcontainers.oracle.OracleContainer;

/**
 * Oracle provider backed by Testcontainers using the gvenzl/oracle-free image.
 * Container is ~3 GB; first pull takes a while. Shared JVM-wide.
 */
public class OracleDatabaseProvider implements DatabaseProvider {

    private static final String IMAGE = "gvenzl/oracle-free:23-slim-faststart";

    private static volatile OracleContainer container;
    private static final Object LOCK = new Object();

    private DataSource dataSource;
    private Dialect dialect;

    @Override
    public String id() {
        return "oracle";
    }

    @Override
    public synchronized ActiveDatabase activate() {
        if (dataSource != null) {
            return new ActiveDatabase(dataSource, dialect);
        }
        OracleContainer c = sharedContainer();
        try {
            OracleDataSource ds = new OracleDataSource();
            ds.setURL(c.getJdbcUrl());
            ds.setUser(c.getUsername());
            ds.setPassword(c.getPassword());
            try (Connection conn = ds.getConnection()) {
                this.dialect = new OracleDialect(DialectInitData.fromConnection(conn));
            }
            this.dataSource = ds;
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to build Oracle dialect", e);
        }
        return new ActiveDatabase(dataSource, dialect);
    }

    @SuppressWarnings("resource")
    private static OracleContainer sharedContainer() {
        OracleContainer c = container;
        if (c != null) {
            return c;
        }
        synchronized (LOCK) {
            if (container == null) {
                OracleContainer o = new OracleContainer(IMAGE).withUsername("rt").withPassword("rt");
                o.start();
                container = o;
                Runtime.getRuntime().addShutdownHook(new Thread(o::close, "daanse-oracle-stop"));
            }
            return container;
        }
    }
}
