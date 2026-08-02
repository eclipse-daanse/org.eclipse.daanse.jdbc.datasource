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

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.eclipse.daanse.jdbc.datasource.pools.api.ConnectionPool;
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

    /** Wraps {@code dataSource} in a pool. */
    public ActiveDatabase(DataSource dataSource, Dialect dialect) {
        this(dataSource, dialect, HikariConnectionPools.create(dataSource, poolConfig()));
    }

    /**
     * Pool settings for test runs, overridable per run:
     * {@code -Ddaanse.test.pool.maxSize=30 -Ddaanse.test.pool.minIdle=1}.
     */
    private static Map<String, Object> poolConfig() {
        Map<String, Object> config = new HashMap<>();
        config.put("maximumPoolSize", Integer.getInteger("daanse.test.pool.maxSize", 30));
        config.put("minimumIdle", Integer.getInteger("daanse.test.pool.minIdle", 1));
        config.put("connectionTimeout", Long.getLong("daanse.test.pool.timeoutMs", 60_000L));
        return config;
    }
}
