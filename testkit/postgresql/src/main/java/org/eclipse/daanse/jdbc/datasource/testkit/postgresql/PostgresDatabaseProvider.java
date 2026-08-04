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
package org.eclipse.daanse.jdbc.datasource.testkit.postgresql;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Consumer;

import org.eclipse.daanse.jdbc.datasource.testkit.api.ActiveDatabase;
import org.eclipse.daanse.jdbc.datasource.testkit.api.DatabaseProvider;
import org.eclipse.daanse.sql.dialect.api.Dialect;
import org.eclipse.daanse.sql.dialect.api.DialectInitData;
import org.eclipse.daanse.sql.dialect.db.postgresql.PostgreSqlDialect;
import org.postgresql.ds.PGSimpleDataSource;
import org.testcontainers.containers.PostgreSQLContainer;

import com.github.dockerjava.api.command.CreateContainerCmd;

/**
 * PostgreSQL provider backed by a Testcontainers container. A single container
 * is shared for the JVM lifetime; {@link #activate(String)} returns a
 * schema-isolated {@link ActiveDatabase} per key (CREATE SCHEMA + DataSource
 * with {@code currentSchema=<key>}).
 */
public class PostgresDatabaseProvider implements DatabaseProvider {

    private static final String IMAGE = "postgres:16-alpine";

    /**
     * Testcontainers hands PostgreSQL no configuration file, so it otherwise runs
     * on the image defaults — 128 MB of shared buffers, 4 MB of work memory. The
     * durability settings hold because the container is discarded when the run
     * ends.
     */
    private static final String[] SERVER_ARGS = { "postgres",
            // Testcontainers' own setting, kept because setCommand replaces it.
            "-c", "fsync=off",
            // Cache: FoodMart is around 250 MB, so 2 GB holds all of it.
            "-c", "shared_buffers=2GB", "-c", "effective_cache_size=6GB",
            // Per connection AND per sort node, so it multiplies by the pool.
            "-c", "work_mem=16MB",
            // Index building; taken once per build, not per connection.
            "-c", "maintenance_work_mem=512MB",
            // Bulk load: a 1 GB WAL ceiling forces a checkpoint mid-import.
            "-c", "max_wal_size=8GB", "-c", "checkpoint_timeout=30min", "-c", "synchronous_commit=off", "-c",
            "full_page_writes=off",
            // SSD, not the spinning disk the default assumes.
            "-c", "random_page_cost=1.1",
            // Headroom for the suite's parallel test threads.
            "-c", "max_connections=200", "-c", "max_worker_processes=16", "-c", "max_parallel_workers=16", "-c",
            "max_parallel_workers_per_gather=4" };

    private static final String DEFAULT_KEY = "__default__";

    private static volatile PostgreSQLContainer<?> container;
    private static final Object LOCK = new Object();

    private final ConcurrentMap<String, ActiveDatabase> dbsByKey = new ConcurrentHashMap<>();

    @Override
    public String id() {
        return "postgres";
    }

    @Override
    public ActiveDatabase activate() {
        return activate(DEFAULT_KEY);
    }

    @Override
    public ActiveDatabase activate(String isolationKey) {
        return dbsByKey.computeIfAbsent(isolationKey, this::newDatabaseForKey);
    }

    private ActiveDatabase newDatabaseForKey(String key) {
        PostgreSQLContainer<?> c = sharedContainer();
        String schema = sanitize(key);
        try (Connection admin = openAdmin(c); Statement st = admin.createStatement()) {
            st.execute("CREATE SCHEMA IF NOT EXISTS \"" + schema + "\"");
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to create PG schema " + schema, e);
        }
        PGSimpleDataSource ds = new PGSimpleDataSource();
        // Append currentSchema so all statements default to the per-key schema.
        String url = c.getJdbcUrl();
        url += (url.contains("?") ? "&" : "?") + "currentSchema=" + schema;
        ds.setUrl(url);
        ds.setUser(c.getUsername());
        ds.setPassword(c.getPassword());
        Dialect dialect;
        try (Connection conn = ds.getConnection()) {
            dialect = new PostgreSqlDialect(DialectInitData.fromConnection(conn));
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to build PostgreSQL dialect for key " + key, e);
        }
        return new ActiveDatabase(ds, dialect, ActiveDatabase.settingsFor(key));
    }

    private static Connection openAdmin(PostgreSQLContainer<?> c) throws SQLException {
        PGSimpleDataSource admin = new PGSimpleDataSource();
        admin.setUrl(c.getJdbcUrl());
        admin.setUser(c.getUsername());
        admin.setPassword(c.getPassword());
        return admin.getConnection();
    }

    /** PG identifier: alphanumeric + _; lowercased; max 63 chars. */
    private static String sanitize(String k) {
        StringBuilder sb = new StringBuilder();
        for (char ch : k.toLowerCase().toCharArray()) {
            sb.append(Character.isLetterOrDigit(ch) || ch == '_' ? ch : '_');
        }
        String s = sb.toString();
        return s.length() > 63 ? s.substring(0, 63) : s;
    }

    /**
     * The container's memory ceiling: shared buffers plus the per-connection work
     * memory that the pool multiplies.
     */
    private static final long MEMORY_LIMIT = 4L << 30;

    /** Typed here because a lambda on the raw {@link PostgreSQLContainer} would be erased. */
    private static Consumer<CreateContainerCmd> memoryLimit() {
        return cmd -> cmd.getHostConfig().withMemory(MEMORY_LIMIT).withMemorySwap(MEMORY_LIMIT);
    }

    @SuppressWarnings("resource")
    private static PostgreSQLContainer<?> sharedContainer() {
        PostgreSQLContainer<?> c = container;
        if (c != null) {
            return c;
        }
        synchronized (LOCK) {
            if (container == null) {
                @SuppressWarnings("rawtypes")
                PostgreSQLContainer pg = new PostgreSQLContainer(IMAGE);
                pg.setCommand(SERVER_ARGS);
                // Parallel query places its worker state in POSIX shared memory,
                // which in a container is /dev/shm — 64 MB by default, and a
                // worker that cannot get a segment does not start. This is RAM.
                pg.withSharedMemorySize(1024L * 1024 * 1024);
                pg.withCreateContainerCmdModifier(memoryLimit());
                pg.start();
                container = pg;
                Runtime.getRuntime().addShutdownHook(new Thread(pg::close, "daanse-pg-stop"));
            }
            return container;
        }
    }
}
