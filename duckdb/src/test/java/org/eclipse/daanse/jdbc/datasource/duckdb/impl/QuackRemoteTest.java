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
*   SmartCity Jena - initial
*/
package org.eclipse.daanse.jdbc.datasource.duckdb.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.io.IOException;
import java.net.ServerSocket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import org.duckdb.DuckDBAppender;
import org.duckdb.DuckDBConnection;
import org.duckdb.DuckDBDriver;
import org.eclipse.daanse.jdbc.datasource.duckdb.api.Constants;
import org.eclipse.daanse.jdbc.datasource.duckdb.api.ocd.RemoteDsConfig;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Runs a Quack server in this JVM (a second DuckDB instance) and connects the
 * DataSource to it. Skipped when the {@code quack} extension cannot be
 * installed, for example without network access.
 */
class QuackRemoteTest {

    private static final String TOKEN = "it's-a-secret";

    private static Connection server;
    private static String uri;

    private DuckDbDataSource ds;

    @BeforeAll
    static void startServer() throws Exception {
        server = new DuckDBDriver().connect("jdbc:duckdb:memory:quack_server_" + UUID.randomUUID(), new Properties());
        uri = "quack:localhost:" + freePort();
        try {
            execute(server, "CALL quack_serve(" + Util.literal(uri) + ", token = " + Util.literal(TOKEN) + ")");
        } catch (SQLException e) {
            server.close();
            server = null;
            assumeTrue(false, "quack extension not available: " + e.getMessage());
        }
        execute(server, "CREATE TABLE t (i INTEGER, s VARCHAR)");
        execute(server, "INSERT INTO t VALUES (1, 'a'), (2, 'b'), (3, 'c')");
        execute(server, "CREATE TABLE loaded (id INTEGER, name VARCHAR, val DOUBLE)");
    }

    @AfterAll
    static void stopServer() throws SQLException {
        if (server != null) {
            execute(server, "CALL quack_stop(" + Util.literal(uri) + ")");
            server.close();
        }
    }

    @AfterEach
    void closeDataSource() throws SQLException {
        if (ds != null) {
            ds.close();
        }
    }

    @Test
    void remoteIsTheDefaultCatalogOfEveryConnection() throws SQLException {
        ds = Util.createRemoteDataSource(remoteConfig());
        try (Connection first = ds.getConnection(); Connection second = ds.getConnection()) {
            assertThat(queryString(first, "SELECT count(*) FROM t")).isEqualTo("3");
            assertThat(queryString(second, "SELECT s FROM t WHERE i = 2")).isEqualTo("b");
            assertThat(second.getCatalog()).isEqualTo(RemoteDsConfig.DEFAULT_REMOTE_ALIAS);
            assertThat(tableNames(second)).contains("t", "loaded");
        }
    }

    @Test
    void aliasAndClientOptionsApply() throws SQLException {
        Map<String, Object> config = remoteConfig();
        config.put(Constants.DATASOURCE_PROPERTY_REMOTE_ALIAS, "my srv");
        config.put(Constants.DATASOURCE_PROPERTY_AUTO_COMMIT, false);
        config.put(Constants.DATASOURCE_PROPERTY_SETTINGS, new String[] { "threads=2" });
        ds = Util.createRemoteDataSource(config);
        try (Connection connection = ds.getConnection()) {
            assertThat(connection.getCatalog()).isEqualTo("my srv");
            assertThat(connection.getAutoCommit()).isFalse();
            assertThat(queryString(connection, "SELECT current_setting('threads')")).isEqualTo("2");
            assertThat(queryString(connection, "SELECT count(*) FROM t")).isEqualTo("3");
        }
    }

