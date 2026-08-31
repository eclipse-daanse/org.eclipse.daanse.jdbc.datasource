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
*   SmartCity Jena - initial
*/
package org.eclipse.daanse.jdbc.datasource.hive.impl;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.apache.hive.jdbc.HiveDriver;

/**
 * Minimal {@link DataSource} over the Hive JDBC driver. The driver's own
 * {@code HiveDataSource} is a stub, so connections are created through
 * {@link HiveDriver#connect(String, Properties)} (no {@code DriverManager} —
 * that would not work across OSGi class loaders). HiveServer2 is a server
 * database: every {@link #getConnection()} opens a fresh connection.
 */
public class HiveDataSource implements DataSource {

    private final HiveDriver driver = new HiveDriver();
    private final String url;
    private final Properties properties;

    private PrintWriter logWriter;
    private int loginTimeout = 0;

    public HiveDataSource(String url, Properties properties) {
        this.url = url;
        this.properties = properties;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return driver.connect(url, properties);
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        Properties overridden = new Properties();
        overridden.putAll(properties);
        if (username != null) {
            overridden.setProperty("user", username);
        }
        if (password != null) {
            overridden.setProperty("password", password);
        }
        return driver.connect(url, overridden);
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return logWriter;
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        this.logWriter = out;
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        this.loginTimeout = seconds;
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return loginTimeout;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return driver.getParentLogger();
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        if (iface.isInstance(this)) {
            return iface.cast(this);
        }
        throw new SQLException("Cannot unwrap to " + iface.getName());
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return iface.isInstance(this);
    }
}
