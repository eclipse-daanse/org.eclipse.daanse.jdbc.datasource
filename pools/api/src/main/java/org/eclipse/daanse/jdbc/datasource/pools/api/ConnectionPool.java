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

import java.sql.Connection;
import java.sql.SQLException;
import java.time.Duration;

import javax.sql.DataSource;

import org.osgi.annotation.versioning.ProviderType;

/**
 * A pool of physical connections over a {@link DataSource}, published as its own
 * OSGi service type.
 * <p>
 * Deliberately not a {@link DataSource}: a consumer that binds a
 * {@code ConnectionPool} cannot accidentally end up with an unpooled data source,
 * whatever the target filter says. The distinction is carried by the type, not by
 * configuration.
 * <p>
 * Implementations must block while the pool is exhausted rather than opening
 * another physical connection - the whole point of the cap is that the database
 * never sees more connections than it was told to expect.
 */
@ProviderType
public interface ConnectionPool extends AutoCloseable {

    /**
     * Releases every physical connection the pool holds.
     * <p>
     * Under Declarative Services the component lifecycle takes care of this. It is
     * declared for consumers that build a pool themselves - test harnesses and
     * embedded use - because a pool that is dropped without being closed keeps its
     * connections open on the database.
     */
    @Override
    void close();

    /**
     * Takes a connection out of the pool, waiting for a free slot if the pool is
     * exhausted. Closing the returned connection hands the slot back.
     *
     * @throws SQLException if no connection became available within
     *                      {@link #acquireTimeout()}
     */
    Connection getConnection() throws SQLException;

    /**
     * As {@link #getConnection()}, but gives up after {@code timeout} even when the
     * pool would wait longer. Callers that run under a statement deadline pass
     * their remaining budget here, so a queue wait cannot silently outlive the
     * query it belongs to.
     */
    Connection getConnection(Duration timeout) throws SQLException;

    /**
     * The pooled {@link DataSource} view, for consumers that only pass a data
     * source on instead of opening connections themselves.
     */
    DataSource dataSource();

    /** How long {@link #getConnection()} waits before giving up. */
    Duration acquireTimeout();

    /** Upper bound on physical connections this pool opens. */
    int maxSize();

    /** Physical connections currently handed out. Diagnostics only. */
    int activeSize();
}
