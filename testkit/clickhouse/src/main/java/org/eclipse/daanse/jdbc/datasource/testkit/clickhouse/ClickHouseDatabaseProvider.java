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
package org.eclipse.daanse.jdbc.datasource.testkit.clickhouse;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.sql.DataSource;

import org.eclipse.daanse.jdbc.datasource.testkit.api.ActiveDatabase;
import org.eclipse.daanse.jdbc.datasource.testkit.api.DatabaseProvider;
import org.eclipse.daanse.sql.dialect.api.Dialect;
import org.eclipse.daanse.sql.dialect.api.DialectInitData;
import org.eclipse.daanse.sql.dialect.db.clickhouse.ClickHouseDialect;
import org.testcontainers.clickhouse.ClickHouseContainer;

import com.clickhouse.jdbc.DataSourceImpl;

/**
 * ClickHouse provider backed by a shared Testcontainers container.
 * <p>
 * Replaces a hand-rolled docker-java provider that reached for ConfigurationAdmin
 * to obtain its DataSource and therefore only worked inside an OSGi framework.
 * Testcontainers needs no framework, which is what lets the suite run as plain
 * JUnit.
 * <p>
 * {@link #activate(String)} isolates per key with {@code CREATE DATABASE};
 * ClickHouse has no schema below the database, so the key names the database.
 */
public class ClickHouseDatabaseProvider implements DatabaseProvider {

    private static final String IMAGE = "clickhouse/clickhouse-server:24.3";
    private static final String DEFAULT_KEY = "__default__";

    private static volatile ClickHouseContainer container;
    private static final Object LOCK = new Object();

    private final ConcurrentMap<String, ActiveDatabase> dbsByKey = new ConcurrentHashMap<>();

    @Override
    public String id() {
        return "clickhouse";
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
        ClickHouseContainer c = sharedContainer();
        String dbName = sanitize(key);
        try (Connection admin = open(c, "default"); Statement st = admin.createStatement()) {
            st.execute("CREATE DATABASE IF NOT EXISTS " + dbName);
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to create ClickHouse database " + dbName, e);
        }
        DataSource ds = dataSource(c, dbName);
        Dialect dialect;
        try (Connection conn = ds.getConnection()) {
            dialect = new ClickHouseDialect(DialectInitData.fromConnection(conn));
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to build ClickHouse dialect for key " + key, e);
        }
        return new ActiveDatabase(ds, dialect, ActiveDatabase.settingsFor(key));
    }

    private static DataSource dataSource(ClickHouseContainer c, String dbName) {
        Properties props = new Properties();
        props.setProperty("user", c.getUsername());
        props.setProperty("password", c.getPassword());
        return new DataSourceImpl(urlWithDatabase(c, dbName), props);
    }

    private static Connection open(ClickHouseContainer c, String dbName) throws SQLException {
        return dataSource(c, dbName).getConnection();
    }

    /**
     * The container hands out a URL pointing at the default database; the isolated
     * database is selected by replacing the path.
     */
    private static String urlWithDatabase(ClickHouseContainer c, String dbName) {
        String url = c.getJdbcUrl();
        int lastSlash = url.lastIndexOf('/');
        return lastSlash < 0 ? url + "/" + dbName : url.substring(0, lastSlash + 1) + dbName;
    }

    /** ClickHouse identifiers: letters, digits and underscore. */
    private static String sanitize(String key) {
        String cleaned = key.replaceAll("[^A-Za-z0-9_]", "_");
        return cleaned.isEmpty() || Character.isDigit(cleaned.charAt(0)) ? "db_" + cleaned : cleaned;
    }

    private static ClickHouseContainer sharedContainer() {
        ClickHouseContainer c = container;
        if (c == null) {
            synchronized (LOCK) {
                c = container;
                if (c == null) {
                    ClickHouseContainer started = new ClickHouseContainer(IMAGE);
                    started.start();
                    container = started;
                    c = started;
                }
            }
        }
        return c;
    }
}
