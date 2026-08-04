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
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
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
 * its own private, empty database, so this provider connects ONCE per database
 * and hands out {@link DuckDBConnection#duplicate()} siblings of that connection
 * — they all share the one in-memory database, and it lives as long as the
 * keeper does.
 *
 * <p>
 * {@link #activate()} returns the provider's single default database;
 * {@link #activate(String)} returns one database per distinct key. Each is its
 * own {@code jdbc:duckdb:} instance, which is what isolates them — and what
 * gives each its own engine thread pool, so the {@code threads} setting
 * multiplies by the number of keys in use.
 * </p>
 */
public class DuckDbDatabaseProvider implements DatabaseProvider {

	// java.util.logging.Logger is imported for the DataSource shim below, so the
	// slf4j class logger is declared with its fully qualified type.
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(DuckDbDatabaseProvider.class);

	private static final String URL = "jdbc:duckdb:";

	private static final String DEFAULT_KEY = "__default__";


	private final ConcurrentMap<String, ActiveDatabase> dbsByKey = new ConcurrentHashMap<>();

	@Override
	public String id() {
		return "duckdb";
	}

	@Override
	public void close() {
		for (ActiveDatabase database : dbsByKey.values()) {
			// The pool first: it holds duplicate() connections, and the keeper is what
			// keeps their database alive. Closing the keeper drops the in-memory
			// database; there is no file to unlink.
			try {
				database.connectionPool().close();
			} catch (RuntimeException e) {
				LOGGER.warn("closing a duckdb connection pool failed", e);
			}
			if (database.dataSource() instanceof DuckDbDataSource duckDbDataSource) {
				duckDbDataSource.close();
			}
		}
		dbsByKey.clear();
	}

	@Override
	public ActiveDatabase activate() {
		return activate(DEFAULT_KEY);
	}

	@Override
	public ActiveDatabase activate(String isolationKey) {
		return dbsByKey.computeIfAbsent(isolationKey, this::newDatabase);
	}

	private ActiveDatabase newDatabase(String key) {
		long tActivate = System.nanoTime();
		DuckDbDataSource dataSource = new DuckDbDataSource(URL);
		try (Connection connection = dataSource.getConnection()) {
			Dialect dialect = new DuckDbDialect(DialectInitData.fromConnection(connection));
			// embedded: db-ready = the (tiny) connect duration, for comparability with docker DBs
			long ms = (System.nanoTime() - tActivate) / 1_000_000;
			LOGGER.warn("DBTIMING db={} phase=db-ready ms={}", id(), ms);
			LOGGER.warn("DBTIMING db={} phase=context-first ms={} detail=fresh,epoch={}", id(), ms,
					System.currentTimeMillis() / 1000);
			return new ActiveDatabase(dataSource, dialect, ActiveDatabase.settingsFor(key));
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
			if (keeper == null) {
				synchronized (this) {
					if (keeper == null) {
						keeper = (DuckDBConnection) driver.connect(url, duckDbSettings());
					}
				}
			}
			// A fresh connect would open a fresh, empty in-memory database. duplicate()
			// returns an independent connection onto the keeper's database.
			return keeper.duplicate();
		}

		/**
		 * The engine settings this provider connects with, chosen for the kind of
		 * run a testkit sees: many small queries on a machine that is busy with the
		 * rest of the suite. They are connection properties rather than a
		 * {@code SET} per checkout, which would cost a round trip each time.
		 *
		 * <p>
		 * <b>{@code preserve_insertion_order=false} is the one to know about:</b> it
		 * lets DuckDB return the rows of a query without ORDER BY in whatever order
		 * its threads produce, so a caller comparing whole result strings can see two
		 * rows swapped between runs. It costs roughly a third of the runtime to turn
		 * back on.
		 */
		private static Properties duckDbSettings() {
			Properties props = new Properties();
			// One thread per core, DuckDB's default, spends more on coordination than
			// it wins on queries this small.
			props.setProperty("threads", "2");
			props.setProperty("preserve_insertion_order", "false");
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
