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
import org.eclipse.daanse.jdbc.datasource.testkit.api.DatabaseProvider;
import org.eclipse.daanse.jdbc.db.dialect.api.Dialect;
import org.eclipse.daanse.jdbc.db.dialect.api.DialectInitData;
import org.eclipse.daanse.jdbc.db.dialect.db.oracle.OracleDialect;
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
        String user = sanitize(key);
        String pwd = user + "_pwd";
        try (Connection admin = openAdmin(c); Statement st = admin.createStatement()) {
            // Best-effort create — ignore if user already exists.
            try { st.execute("CREATE USER " + user + " IDENTIFIED BY \"" + pwd + "\""); } catch (SQLException ignore) {}
            try { st.execute("GRANT CREATE SESSION, RESOURCE, UNLIMITED TABLESPACE TO " + user); } catch (SQLException ignore) {}
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to create Oracle user " + user, e);
        }
        try {
            OracleDataSource ds = new OracleDataSource();
            ds.setURL(c.getJdbcUrl());
            ds.setUser(user);
            ds.setPassword(pwd);
            Dialect dialect;
            try (Connection conn = ds.getConnection()) {
                dialect = new OracleDialect(DialectInitData.fromConnection(conn));
            }
            return new ActiveDatabase(ds, dialect);
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to build Oracle dialect for key " + key, e);
        }
    }

    private static Connection openAdmin(OracleContainer c) throws SQLException {
        try {
            OracleDataSource admin = new OracleDataSource();
            admin.setURL(c.getJdbcUrl());
            admin.setUser(c.getUsername());
            admin.setPassword(c.getPassword());
            return admin.getConnection();
        } catch (SQLException e) { throw e; }
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
                container = o;
                Runtime.getRuntime().addShutdownHook(new Thread(o::close, "daanse-oracle-stop"));
            }
            return container;
        }
    }
}