    @Test
    void tokenStaysOutOfTheDatabaseList() throws SQLException {
        ds = Util.createRemoteDataSource(remoteConfig());
        try (Connection connection = ds.getConnection()) {
            assertThat(queryString(connection, "SELECT options::VARCHAR FROM duckdb_databases() WHERE database_name = "
                    + Util.literal(RemoteDsConfig.DEFAULT_REMOTE_ALIAS))).doesNotContain(TOKEN);
            assertThat(queryString(connection, "SELECT secret_string FROM duckdb_secrets() WHERE type = 'quack'"))
                    .doesNotContain(TOKEN).contains("redacted");
        }
        assertThat(Util.remote(remoteConfig())).asString().doesNotContain(TOKEN);
    }

    @Test
    void wrongTokenRefusesTheConnection() {
        Map<String, Object> config = remoteConfig();
        config.put(Constants.DATASOURCE_PROPERTY_REMOTE_TOKEN, "wrong-" + TOKEN);
        ds = Util.createRemoteDataSource(config);
        assertThatThrownBy(ds::getConnection).isInstanceOf(SQLException.class).hasMessageContaining(uri)
                .hasMessageNotContaining(TOKEN);
    }

    /**
     * Parameterized statements cannot reach a remote table; the appender cannot
     * either. Appending into a local temporary table and moving it with one
     * INSERT ... SELECT can.
     */
    @Test
    void bulkLoadThroughALocalStagingTable() throws SQLException {
        int rows = 10_000;
        ds = Util.createRemoteDataSource(remoteConfig());
        try (Connection connection = ds.getConnection()) {
            execute(connection, "CREATE TEMP TABLE stage (id INTEGER, name VARCHAR, val DOUBLE)");
            DuckDBConnection duck = connection.unwrap(DuckDBConnection.class);
            try (DuckDBAppender appender = duck.createAppender("temp", "main", "stage")) {
                for (int i = 0; i < rows; i++) {
                    appender.beginRow().append(i).append("name" + i).append(i * 0.5).endRow();
                }
            }
            execute(connection, "INSERT INTO loaded SELECT * FROM temp.main.stage");
            assertThat(queryString(server, "SELECT count(*) FROM loaded")).isEqualTo(String.valueOf(rows));
        }
    }

    @Test
    void remoteUriMustBeQuack() {
        Map<String, Object> config = remoteConfig();
        config.put(Constants.DATASOURCE_PROPERTY_REMOTE_URI, "postgres:host=db");
        assertThatThrownBy(() -> Util.createRemoteDataSource(config)).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(Constants.DATASOURCE_PROPERTY_REMOTE_URI);
    }

    @Test
    void remoteUriIsRequired() {
        Map<String, Object> config = new HashMap<>();
        config.put(Constants.DATASOURCE_PROPERTY_REMOTE_TOKEN, TOKEN);
        assertThatThrownBy(() -> Util.createRemoteDataSource(config)).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(Constants.DATASOURCE_PROPERTY_REMOTE_URI).hasMessageNotContaining(TOKEN);
    }

    private static Map<String, Object> remoteConfig() {
        Map<String, Object> config = new HashMap<>();
        config.put(Constants.DATASOURCE_PROPERTY_REMOTE_URI, uri);
        config.put(Constants.DATASOURCE_PROPERTY_REMOTE_TOKEN, TOKEN);
        return config;
    }

    private static int freePort() throws IOException {
        try (ServerSocket socket = new ServerSocket(0)) {
            return socket.getLocalPort();
        }
    }

    private static List<String> tableNames(Connection connection) throws SQLException {
        List<String> names = new ArrayList<>();
        try (ResultSet rs = connection.getMetaData().getTables(connection.getCatalog(), null, "%", null)) {
            while (rs.next()) {
                names.add(rs.getString("TABLE_NAME"));
            }
        }
        return names;
    }

    private static void execute(Connection connection, String sql) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
        }
    }

    private static String queryString(Connection connection, String sql) throws SQLException {
        try (Statement statement = connection.createStatement(); ResultSet rs = statement.executeQuery(sql)) {
            assertThat(rs.next()).isTrue();
            return rs.getString(1);
        }
    }
}
