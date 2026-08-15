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
package org.eclipse.daanse.jdbc.datasource.duckdb.impl;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.duckdb.DuckDBConnection;
import org.duckdb.DuckDBDriver;

/**
 * Minimal {@link DataSource} over the DuckDB JDBC driver. The driver ships no
 * DataSource implementation of its own, so connections are created through
 * {@link DuckDBDriver#connect(String, Properties)} (no {@code DriverManager} —
 * that would not work across OSGi class loaders).
 * <p>
 * One connection is opened and kept; every connection handed out is a
 * {@link DuckDBConnection#duplicate()} of it. An in-memory DuckDB database
 * belongs to the connection that opened it — a second {@code connect} would
 * open a second, empty database.
 */
public class DuckDbDataSource implements DataSource {

    private final DuckDBDriver driver = new DuckDBDriver();
    private final String url;
    private final Properties properties;

    /** The connection that owns the database; held until {@link #close()}. */
    private volatile DuckDBConnection keeper;

    private PrintWriter logWriter;
    private int loginTimeout = 0;

    public DuckDbDataSource(String url, Properties properties) {
        this.url = url;
        this.properties = properties;
    }

    @Override
    public Connection getConnection() throws SQLException {
        DuckDBConnection held = keeper;
        if (held == null) {
            synchronized (this) {
                if (keeper == null) {
                    keeper = (DuckDBConnection) driver.connect(url, properties);
                }
                held = keeper;
            }
        }
        return held.duplicate();
    }

    /**
     * Releases the database. The next {@link #getConnection()} opens a new — for
     * in-memory, empty — database, so call this only at the end of the component's life.
     */
    public void close() throws SQLException {
        DuckDBConnection held;
        synchronized (this) {
            held = keeper;
            keeper = null;
        }
        if (held != null) {
            held.close();
        }
    }

    /**
     * DuckDB is embedded and has no user/password authentication — the credentials
     * are ignored.
     */
    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return getConnection();
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
