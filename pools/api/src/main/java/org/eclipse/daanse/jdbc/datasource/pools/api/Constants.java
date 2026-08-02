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
*   Stefan Bischof (bipolis.org) - initial
*/
package org.eclipse.daanse.jdbc.datasource.pools.api;

import org.osgi.framework.Bundle;

/**
 * Configuration property names shared by every {@link ConnectionPool}
 * implementation. The PIDs themselves live in the implementation {@link Bundle},
 * because each one is configured separately.
 */
public class Constants {

    private Constants() {
    }

    /** Marks a service as pooled; consumers can filter on it. */
    public static final String POOL_PROPERTY_KIND = "org.eclipse.daanse.jdbc.datasource.pool.kind";

    /** Target filter selecting the {@link javax.sql.DataSource} to pool. */
    public static final String POOL_PROPERTY_DATASOURCE_TARGET = "dataSource.target";

    /** Free-form name; ends up in the pool's thread names and in its metrics. */
    public static final String POOL_PROPERTY_POOL_NAME = "poolName";

    /**
     * Upper bound on physical connections. Must clear the concurrency the engine
     * actually produces - rolap runs up to
     * {@code segmentCacheManagerNumberSqlThreads} statements at once per context,
     * and several contexts may share one pool.
     */
    public static final String POOL_PROPERTY_MAX_SIZE = "maximumPoolSize";

    /** Connections kept open while idle. */
    public static final String POOL_PROPERTY_MIN_IDLE = "minimumIdle";

    /** How long {@code getConnection()} waits for a free slot, in milliseconds. */
    public static final String POOL_PROPERTY_ACQUIRE_TIMEOUT = "connectionTimeout";

    /** Idle connections are evicted after this many milliseconds. */
    public static final String POOL_PROPERTY_IDLE_TIMEOUT = "idleTimeout";

    /** A connection is retired this many milliseconds after it was opened. */
    public static final String POOL_PROPERTY_MAX_LIFETIME = "maxLifetime";

    /**
     * A connection held longer than this is reported (hikari) or reclaimed
     * (dbcp2), in milliseconds; 0 disables it. Mondrian's own pool reclaimed after
     * 300s, which is worth keeping in mind for the streaming paths that hold a
     * connection for as long as a ResultSet is being read.
     */
    public static final String POOL_PROPERTY_LEAK_THRESHOLD = "leakThreshold";

    public static final int DEFAULT_MAX_SIZE = 50;
    public static final int DEFAULT_MIN_IDLE = 5;
    public static final long DEFAULT_ACQUIRE_TIMEOUT = 30_000L;
    public static final long DEFAULT_IDLE_TIMEOUT = 600_000L;
    public static final long DEFAULT_MAX_LIFETIME = 1_800_000L;
    public static final long DEFAULT_LEAK_THRESHOLD = 300_000L;
}
