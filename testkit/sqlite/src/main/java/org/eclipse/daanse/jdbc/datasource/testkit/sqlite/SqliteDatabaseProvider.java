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

import javax.sql.DataSource;

import org.eclipse.daanse.jdbc.datasource.testkit.api.ActiveDatabase;
import org.eclipse.daanse.jdbc.datasource.testkit.api.DatabaseProvider;
import org.eclipse.daanse.jdbc.db.dialect.api.Dialect;
import org.eclipse.daanse.jdbc.db.dialect.api.DialectInitData;
import org.eclipse.daanse.jdbc.db.dialect.db.sqlite.SqliteDialect;
import org.sqlite.SQLiteDataSource;

/**
 * SQLite in-memory provider using URI-style shared-cache so multiple
 * connections from the same {@link DataSource} see the same database. Each
 * provider instance gets its own UUID-keyed in-memory DB for test isolation.
 */
public class SqliteDatabaseProvider implements DatabaseProvider {

    private DataSource dataSource;
    private Dialect dialect;

    @Override
    public String id() {
        return "sqlite";
    }

    @Override
    public synchronized ActiveDatabase activate() {
        if (dataSource != null) {
            return new ActiveDatabase(dataSource, dialect);
        }
        try {
            // URI form with mode=memory + cache=shared means multiple
            // connections to the same URL share an in-memory database; the
            // UUID-tagged name isolates this provider instance from other
            // providers in the same JVM.
            String url = "jdbc:sqlite:file:daanse-test-" + UUID.randomUUID() + "?mode=memory&cache=shared";
            SQLiteDataSource ds = new SQLiteDataSource();
            ds.setUrl(url);
            try (Connection c = ds.getConnection()) {
                this.dialect = new SqliteDialect(DialectInitData.fromConnection(c));
            }
            this.dataSource = ds;
            return new ActiveDatabase(dataSource, dialect);
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to start SQLite datasource", e);
        }
    }
}
