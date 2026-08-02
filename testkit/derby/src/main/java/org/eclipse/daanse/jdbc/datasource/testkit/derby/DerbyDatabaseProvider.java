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
package org.eclipse.daanse.jdbc.datasource.testkit.derby;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

import javax.sql.DataSource;

import org.apache.derby.jdbc.EmbeddedDataSource;
import org.eclipse.daanse.jdbc.datasource.testkit.api.ActiveDatabase;
import org.eclipse.daanse.jdbc.datasource.testkit.api.DatabaseProvider;
import org.eclipse.daanse.sql.dialect.api.Dialect;
import org.eclipse.daanse.sql.dialect.api.DialectInitData;
import org.eclipse.daanse.sql.dialect.db.derby.DerbyDialect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Embedded Apache Derby, in-memory ({@code memory:foodmart-<UUID>}). The
 * in-memory database lives for the whole JVM regardless of open connections.
 */
public class DerbyDatabaseProvider implements DatabaseProvider {

	private static final Logger LOGGER = LoggerFactory.getLogger(DerbyDatabaseProvider.class);

	private final String databaseName = "memory:foodmart-" + UUID.randomUUID();

	@Override
	public String id() {
		return "derby";
	}

	@Override
	public void close() {
	}

	/**
	 * Derby reads these once, when the engine boots on the first connection, so they have to be
	 * in place before {@link EmbeddedDataSource#getConnection()} is called. The page cache
	 * defaults to 1000 pages (~4 MB) — FoodMart does not fit, so every aggregate scan walks back
	 * through the pager. {@code durability=test} skips the log syncs, which buy nothing for a
	 * database that lives and dies with the JVM.
	 */
	private static void bootProperties() {
		setIfAbsent("derby.storage.pageCacheSize", "20000");
		setIfAbsent("derby.system.durability", "test");
		setIfAbsent("derby.language.statementCacheSize", "500");
	}

	private static void setIfAbsent(String key, String value) {
		if (System.getProperty(key) == null) {
			System.setProperty(key, value);
		}
	}

	@Override
	public ActiveDatabase activate() {
		long tActivate = System.nanoTime();
		bootProperties();
		EmbeddedDataSource dataSource = new EmbeddedDataSource();
		dataSource.setDatabaseName(databaseName);
		dataSource.setCreateDatabase("create");
		try {
			Connection connection = dataSource.getConnection();
			Dialect dialect = new DerbyDialect(DialectInitData.fromConnection(connection));
			connection.close();
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

}
