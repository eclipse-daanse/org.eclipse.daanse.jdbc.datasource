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

import javax.sql.DataSource;

import org.eclipse.daanse.jdbc.datasource.testkit.api.ActiveDatabase;
import org.eclipse.daanse.jdbc.datasource.testkit.api.DatabaseProvider;
import org.eclipse.daanse.jdbc.db.dialect.api.Dialect;
import org.eclipse.daanse.jdbc.db.dialect.api.DialectInitData;
import org.eclipse.daanse.jdbc.db.dialect.db.h2.H2Dialect;
import org.h2.jdbcx.JdbcDataSource;

/**
 * H2 in-memory provider. Uses the memFS feature so each provider instance gets
 * its own isolated database.
 */
public class H2DatabaseProvider implements DatabaseProvider {

    private DataSource dataSource;
    private Dialect dialect;

    @Override
    public String id() {
        return "h2";
    }

    @Override
    public synchronized ActiveDatabase activate() {
        if (dataSource != null) {
            return new ActiveDatabase(dataSource, dialect);
        }
        try {
            String url = "jdbc:h2:memFS:" + UUID.randomUUID() + ";DATABASE_TO_UPPER=false";
            JdbcDataSource ds = new JdbcDataSource();
            ds.setUrl(url);
            ds.setUser("sa");
            ds.setPassword("sa");
            try (Connection c = ds.getConnection()) {
                this.dialect = new H2Dialect(DialectInitData.fromConnection(c));
            }
            this.dataSource = ds;
            return new ActiveDatabase(dataSource, dialect);
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to start H2 datasource", e);
        }
    }
}
