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
package org.eclipse.daanse.jdbc.datasource.testkit.oracle;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import oracle.jdbc.pool.OracleDataSource;
import org.eclipse.daanse.jdbc.datasource.testkit.api.ActiveDatabase;
import org.eclipse.daanse.jdbc.datasource.pools.api.PoolSettings;
import org.eclipse.daanse.jdbc.datasource.testkit.api.DatabaseProvider;
import org.eclipse.daanse.sql.dialect.api.Dialect;
import org.eclipse.daanse.sql.dialect.api.DialectInitData;
import org.eclipse.daanse.sql.dialect.db.oracle.OracleDialect;
import org.testcontainers.containers.Container;
import org.testcontainers.oracle.OracleContainer;

/**
 * Oracle provider backed by a shared Testcontainers container.
 * {@link #activate(String)} returns per-key USER-isolated
 * {@link ActiveDatabase} — Oracle has one user == one schema, so the test
 * harness gets clean namespace per key.
 */
public class OracleDatabaseProvider implements DatabaseProvider {

    private static final String IMAGE = "gvenzl/oracle-free:23-slim-faststart";
    private static final String DEFAULT_KEY = "__default__";

    /**
     * How many processes the test instance allows; {@code 0} keeps the image's own setting (200). The image's 200 leave
     * about 118 for connections once the background processes run, and a test suite that opens and closes a pool per
     * class makes the listener refuse with {@code ORA-12516} although the instance is far from full: it counts the
     * connections it handed out and learns late that they closed (measured 17.10.2026: 101 of 320 connections refused
     * at 200, none at 1000). This is our own test database, so we may set it; the product has to cope with whatever a
     * customer's database is set to. Not more than needed: every process takes memory of the image's 1.5 GB SGA, and
     * at 1000 a full run left 16 MB of it free (18.10.2026) -- 400 is the idle 82 plus room for the burst.
     */
    public static final String PROCESSES_PROPERTY = "daanse.testkit.oracle.processes";
    static final int DEFAULT_PROCESSES = 400;

    /**
     * How long a test connection waits for the server's answer before it gives up, in milliseconds; {@code 0} waits
     * forever. A connection the server no longer serves kept a full run waiting for two hours in a socket read (18.10.,
     * a CSV import in {@code aggtable}); with a bound the test fails and the run goes on. Long enough for a slow load.
     */
    public static final String READ_TIMEOUT_PROPERTY = "daanse.testkit.oracle.readTimeoutMillis";
    static final long DEFAULT_READ_TIMEOUT_MILLIS = 15 * 60 * 1000L;

    private static volatile OracleContainer container;
    private static final Object LOCK = new Object();

    private final ConcurrentMap<String, ActiveDatabase> dbsByKey = new ConcurrentHashMap<>();

    @Override
    public String id() {
        return "oracle";
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
        OracleContainer c = sharedContainer();
        // Oracle: schema == user. The default key reuses the container's own
        // application user (and thus its schema); any other key gets an
        // isolated user created via the privileged SYSTEM account.
        if (DEFAULT_KEY.equals(key)) {
            return buildDatabase(c, key, c.getUsername(), c.getPassword());
        }
        String user = sanitize(key);
        String pwd = user + "_pwd";
        try (Connection admin = openAdmin(c); Statement st = admin.createStatement()) {
            // Best-effort create — ignore if user already exists.
            try { st.execute("CREATE USER " + user + " IDENTIFIED BY \"" + pwd + "\""); } catch (SQLException ignore) {}
            try { st.execute("GRANT CREATE SESSION, RESOURCE, UNLIMITED TABLESPACE TO " + user); } catch (SQLException ignore) {}
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to create Oracle user " + user, e);
        }
        return buildDatabase(c, key, user, pwd);
    }

    private static ActiveDatabase buildDatabase(OracleContainer c, String key, String user, String pwd) {
        try {
            OracleDataSource ds = new OracleDataSource();
            ds.setURL(c.getJdbcUrl());
            ds.setUser(user);
            ds.setPassword(pwd);
            ds.setConnectionProperties(readTimeout());
            Dialect dialect;
            PoolSettings poolSettings;
            try (Connection conn = ds.getConnection()) {
                dialect = new OracleDialect(DialectInitData.fromConnection(conn));
                poolSettings = poolSettingsFor(conn, key);
            }
            return new ActiveDatabase(ds, dialect, poolSettings);
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to build Oracle dialect for key " + key, e);
        }
    }

