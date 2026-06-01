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

import javax.sql.DataSource;

import org.eclipse.daanse.jdbc.datasource.testkit.api.ActiveDatabase;
import org.eclipse.daanse.jdbc.datasource.testkit.api.DatabaseProvider;
import org.eclipse.daanse.jdbc.db.dialect.api.Dialect;
import org.eclipse.daanse.jdbc.db.dialect.api.DialectInitData;
import org.eclipse.daanse.jdbc.db.dialect.db.mysql.MySqlDialect;
import org.testcontainers.containers.MySQLContainer;

import com.mysql.cj.jdbc.MysqlDataSource;

/**
 * MySQL provider backed by a Testcontainers container. Shared JVM-wide.
 */
public class MySqlDatabaseProvider implements DatabaseProvider {

    private static final String IMAGE = "mysql:8.0";

    private static volatile MySQLContainer<?> container;
    private static final Object LOCK = new Object();

    private DataSource dataSource;
    private Dialect dialect;

    @Override
    public String id() {
        return "mysql";
    }

    @Override
    public synchronized ActiveDatabase activate() {
        if (dataSource != null) {
            return new ActiveDatabase(dataSource, dialect);
        }
        MySQLContainer<?> c = sharedContainer();
        MysqlDataSource ds = new MysqlDataSource();
        ds.setURL(c.getJdbcUrl());
        ds.setUser(c.getUsername());
        ds.setPassword(c.getPassword());
        try (Connection conn = ds.getConnection()) {
            this.dialect = new MySqlDialect(DialectInitData.fromConnection(conn));
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to build MySQL dialect", e);
        }
        this.dataSource = ds;
        return new ActiveDatabase(dataSource, dialect);
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
