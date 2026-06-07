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
import java.sql.Statement;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.eclipse.daanse.jdbc.datasource.testkit.api.ActiveDatabase;
import org.eclipse.daanse.jdbc.datasource.testkit.api.DatabaseProvider;
import org.eclipse.daanse.jdbc.db.dialect.api.Dialect;
import org.eclipse.daanse.jdbc.db.dialect.api.DialectInitData;
import org.eclipse.daanse.jdbc.db.dialect.db.mssqlserver.MicrosoftSqlServerDialect;
import org.testcontainers.containers.MSSQLServerContainer;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

/**
 * MS SQL Server provider backed by a shared Testcontainers container.
 * {@link #activate(String)} returns per-key DATABASE-isolated
 * {@link ActiveDatabase} — each key gets {@code CREATE DATABASE} and a
 * DataSource bound to it.
 */
public class MsSqlDatabaseProvider implements DatabaseProvider {

    private static final String IMAGE = "mcr.microsoft.com/mssql/server:2022-latest";
    private static final String DEFAULT_KEY = "__default__";

    private static volatile MSSQLServerContainer<?> container;
    private static final Object LOCK = new Object();

    private final ConcurrentMap<String, ActiveDatabase> dbsByKey = new ConcurrentHashMap<>();

    @Override
    public String id() {
        return "mssql";
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
        MSSQLServerContainer<?> c = sharedContainer();
        String dbName = sanitize(key);
        try (Connection admin = openAdmin(c); Statement st = admin.createStatement()) {
            st.execute("IF DB_ID('" + dbName + "') IS NULL CREATE DATABASE [" + dbName + "]");
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to create MSSQL database " + dbName, e);
        }
        SQLServerDataSource ds = new SQLServerDataSource();
        ds.setURL(c.getJdbcUrl());
        ds.setUser(c.getUsername());
        ds.setPassword(c.getPassword());
        ds.setDatabaseName(dbName);
        try (Connection conn = ds.getConnection()) {
            return new ActiveDatabase(ds, new MicrosoftSqlServerDialect(DialectInitData.fromConnection(conn)));
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to build MSSQL dialect for key " + key, e);
        }
    }

    private static Connection openAdmin(MSSQLServerContainer<?> c) throws SQLException {
        SQLServerDataSource admin = new SQLServerDataSource();
        admin.setURL(c.getJdbcUrl());
        admin.setUser(c.getUsername());
        admin.setPassword(c.getPassword());
        return admin.getConnection();
    }

    private static String sanitize(String k) {
        StringBuilder sb = new StringBuilder();
        for (char ch : k.toCharArray()) {
            sb.append(Character.isLetterOrDigit(ch) || ch == '_' ? ch : '_');
        }
        String s = sb.toString();
        return s.length() > 128 ? s.substring(0, 128) : s;
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
