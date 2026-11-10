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

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.duckdb.DuckDBConnection;
import org.duckdb.DuckDBDriver;
import org.eclipse.daanse.jdbc.datasource.duckdb.api.Constants;
import org.eclipse.daanse.jdbc.datasource.duckdb.api.ocd.RemoteDsConfig;

public class Util {

    private Util() {
        // constructor
    }

    private static final String URL_PREFIX = "jdbc:duckdb:";
    private static final String MEMORY_DB = ":memory:";
    /** The only scheme a remote URI may have. */
    private static final String QUACK_PREFIX = "quack:";

    // Read by the driver from the URL only, and only as its first two options.
    private static final String OPTION_SESSION_INIT_SQL_FILE = "session_init_sql_file";
    private static final String OPTION_SESSION_INIT_SQL_FILE_SHA256 = "session_init_sql_file_sha256";

    /**
     * Embedded database, {@link Constants#PID_DATASOURCE}. DuckDB is URL-based
     * ({@code jdbc:duckdb:<path>}); an empty or {@code :memory:} databaseName
     * yields an in-memory database. An option is only handed to the driver where
     * it is configured, so that the driver's own defaults - which differ for
     * {@code ducklake:} - stay in place.
     */
    public static DuckDbDataSource createDataSource(Map<String, Object> configMap) {
        String databaseName = stringValue(configMap, Constants.DATASOURCE_PROPERTY_DATABASENAME, "");
        if (databaseName.indexOf(';') >= 0) {
            // The driver reads options off the URL, where they win over everything.
            throw new IllegalArgumentException(Constants.DATASOURCE_PROPERTY_DATABASENAME
                    + " must not contain ';' - connection options have their own attributes");
        }
        if (MEMORY_DB.equals(databaseName)) {
            databaseName = "";
        }
        String url = URL_PREFIX + databaseName + sessionInitUrlOptions(configMap);

        Properties properties = clientProperties(configMap);
        if (booleanValue(configMap, Constants.DATASOURCE_PROPERTY_READ_ONLY, false)) {
            properties.setProperty(DuckDBDriver.DUCKDB_READONLY_PROPERTY, "true");
        }
        // AUTOMATIC is the engine default and stays unset — DuckDB refuses it next
        // to readOnly=true.
        String accessMode = stringValue(configMap, Constants.DATASOURCE_PROPERTY_ACCESS_MODE, "").trim();
        if (!accessMode.isEmpty() && !Constants.ACCESS_MODE_AUTOMATIC.equalsIgnoreCase(accessMode)) {
            properties.setProperty(DuckDBDriver.DUCKDB_ACCESS_MODE_PROPERTY, accessMode);
        }
        setIfConfigured(properties, DuckDBDriver.JDBC_PIN_DB, configMap, Constants.DATASOURCE_PROPERTY_PIN_DB);
        setIfConfigured(properties, DuckDBDriver.JDBC_INSTANCE_CACHE, configMap,
                Constants.DATASOURCE_PROPERTY_INSTANCE_CACHE);

        return new DuckDbDataSource(url, properties);
    }

    /**
     * Client of a remote server, {@link Constants#PID_REMOTE_DATASOURCE}: the
     * remote is attached to a private in-memory database and is the default
     * catalog of every connection handed out.
     */
    public static DuckDbDataSource createRemoteDataSource(Map<String, Object> configMap) {
        Remote remote = remote(configMap);
        // setCatalog does what USE <alias> does (schema main), without running a statement.
        return new DuckDbDataSource(URL_PREFIX, clientProperties(configMap), remote::attach,
                connection -> connection.setCatalog(remote.alias()));
    }

    /**
     * The settings and the attributes shared by both kinds of DataSource. The
     * dedicated attributes are set after the settings and win over an entry of
     * their option; callers add theirs afterwards as well.
     */
    private static Properties clientProperties(Map<String, Object> configMap) {
        Properties properties = new Properties();
        // Passed through unread; an unknown setting is DuckDB's to reject.
        for (String setting : settings(configMap)) {
            int equals = setting.indexOf('=');
            if (equals > 0) {
                properties.setProperty(setting.substring(0, equals).trim(), setting.substring(equals + 1).trim());
            }
        }
        setIfConfigured(properties, DuckDBDriver.DUCKDB_USER_AGENT_PROPERTY, configMap,
                Constants.DATASOURCE_PROPERTY_CUSTOM_USER_AGENT);
        setIfConfigured(properties, DuckDBDriver.JDBC_STREAM_RESULTS, configMap,
                Constants.DATASOURCE_PROPERTY_STREAM_RESULTS);
        setIfConfigured(properties, DuckDBDriver.JDBC_AUTO_COMMIT, configMap,
                Constants.DATASOURCE_PROPERTY_AUTO_COMMIT);
        setIfConfigured(properties, DuckDBDriver.JDBC_IGNORE_UNSUPPORTED_OPTIONS, configMap,
                Constants.DATASOURCE_PROPERTY_IGNORE_UNSUPPORTED_OPTIONS);
        setIfConfigured(properties, DuckDBDriver.JDBC_JFR_MEMORY_MONITOR, configMap,
                Constants.DATASOURCE_PROPERTY_JFR_MEMORY_MONITOR);
        return properties;
    }

