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

import java.util.ServiceLoader;

import javax.sql.DataSource;

import org.eclipse.daanse.sql.dialect.api.Dialect;

/**
 * SPI: provides a {@link DataSource} + matching {@link Dialect}. Discovered via
 * {@link ServiceLoader}; obtain a single instance via direct construction,
 * {@link #byId(String)}, or {@link #selected()}.
 */
public interface DatabaseProvider extends AutoCloseable {

    /** The key of the one database a provider serves when nothing asks for isolation. */
    String DEFAULT_KEY = "__default__";

    String id();

    /**
     * Returns the provider's single default database. Repeated calls return
     * the same {@link ActiveDatabase}; no isolation between consumers.
     */
    ActiveDatabase activate();

    /**
     * Returns an isolated database keyed by {@code isolationKey}: distinct
     * keys yield distinct databases. The same key returns the same
     * {@link ActiveDatabase} on every call.
     *
     * <p>Default implementation ignores the key and delegates to
     * {@link #activate()} — providers that can isolate (H2 in-memory UUID;
     * Postgres/MySQL/MSSQL/MariaDB/Oracle per-schema in shared container)
     * override this to honour the key.
     */
    default ActiveDatabase activate(String isolationKey) {
        return activate();
    }

    /**
     * Releases just {@code isolationKey}'s database ahead of {@link #close()},
     * for a caller that knows a key is done for good (e.g. a per-test or
     * per-class database whose owning test is finishing) and wants its memory
     * back now rather than at provider close. A no-op if the key was never
     * activated, or was already released.
     *
     * <p>Default no-op: most providers reach an isolated database as a
     * schema/session in a shared container that is cheap to leave open until
     * {@link #close()}, so there's nothing worth releasing early. Providers
     * with an expensive per-key resource (currently {@code duckdb}, whose
     * in-memory database otherwise sits in the JVM heap for the whole run)
     * override this.
     */
    default void close(String isolationKey) {
    }

    @Override
    default void close() {
    }

    /** Look up by {@link #id()} (case-insensitive). */
    static DatabaseProvider byId(String id) {
        String want = id.toLowerCase();
        return ServiceLoader.load(DatabaseProvider.class).stream().map(ServiceLoader.Provider::get)
                .filter(p -> p.id().equals(want)).findFirst()
                .orElseThrow(() -> new IllegalStateException("No DatabaseProvider with id=" + want));
    }

    /**
     * Env-var dispatch: {@code DAANSE_TEST_DB} → {@code daanse.test.db} sys-prop →
     * default {@code "duckdb"} (embedded, in-memory). Makes it possible to run
     * tests in maven in different db by setting the env
     */
    static DatabaseProvider selected() {
        String id = System.getenv("DAANSE_TEST_DB");
        if (id == null || id.isBlank()) {
            id = System.getProperty("daanse.test.db");
        }
        if (id == null || id.isBlank()) {
            id = "duckdb";
        }
        return byId(id);
    }
}
