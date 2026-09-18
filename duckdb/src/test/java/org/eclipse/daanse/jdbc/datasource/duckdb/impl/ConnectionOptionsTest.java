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

import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.HexFormat;
import java.util.Map;

import org.duckdb.DuckDBDriver;
import org.eclipse.daanse.jdbc.datasource.duckdb.api.Constants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class ConnectionOptionsTest {

    @TempDir
    Path dir;

    @Test
    void semicolonInDatabaseNameIsRejected() {
        Map<String, Object> config = Map.of(Constants.DATASOURCE_PROPERTY_DATABASENAME,
                dir.resolve("a.duckdb") + ";access_mode=READ_WRITE");
        assertThatThrownBy(() -> Util.createDataSource(config)).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(Constants.DATASOURCE_PROPERTY_DATABASENAME);
    }

    @Test
    void autoCommitAppliesToEveryConnection() throws SQLException {
        DuckDbDataSource ds = Util.createDataSource(config(Constants.DATASOURCE_PROPERTY_AUTO_COMMIT, false));
        try (Connection first = ds.getConnection(); Connection second = ds.getConnection()) {
            assertThat(first.getAutoCommit()).isFalse();
            assertThat(second.getAutoCommit()).isFalse();
        } finally {
            ds.close();
        }
    }

    @Test
    void autoCommitIsOnByDefault() throws SQLException {
        DuckDbDataSource ds = Util.createDataSource(config());
        try (Connection connection = ds.getConnection()) {
            assertThat(connection.getAutoCommit()).isTrue();
        } finally {
            ds.close();
        }
    }

    @Test
    void attributeWinsOverSettingsEntry() throws SQLException {
        DuckDbDataSource ds = Util.createDataSource(config(Constants.DATASOURCE_PROPERTY_AUTO_COMMIT, false,
                Constants.DATASOURCE_PROPERTY_SETTINGS, new String[] { DuckDBDriver.JDBC_AUTO_COMMIT + "=true" }));
        try (Connection connection = ds.getConnection()) {
            assertThat(connection.getAutoCommit()).isFalse();
        } finally {
            ds.close();
        }
    }

    @Test
    void engineSettingsAndUserAgentReachTheDatabase() throws SQLException {
        DuckDbDataSource ds = Util.createDataSource(config(Constants.DATASOURCE_PROPERTY_CUSTOM_USER_AGENT, "daanse",
                Constants.DATASOURCE_PROPERTY_STREAM_RESULTS, true, Constants.DATASOURCE_PROPERTY_INSTANCE_CACHE,
                false, Constants.DATASOURCE_PROPERTY_SETTINGS, new String[] { "threads=2" }));
        try (Connection connection = ds.getConnection()) {
            assertThat(queryString(connection, "SELECT current_setting('threads')")).isEqualTo("2");
            assertThat(queryString(connection, "PRAGMA user_agent")).contains("daanse");
        } finally {
            ds.close();
        }
    }

    @Test
    void unknownSettingIsRefusedUnlessIgnored() throws SQLException {
        String[] unknown = { "no_such_duckdb_setting=1" };
        DuckDbDataSource refusing = Util.createDataSource(config(Constants.DATASOURCE_PROPERTY_SETTINGS, unknown));
        assertThatThrownBy(refusing::getConnection).isInstanceOf(SQLException.class);

        DuckDbDataSource ignoring = Util.createDataSource(config(Constants.DATASOURCE_PROPERTY_SETTINGS, unknown,
                Constants.DATASOURCE_PROPERTY_IGNORE_UNSUPPORTED_OPTIONS, true));
        try (Connection connection = ignoring.getConnection()) {
            assertThat(connection.isValid(1)).isTrue();
        } finally {
            ignoring.close();
        }
    }

    @Test
    void sessionInitSqlRunsOncePerDatabaseAndForEveryConnection() throws Exception {
        Path init = dir.resolve("init.sql");
        Files.writeString(init, """
                CREATE TABLE init_runs (i INTEGER);
                /* DUCKDB_CONNECTION_INIT_BELOW_MARKER */
                INSERT INTO init_runs VALUES (1);
                """);
        DuckDbDataSource ds = Util.createDataSource(config(Constants.DATASOURCE_PROPERTY_SESSION_INIT_SQL_FILE,
                init.toString(), Constants.DATASOURCE_PROPERTY_SESSION_INIT_SQL_FILE_SHA256, sha256(init)));
        try (Connection first = ds.getConnection(); Connection second = ds.getConnection()) {
            // the connection the DataSource holds the database with, and the two handed out
            assertThat(queryString(second, "SELECT count(*) FROM init_runs")).isEqualTo("3");
        } finally {
            ds.close();
        }
    }

    @Test
    void sessionInitSqlFileWithWrongDigestIsRefused() throws Exception {
        Path init = dir.resolve("init.sql");
        Files.writeString(init, "CREATE TABLE t (i INTEGER);");
        DuckDbDataSource ds = Util.createDataSource(config(Constants.DATASOURCE_PROPERTY_SESSION_INIT_SQL_FILE,
                init.toString(), Constants.DATASOURCE_PROPERTY_SESSION_INIT_SQL_FILE_SHA256, "00"));
        assertThatThrownBy(ds::getConnection).isInstanceOf(SQLException.class).hasMessageContaining("SHA-256");
    }

    @Test
    void digestWithoutSessionInitSqlFileIsRejected() {
        Map<String, Object> config = config(Constants.DATASOURCE_PROPERTY_SESSION_INIT_SQL_FILE_SHA256, "00");
        assertThatThrownBy(() -> Util.createDataSource(config)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void sessionInitSqlFileMustNotCarryUrlOptions() {
        Map<String, Object> config = config(Constants.DATASOURCE_PROPERTY_SESSION_INIT_SQL_FILE,
                "init.sql;access_mode=READ_WRITE");
        assertThatThrownBy(() -> Util.createDataSource(config)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void closeReleasesAPinnedDatabase() throws SQLException {
        DuckDbDataSource ds = Util.createDataSource(config(Constants.DATASOURCE_PROPERTY_PIN_DB, true));
        try (Connection connection = ds.getConnection()) {
            assertThat(connection.isValid(1)).isTrue();
        }
        ds.close();
        // nothing left to release: close() did it
        assertThat(DuckDBDriver.releaseDB("jdbc:duckdb:" + databaseFile())).isFalse();
    }

    private Path databaseFile() {
        return dir.resolve("test.duckdb");
    }

    private Map<String, Object> config(Object... keysAndValues) {
        Map<String, Object> config = new HashMap<>();
        config.put(Constants.DATASOURCE_PROPERTY_DATABASENAME, databaseFile().toString());
        for (int i = 0; i < keysAndValues.length; i += 2) {
            config.put((String) keysAndValues[i], keysAndValues[i + 1]);
        }
        return config;
    }

    private static String queryString(Connection connection, String sql) throws SQLException {
        try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sql)) {
            assertThat(resultSet.next()).isTrue();
            return resultSet.getString(1);
        }
    }

    private static String sha256(Path file) throws Exception {
        return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(Files.readAllBytes(file)));
    }
}