    /** The configured remote server; {@code remoteUri} is required. */
    static Remote remote(Map<String, Object> configMap) {
        String uri = stringValue(configMap, Constants.DATASOURCE_PROPERTY_REMOTE_URI, "").trim();
        if (!uri.regionMatches(true, 0, QUACK_PREFIX, 0, QUACK_PREFIX.length())
                || uri.length() == QUACK_PREFIX.length()) {
            throw new IllegalArgumentException(Constants.DATASOURCE_PROPERTY_REMOTE_URI
                    + " is required and must look like " + QUACK_PREFIX + "<host>[:<port>], was: '" + uri + "'");
        }
        String alias = stringValue(configMap, Constants.DATASOURCE_PROPERTY_REMOTE_ALIAS, "").trim();
        return new Remote(uri, stringValue(configMap, Constants.DATASOURCE_PROPERTY_REMOTE_TOKEN, ""),
                alias.isEmpty() ? RemoteDsConfig.DEFAULT_REMOTE_ALIAS : alias,
                booleanValue(configMap, Constants.DATASOURCE_PROPERTY_REMOTE_DISABLE_SSL, false));
    }

    /**
     * A remote DuckDB server attached over the Quack protocol.
     *
     * @param uri        {@code quack:<host>[:<port>]}
     * @param token      authentication token; empty relies on an existing secret
     * @param alias      catalog name of the remote
     * @param disableSsl plain HTTP to a non-local host
     */
    record Remote(String uri, String token, String alias, boolean disableSsl) {

        /** Attaches the remote to the fresh, private database of the connection. */
        void attach(DuckDBConnection connection) throws SQLException {
            try (Statement statement = connection.createStatement()) {
                try {
                    // On their own, so that a failing download is reported in full.
                    statement.execute("INSTALL quack");
                    statement.execute("LOAD quack");
                } catch (SQLException e) {
                    throw failed(e);
                }
                if (!token.isEmpty()) {
                    try {
                        statement.execute("CREATE TEMPORARY SECRET daanse_remote (TYPE quack, TOKEN " + literal(token)
                                + ", SCOPE " + literal(uri) + ")");
                    } catch (SQLException e) {
                        // DuckDB may quote the statement, token included: none of its text is passed on.
                        throw new SQLException("creating the secret for remote " + uri + " failed", e.getSQLState(),
                                e.getErrorCode());
                    }
                }
                try {
                    statement.execute("ATTACH " + literal(uri) + " AS " + identifier(alias) + " (TYPE quack"
                            + (disableSsl ? ", DISABLE_SSL true" : "") + ")");
                } catch (SQLException e) {
                    throw failed(e);
                }
            }
        }

        private SQLException failed(SQLException e) {
            return new SQLException("attaching remote " + uri + " as " + alias + " failed: " + e.getMessage(),
                    e.getSQLState(), e.getErrorCode(), e);
        }

        /** Keeps the token out of logs. */
        @Override
        public String toString() {
            return "Remote[uri=" + uri + ", alias=" + alias + ", disableSsl=" + disableSsl + "]";
        }
    }

    static String literal(String value) {
        return "'" + value.replace("'", "''") + "'";
    }

    static String identifier(String value) {
        return "\"" + value.replace("\"", "\"\"") + "\"";
    }

    private static String sessionInitUrlOptions(Map<String, Object> configMap) {
        String file = urlValue(configMap, Constants.DATASOURCE_PROPERTY_SESSION_INIT_SQL_FILE);
        String sha256 = urlValue(configMap, Constants.DATASOURCE_PROPERTY_SESSION_INIT_SQL_FILE_SHA256);
        if (file.isEmpty()) {
            if (!sha256.isEmpty()) {
                throw new IllegalArgumentException(Constants.DATASOURCE_PROPERTY_SESSION_INIT_SQL_FILE_SHA256
                        + " needs " + Constants.DATASOURCE_PROPERTY_SESSION_INIT_SQL_FILE);
            }
            return "";
        }
        String options = ";" + OPTION_SESSION_INIT_SQL_FILE + "=" + file;
        return sha256.isEmpty() ? options : options + ";" + OPTION_SESSION_INIT_SQL_FILE_SHA256 + "=" + sha256;
    }

    /** The driver splits the URL at {@code ;} and {@code =} and knows no escaping. */
    private static String urlValue(Map<String, Object> configMap, String propName) {
        String value = stringValue(configMap, propName, "").trim();
        if (value.indexOf(';') >= 0 || value.indexOf('=') >= 0) {
            throw new IllegalArgumentException(propName + " must not contain ';' or '='");
        }
        return value;
    }

    private static void setIfConfigured(Properties properties, String option, Map<String, Object> configMap,
            String propName) {
        String value = stringValue(configMap, propName, "").trim();
        if (!value.isEmpty()) {
            properties.setProperty(option, value);
        }
    }

    private static List<String> settings(Map<String, Object> configMap) {
        Object value = configMap.get(Constants.DATASOURCE_PROPERTY_SETTINGS);
        if (value instanceof String[] many) {
            return List.of(many);
        }
        if (value instanceof Collection<?> collection) {
            return collection.stream().map(String::valueOf).toList();
        }
        if (value instanceof String single && !single.isBlank()) {
            return List.of(single);
        }
        return List.of();
    }

    private static String stringValue(Map<String, Object> configMap, String propName, String fallback) {
        Object value = configMap.get(propName);
        return value == null ? fallback : String.valueOf(value);
    }

    private static boolean booleanValue(Map<String, Object> configMap, String propName, boolean fallback) {
        Object value = configMap.get(propName);
        if (value instanceof Boolean bool) {
            return bool;
        }
        if (value instanceof String string) {
            return Boolean.parseBoolean(string);
        }
        return fallback;
    }
}
