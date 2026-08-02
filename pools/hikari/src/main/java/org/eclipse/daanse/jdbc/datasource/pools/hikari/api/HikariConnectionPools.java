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
package org.eclipse.daanse.jdbc.datasource.pools.hikari.api;

import java.util.Map;

import javax.sql.DataSource;

import org.eclipse.daanse.jdbc.datasource.pools.api.ConnectionPool;
import org.eclipse.daanse.jdbc.datasource.pools.hikari.impl.HikariConnectionPool;

/**
 * Builds a {@link ConnectionPool} without Declarative Services.
 * <p>
 * In a running framework the pool arrives as a service and none of this is
 * needed. This exists for the callers that wire a context by hand - test
 * harnesses above all - so that they get the same pooling as production instead
 * of handing rolap a bare DataSource, which opens one physical connection per
 * statement.
 * <p>
 * The caller owns the returned pool and has to {@link ConnectionPool#close()} it.
 */
public final class HikariConnectionPools {

    private HikariConnectionPools() {
    }

    /**
     * @param dataSource the data source to pool
     * @param config     pool properties as in {@link org.eclipse.daanse.jdbc.datasource.pools.api.Constants};
     *                   an empty map takes every default
     */
    public static ConnectionPool create(DataSource dataSource, Map<String, Object> config) {
        return new HikariConnectionPool(dataSource, config == null ? Map.of() : config);
    }

    /** As {@link #create(DataSource, Map)} with every default. */
    public static ConnectionPool create(DataSource dataSource) {
        return create(dataSource, Map.of());
    }
}
