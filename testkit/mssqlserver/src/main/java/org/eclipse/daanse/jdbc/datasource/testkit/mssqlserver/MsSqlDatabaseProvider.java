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
package org.eclipse.daanse.jdbc.datasource.testkit.mssqlserver;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.eclipse.daanse.jdbc.datasource.testkit.api.ActiveDatabase;
import org.eclipse.daanse.jdbc.datasource.testkit.api.DatabaseProvider;
import org.eclipse.daanse.jdbc.db.dialect.api.Dialect;
import org.eclipse.daanse.jdbc.db.dialect.api.DialectInitData;
import org.eclipse.daanse.jdbc.db.dialect.db.mssqlserver.MicrosoftSqlServerDialect;
import org.testcontainers.containers.MSSQLServerContainer;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

/**
 * MS SQL Server provider backed by a Testcontainers container. The EULA must be
 * accepted explicitly — the container ships with a flag for that.
 */
public class MsSqlDatabaseProvider implements DatabaseProvider {

    private static final String IMAGE = "mcr.microsoft.com/mssql/server:2022-latest";

    private static volatile MSSQLServerContainer<?> container;
    private static final Object LOCK = new Object();

    private DataSource dataSource;
    private Dialect dialect;

    @Override
    public String id() {
        return "mssql";
    }

    @Override
    public synchronized ActiveDatabase activate() {
        if (dataSource != null) {
            return new ActiveDatabase(dataSource, dialect);
        }
        MSSQLServerContainer<?> c = sharedContainer();
        SQLServerDataSource ds = new SQLServerDataSource();
        ds.setURL(c.getJdbcUrl());
        ds.setUser(c.getUsername());
        ds.setPassword(c.getPassword());
        try (Connection conn = ds.getConnection()) {
            DialectInitData fromConnection = DialectInitData.fromConnection(conn);
            this.dialect = new MicrosoftSqlServerDialect(fromConnection);
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to build MS SQL Server dialect", e);
        }
        this.dataSource = ds;
        return new ActiveDatabase(dataSource, dialect);
    }

    @SuppressWarnings("resource")
    private static MSSQLServerContainer<?> sharedContainer() {
        MSSQLServerContainer<?> c = container;
        if (c != null) {
            return c;
        }
        synchronized (LOCK) {
            if (container == null) {
                @SuppressWarnings("rawtypes")
                MSSQLServerContainer ms = new MSSQLServerContainer(IMAGE).acceptLicense();
                ms.start();
                container = ms;
                Runtime.getRuntime().addShutdownHook(new Thread(ms::close, "daanse-mssql-stop"));
            }
            return container;
        }
    }
}
