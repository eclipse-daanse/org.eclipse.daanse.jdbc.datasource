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
package org.eclipse.daanse.jdbc.datasource.testkit.mysql;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.eclipse.daanse.jdbc.datasource.testkit.api.ActiveDatabase;
import org.eclipse.daanse.jdbc.datasource.testkit.api.DatabaseProvider;
import org.eclipse.daanse.jdbc.db.dialect.api.Dialect;
import org.eclipse.daanse.jdbc.db.dialect.api.DialectInitData;
import org.eclipse.daanse.jdbc.db.dialect.db.mysql.MySqlDialect;
import org.testcontainers.containers.MySQLContainer;

import com.mysql.cj.jdbc.MysqlDataSource;

/**
 * MySQL provider backed by a shared Testcontainers container.
 * {@link #activate(String)} returns a database-isolated {@link ActiveDatabase}
 * per key — MySQL "schema" == "database", so each key gets {@code CREATE
 * DATABASE} and a DataSource bound to it.
 */
public class MySqlDatabaseProvider implements DatabaseProvider {

    private static final String IMAGE = "mysql:8.0";
    private static final String DEFAULT_KEY = "__default__";

    private static volatile MySQLContainer<?> container;
    private static final Object LOCK = new Object();

    private final ConcurrentMap<String, ActiveDatabase> dbsByKey = new ConcurrentHashMap<>();

    @Override
    public String id() {
        return "mysql";
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
        MySQLContainer<?> c = sharedContainer();
        String dbName = sanitize(key);
        try (Connection admin = openAdmin(c, c.getDatabaseName()); Statement st = admin.createStatement()) {
            st.execute("CREATE DATABASE IF NOT EXISTS `" + dbName + "`");
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to create MySQL database " + dbName, e);
        }
        MysqlDataSource ds = new MysqlDataSource();
        ds.setURL(jdbcUrlWithDb(c, dbName));
        ds.setUser(c.getUsername());
        ds.setPassword(c.getPassword());
        Dialect dialect;
        try (Connection conn = ds.getConnection()) {
            dialect = new MySqlDialect(DialectInitData.fromConnection(conn));
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to build MySQL dialect for key " + key, e);
        }
        return new ActiveDatabase(ds, dialect);
    }

    private static Connection openAdmin(MySQLContainer<?> c, String defaultDb) throws SQLException {
        MysqlDataSource admin = new MysqlDataSource();
        admin.setURL(c.getJdbcUrl());
        admin.setUser(c.getUsername());
        admin.setPassword(c.getPassword());
        return admin.getConnection();
    }

    private static String jdbcUrlWithDb(MySQLContainer<?> c, String dbName) {
        String url = c.getJdbcUrl();
        int q = url.indexOf('?');
        String base = q == -1 ? url : url.substring(0, q);
        String tail = q == -1 ? "" : url.substring(q);
        int slash = base.lastIndexOf('/');
        if (slash > "jdbc:mysql://".length()) {
            base = base.substring(0, slash + 1) + dbName;
        }
        return base + tail;
    }

    /** MySQL identifier: alphanumeric + _; max 64 chars. */
    private static String sanitize(String k) {
        StringBuilder sb = new StringBuilder();
        for (char ch : k.toCharArray()) {
            sb.append(Character.isLetterOrDigit(ch) || ch == '_' ? ch : '_');
        }
        String s = sb.toString();
        return s.length() > 64 ? s.substring(0, 64) : s;
    }

    @SuppressWarnings("resource")
    private static MySQLContainer<?> sharedContainer() {
        MySQLContainer<?> c = container;
        if (c != null) {
            return c;
        }
        synchronized (LOCK) {
            if (container == null) {
                @SuppressWarnings("rawtypes")
                MySQLContainer my = new MySQLContainer(IMAGE);
                my.start();
                container = my;
                Runtime.getRuntime().addShutdownHook(new Thread(my::close, "daanse-mysql-stop"));
            }
            return container;
        }
    }
}
