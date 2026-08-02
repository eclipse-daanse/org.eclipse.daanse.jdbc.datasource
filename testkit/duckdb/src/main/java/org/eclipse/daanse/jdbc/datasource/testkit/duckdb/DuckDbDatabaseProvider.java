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
package org.eclipse.daanse.jdbc.datasource.testkit.duckdb;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.duckdb.DuckDBConnection;
import org.duckdb.DuckDBDriver;
import org.eclipse.daanse.jdbc.datasource.testkit.api.ActiveDatabase;
import org.eclipse.daanse.jdbc.datasource.testkit.api.DatabaseProvider;
import org.eclipse.daanse.sql.dialect.api.Dialect;
import org.eclipse.daanse.sql.dialect.api.DialectInitData;
import org.eclipse.daanse.sql.dialect.db.duckdb.DuckDbDialect;
import org.slf4j.LoggerFactory;


/**
 * Embedded DuckDB, in memory. Every {@code driver.connect("jdbc:duckdb:")} opens
 * its own private, empty database, so this provider connects ONCE and hands out
 * {@link DuckDBConnection#duplicate()} siblings of that connection — they all
 * share the one in-memory database, and it lives as long as the keeper does.
 *
 * <p>
 * The engine runs on its own defaults; system properties named
 * {@code daanse.duckdb.<setting>} are passed through to it.
 * </p>
 */
public class DuckDbDatabaseProvider implements DatabaseProvider {

	// java.util.logging.Logger is imported for the DataSource shim below, so the
	// slf4j class logger is declared with its fully qualified type.
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(DuckDbDatabaseProvider.class);

	private static final String URL = "jdbc:duckdb:";

	/** Prefix of the system properties passed through to the engine. */
	private static final String SETTING_PREFIX = "daanse.duckdb.";

	@Override
	public String id() {
		return "duckdb";
	}

	private volatile DuckDbDataSource dataSource;

	@Override
	public void close() {
		DuckDbDataSource ds = dataSource;
		if (ds != null) {
			// closing the keeper drops the in-memory database; there is no file to unlink
			ds.close();
		}
	}

	@Override
	public ActiveDatabase activate() {
		long tActivate = System.nanoTime();
		DuckDbDataSource dataSource = new DuckDbDataSource(URL);
		this.dataSource = dataSource;
		try (Connection connection = dataSource.getConnection()) {
			Dialect dialect = new DuckDbDialect(DialectInitData.fromConnection(connection));
			// embedded: db-ready = the (tiny) connect duration, for comparability with docker DBs
			long ms = (System.nanoTime() - tActivate) / 1_000_000;
			LOGGER.warn("DBTIMING db={} phase=db-ready ms={}", id(), ms);
			LOGGER.warn("DBTIMING db={} phase=context-first ms={} detail=fresh,epoch={}", id(), ms,
					System.currentTimeMillis() / 1000);
			return new ActiveDatabase(dataSource, dialect);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * The DuckDB driver ships no {@link DataSource}; connect through
	 * {@link DuckDBDriver#connect} directly (NOT {@code DriverManager}, which
	 * does not work across OSGi class loaders).
	 */
	private static final class DuckDbDataSource implements DataSource {

		private final DuckDBDriver driver = new DuckDBDriver();
		private final String url;
		private PrintWriter logWriter;
		private int loginTimeout = 0;

		/**
		 * An embedded DuckDB database lives exactly as long as its last open
		 * connection: closing it tears the database down. Callers here take a
		 * connection per statement, so this one is held open for the provider's
		 * lifetime - otherwise every query would pay a full database startup and
		 * shutdown, which dwarfs the query itself.
		 *
		 * <p>
		 * It is also the database every other connection duplicates, so it must be
		 * the connection that carries the settings (see {@link #getConnection()}).
		 */
		private volatile DuckDBConnection keeper;

		private DuckDbDataSource(String url) {
			this.url = url;
		}

		private void close() {
			DuckDBConnection k = keeper;
			keeper = null;
			if (k != null) {
				try {
					k.close();
				} catch (SQLException e) {
					LOGGER.warn("closing the duckdb keeper connection failed", e);
				}
			}
		}

		@Override
		public Connection getConnection() throws SQLException {
			Properties props = duckDbSettings();
			if (keeper == null) {
				synchronized (this) {
					if (keeper == null) {
						// The settings above only take effect on the connect that starts the
						// database, so the keeper must carry them.
						keeper = (DuckDBConnection) driver.connect(url, props);
					}
				}
			}
			// A fresh connect would open a fresh, empty in-memory database. duplicate()
			// returns an independent connection onto the keeper's database.
			return keeper.duplicate();
		}

		/**
		 * Every {@code daanse.duckdb.<setting>} system property, passed on as
		 * {@code <setting>}.
		 *
		 * <p>
		 * Nothing is set otherwise, so an unconfigured provider starts DuckDB with
		 * DuckDB's own defaults. Any setting the engine accepts can be given this
		 * way - {@code -Ddaanse.duckdb.threads=2},
		 * {@code -Ddaanse.duckdb.memory_limit=4GB}. They go in as connection
		 * properties rather than a {@code SET} per connection, which would cost a
		 * round trip on every checkout.
		 * </p>
		 *
		 * <p>
		 * One worth knowing about: {@code preserve_insertion_order=false} is faster,
		 * but lets DuckDB return the rows of an unordered query in whatever order its
		 * threads produce - which a caller comparing whole result strings cannot rely
		 * on.
		 * </p>
		 */
		private static Properties duckDbSettings() {
			Properties props = new Properties();
			for (String name : System.getProperties().stringPropertyNames()) {
				if (name.startsWith(SETTING_PREFIX) && name.length() > SETTING_PREFIX.length()) {
					String value = System.getProperty(name);
					if (value != null && !value.isBlank()) {
						props.setProperty(name.substring(SETTING_PREFIX.length()), value);
					}
				}
			}
			return props;
		}

		@Override
		public Connection getConnection(String username, String password) throws SQLException {
			// DuckDB is embedded — no authentication.
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

}
