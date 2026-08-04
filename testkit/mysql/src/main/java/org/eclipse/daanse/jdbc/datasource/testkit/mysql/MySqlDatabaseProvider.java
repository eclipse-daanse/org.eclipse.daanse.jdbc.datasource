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
package org.eclipse.daanse.jdbc.datasource.testkit.mysql;

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
import org.eclipse.daanse.sql.dialect.db.mysql.MySqlDialect;
import org.testcontainers.containers.MySQLContainer;

import com.github.dockerjava.api.command.CreateContainerCmd;
import com.mysql.cj.jdbc.MysqlDataSource;

/**
 * MySQL provider backed by a shared Testcontainers container.
 * {@link #activate(String)} returns a database-isolated {@link ActiveDatabase}
 * per key — MySQL "schema" == "database", so each key gets {@code CREATE
 * DATABASE} and a DataSource bound to it.
 */
public class MySqlDatabaseProvider implements DatabaseProvider {

    /** Follows the current long-term release rather than pinning a version. */
    private static final String IMAGE = "mysql:lts";

    /**
     * Replaces the {@code my.cnf} Testcontainers mounts, which must go rather
     * than be overridden: it sets {@code innodb_log_file_size}, removed in MySQL
     * 9, and the server aborts on it before reading any command-line argument.
     */
    private static final String CONFIG_OVERRIDE = "daanse-mysql-conf";

    /**
     * The container's memory ceiling, and the figure the server sizes itself
     * from — {@code innodb_dedicated_server} takes 75 % of what it can see, and
     * without a limit that is the whole host.
     */
    private static final long MEMORY_LIMIT = 4L << 30;

    private static final String DEFAULT_KEY = "__default__";

    private static volatile MySQLContainer<?> container;
    private static final Object LOCK = new Object();

    private final ConcurrentMap<String, ActiveDatabase> dbsByKey = new ConcurrentHashMap<>();

    @Override
    public String id() {
        return "mysql";
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
        MySQLContainer<?> c = sharedContainer();
        String dbName = sanitize(key);
        try (Connection admin = openAdmin(c); Statement st = admin.createStatement()) {
            st.execute("CREATE DATABASE IF NOT EXISTS `" + dbName + "`");
            // The container's own user has rights on the container's own database
            // only. Granted here per database, and globally besides, so that a
            // consumer can CREATE SCHEMA - a database on this server - of its own.
            st.execute("GRANT ALL PRIVILEGES ON `" + dbName + "`.* TO '" + c.getUsername() + "'@'%'");
            st.execute("GRANT ALL PRIVILEGES ON *.* TO '" + c.getUsername() + "'@'%' WITH GRANT OPTION");
            st.execute("FLUSH PRIVILEGES");
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to create MySQL database " + dbName, e);
        }
        MysqlDataSource ds = new MysqlDataSource();
        ds.setURL(jdbcUrlWithDb(c, dbName));
        ds.setUser(c.getUsername());
        ds.setPassword(c.getPassword());
        Dialect dialect;
        try (Connection conn = ds.getConnection()) {
            dialect = new MySqlDialect(DialectInitData.fromConnection(conn));
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to build MySQL dialect for key " + key, e);
        }
        return new ActiveDatabase(ds, dialect, ActiveDatabase.settingsFor(key));
    }

    /**
     * Connects as root: creating a database and granting rights on it is beyond
     * what the container's own user may do. Testcontainers gives root the same
     * password as that user.
     */
    private static Connection openAdmin(MySQLContainer<?> c) throws SQLException {
        MysqlDataSource admin = new MysqlDataSource();
        admin.setURL(c.getJdbcUrl());
        admin.setUser("root");
        admin.setPassword(c.getPassword());
        return admin.getConnection();
    }

    private static String jdbcUrlWithDb(MySQLContainer<?> c, String dbName) {
        String url = c.getJdbcUrl();
        int q = url.indexOf('?');
        String base = q == -1 ? url : url.substring(0, q);
        String tail = q == -1 ? "" : url.substring(q);
        int slash = base.lastIndexOf('/');
        if (slash > "jdbc:mysql://".length()) {
            base = base.substring(0, slash + 1) + dbName;
        }
        return base + tail;
    }

    /** MySQL identifier: alphanumeric + _; max 64 chars. */
    private static String sanitize(String k) {
        StringBuilder sb = new StringBuilder();
        for (char ch : k.toCharArray()) {
            sb.append(Character.isLetterOrDigit(ch) || ch == '_' ? ch : '_');
        }
        String s = sb.toString();
        return s.length() > 64 ? s.substring(0, 64) : s;
    }

    /** Typed here because a lambda on the raw {@link MySQLContainer} would be erased. */
    private static Consumer<CreateContainerCmd> memoryLimit() {
        return cmd -> cmd.getHostConfig().withMemory(MEMORY_LIMIT).withMemorySwap(MEMORY_LIMIT);
    }

    @SuppressWarnings("resource")
    private static MySQLContainer<?> sharedContainer() {
        MySQLContainer<?> c = container;
        if (c != null) {
            return c;
        }
        synchronized (LOCK) {
            if (container == null) {
                @SuppressWarnings("rawtypes")
                MySQLContainer my = new MySQLContainer(IMAGE);
                my.withConfigurationOverride(CONFIG_OVERRIDE);
                my.withCreateContainerCmdModifier(memoryLimit());
                my.start();
                container = my;
                Runtime.getRuntime().addShutdownHook(new Thread(my::close, "daanse-mysql-stop"));
            }
            return container;
        }
    }
}
