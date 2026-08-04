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
package org.eclipse.daanse.jdbc.datasource.testkit.api;

import java.time.Duration;

import javax.sql.DataSource;

import org.eclipse.daanse.jdbc.datasource.pools.api.ConnectionPool;
import org.eclipse.daanse.jdbc.datasource.pools.api.PoolSettings;
import org.eclipse.daanse.jdbc.datasource.pools.hikari.api.HikariConnectionPools;
import org.eclipse.daanse.sql.dialect.api.Dialect;

/**
 * A live {@link DataSource}, the matching {@link Dialect}, and a
 * {@link ConnectionPool} over that data source.
 * <p>
 * The pool is part of the record because consumers of this testkit drive rolap,
 * which opens one physical connection per statement. Handing them a bare
 * DataSource is what exhausted ephemeral ports on MariaDB, server processes on
 * Oracle and table locks on SQLite; every consumer getting a pooled handle by
 * default is the point.
 * <p>
 * The pool belongs to whoever created the record - normally the
 * {@link DatabaseProvider}, which caches its databases and should close them.
 */
public record ActiveDatabase(DataSource dataSource, Dialect dialect, ConnectionPool connectionPool) {

    /**
     * For the one database a provider serves when nothing asks for isolation.
     * The size matches {@code segmentCacheManagerNumberSqlThreads}, the fan-out
     * of a single MDX query, and stays under the server ceilings (200 on
     * PostgreSQL, 300 on MySQL and MariaDB).
     */
    public static final PoolSettings SINGLE = PoolSettings.defaults().withMaximumPoolSize(100).withMinimumIdle(1)
            .withConnectionTimeout(Duration.ofMinutes(1));

    /**
     * For a database reached under an isolation key: one live database per key,
     * each with a pool, so they share the same server ceiling.
     */
    public static final PoolSettings ISOLATED = SINGLE.withMaximumPoolSize(8);

    /** Wraps {@code dataSource} in a pool for the single unkeyed database. */
    public ActiveDatabase(DataSource dataSource, Dialect dialect) {
        this(dataSource, dialect, SINGLE);
    }

    /** Wraps {@code dataSource} in a pool with the given settings. */
    public ActiveDatabase(DataSource dataSource, Dialect dialect, PoolSettings settings) {
        this(dataSource, dialect, HikariConnectionPools.create(dataSource, settings));
    }

    /** {@link #SINGLE} for the unkeyed database, {@link #ISOLATED} for any other. */
    public static PoolSettings settingsFor(String isolationKey) {
        return isolationKey == null || DatabaseProvider.DEFAULT_KEY.equals(isolationKey) ? SINGLE : ISOLATED;
    }
}