    /**
     * How many connections this instance will serve. Oracle's session count is a
     * hard instance parameter: past {@code processes} a connection does not wait,
     * it fails with {@code ORA-12516}. {@code getMaxConnections()} answers 0 for
     * "unknown or unlimited", so the fallback carries the number.
     */
    private static PoolSettings poolSettingsFor(Connection conn, String key) {
        int oracleMax = 40;
        try {
            int reported = conn.getMetaData().getMaxConnections();
            if (reported > 0) {
                oracleMax = Math.max(8, Math.min(100, reported * 3 / 4));
            }
        } catch (SQLException | RuntimeException e) {
            // An unanswered question is the same as an answer of 0.
        }
        // A keyed database shares the instance with its siblings, so it takes
        // whichever share is smaller.
        PoolSettings settings = ActiveDatabase.settingsFor(key);
        return settings.withMaximumPoolSize(Math.min(settings.maximumPoolSize(), oracleMax));
    }

    private static Connection openAdmin(OracleContainer c) throws SQLException {
        // CREATE USER requires the privileged SYSTEM account — the container's
        // application user (getUsername()) cannot create users. The SYSTEM
        // password equals the configured container password.
        OracleDataSource admin = new OracleDataSource();
        admin.setURL(c.getJdbcUrl());
        admin.setUser("system");
        admin.setPassword(c.getPassword());
        admin.setConnectionProperties(readTimeout());
        return admin.getConnection();
    }

    /** The driver's read timeout for a test connection, as {@link #READ_TIMEOUT_PROPERTY} sets it. */
    static java.util.Properties readTimeout() {
        java.util.Properties properties = new java.util.Properties();
        long millis = Long.getLong(READ_TIMEOUT_PROPERTY, DEFAULT_READ_TIMEOUT_MILLIS);
        if (millis > 0) {
            properties.setProperty("oracle.jdbc.ReadTimeout", Long.toString(millis));
        }
        return properties;
    }

    /**
     * Sets {@code processes} and restarts the instance, which reads the parameter only when it starts; then waits until
     * the application user connects through the listener again.
     *
     * @param processes the number, or {@code 0} or less to leave the image's setting alone
     */
    static void configureProcesses(OracleContainer c, int processes) {
        if (processes <= 0) {
            return;
        }
        String script = "sqlplus -s / as sysdba <<'SQL'\n"
                + "WHENEVER SQLERROR EXIT FAILURE\n"
                + "ALTER SYSTEM SET processes=" + processes + " SCOPE=SPFILE;\n"
                + "SHUTDOWN IMMEDIATE;\n"
                + "STARTUP;\n"
                + "ALTER PLUGGABLE DATABASE ALL OPEN;\n"
                + "SQL\n";
        try {
            Container.ExecResult result = c.execInContainer("bash", "-c", script);
            if (result.getExitCode() != 0) {
                throw new IllegalStateException("setting processes=" + processes + " failed: " + result.getStdout()
                        + result.getStderr());
            }
        } catch (java.io.IOException e) {
            throw new IllegalStateException("setting processes=" + processes + " failed", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("interrupted while setting processes=" + processes, e);
        }
        awaitConnectable(c);
    }

    /** Until the restarted instance has registered its service with the listener again, a connection is refused. */
    private static void awaitConnectable(OracleContainer c) {
        long deadline = System.nanoTime() + java.util.concurrent.TimeUnit.SECONDS.toNanos(120);
        SQLException last = null;
        while (System.nanoTime() < deadline) {
            try {
                OracleDataSource ds = new OracleDataSource();
                ds.setURL(c.getJdbcUrl());
                ds.setUser(c.getUsername());
                ds.setPassword(c.getPassword());
                try (Connection conn = ds.getConnection()) {
                    return;
                }
            } catch (SQLException e) {
                last = e;
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new IllegalStateException("interrupted while waiting for the restarted instance", e);
            }
        }
        throw new IllegalStateException("the restarted Oracle instance did not accept a connection within 120 s", last);
    }

    private static String sanitize(String k) {
        StringBuilder sb = new StringBuilder();
        for (char ch : k.toUpperCase().toCharArray()) {
            sb.append(Character.isLetterOrDigit(ch) || ch == '_' ? ch : '_');
        }
        // Oracle identifiers must start with a letter
        String s = sb.toString();
        if (s.isEmpty() || !Character.isLetter(s.charAt(0))) s = "U_" + s;
        return s.length() > 30 ? s.substring(0, 30) : s;
    }

    @SuppressWarnings("resource")
    private static OracleContainer sharedContainer() {
        OracleContainer c = container;
        if (c != null) {
            return c;
        }
        synchronized (LOCK) {
            if (container == null) {
                OracleContainer o = new OracleContainer(IMAGE).withUsername("rt").withPassword("rt");
                o.start();
                configureProcesses(o, Integer.getInteger(PROCESSES_PROPERTY, DEFAULT_PROCESSES));
                container = o;
                Runtime.getRuntime().addShutdownHook(new Thread(o::close, "daanse-oracle-stop"));
            }
            return container;
        }
    }
}
