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
package org.eclipse.daanse.jdbc.datasource.duckdb.api;

import javax.sql.DataSource;

import org.eclipse.daanse.jdbc.datasource.common.annotation.prototype.DataSourceMetaData;
import org.osgi.framework.Bundle;

/**
 * Constants of this {@link Bundle}.
 */
public class Constants {

    private Constants() {
    }

    /**
     * Constant for the {@link DataSourceMetaData#subprotocol()}
     */
    public static final String SUBPROTOCOL = "duckdb";

    /**
     * Constant for the {@link org.osgi.framework.Constants#SERVICE_PID} of a
     * {@link DataSource} - Service.
     */
    public static final String PID_DATASOURCE = "daanse.jdbc.datasource.duckdb.DataSource";

    /**
     * Constant for Properties of the Service that could be configured using the
     * {@link Constants#PID_DATASOURCE}.
     *
     * Path of the database file for a persistent database, empty /
     * {@code :memory:} for an in-memory database, {@code memory:<label>} for a
     * named in-memory database or {@code ducklake:<metadata path>}. All connections
     * of one DataSource share its database — also an in-memory one; two DataSources
     * share only a file or a named in-memory database. Must not contain {@code ;}:
     * connection options have their own attributes.
     *
     * {@link org.eclipse.daanse.jdbc.datasource.duckdb.api.ocd.BaseConfig#databaseName()}
     */
    public static final String DATASOURCE_PROPERTY_DATABASENAME = "databaseName";

    /**
     * Constant for Properties of the Service that could be configured using the
     * {@link Constants#PID_DATASOURCE}.
     *
     * Open the database in read-only mode (file databases only). Shorthand for the
     * setting {@code access_mode=READ_ONLY}; the two must not contradict each
     * other.
     *
     * {@link org.eclipse.daanse.jdbc.datasource.duckdb.api.ocd.BaseConfig#readOnly()}
     */
    public static final String DATASOURCE_PROPERTY_READ_ONLY = "readOnly";

    /**
     * Constant for Properties of the Service that could be configured using the
     * {@link Constants#PID_DATASOURCE}.
     *
     * DuckDB {@code access_mode}: {@link #ACCESS_MODE_AUTOMATIC} (default),
     * {@link #ACCESS_MODE_READ_ONLY} or {@link #ACCESS_MODE_READ_WRITE}. Wins over
     * an {@code access_mode} entry of the settings; DuckDB refuses a
     * {@code readOnly=true} combined with a different access mode, and a read-only
     * in-memory database.
     *
     * {@link org.eclipse.daanse.jdbc.datasource.duckdb.api.ocd.BaseConfig#accessMode()}
     */
    public static final String DATASOURCE_PROPERTY_ACCESS_MODE = "accessMode";

    /** Name of the DuckDB setting behind {@link #DATASOURCE_PROPERTY_ACCESS_MODE}. */
    public static final String SETTING_ACCESS_MODE = "access_mode";

    public static final String ACCESS_MODE_AUTOMATIC = "AUTOMATIC";
    public static final String ACCESS_MODE_READ_ONLY = "READ_ONLY";
    public static final String ACCESS_MODE_READ_WRITE = "READ_WRITE";

    /**
     * Constant for Properties of the Service that could be configured using the
     * {@link Constants#PID_DATASOURCE}.
     *
     * Custom string appended to the user agent reported to DuckDB. Driver option {@code custom_user_agent}.
     *
     * {@link org.eclipse.daanse.jdbc.datasource.duckdb.api.ocd.BaseConfig#customUserAgent()}
     */
    public static final String DATASOURCE_PROPERTY_CUSTOM_USER_AGENT = "customUserAgent";

    /**
     * Constant for Properties of the Service that could be configured using the
     * {@link Constants#PID_DATASOURCE}.
     *
     * Stream result sets instead of materializing them. Off by default; on by
     * default for {@code ducklake:} databases. Driver option {@code jdbc_stream_results}.
     *
     * {@link org.eclipse.daanse.jdbc.datasource.duckdb.api.ocd.BaseConfig#streamResults()}
     */
    public static final String DATASOURCE_PROPERTY_STREAM_RESULTS = "streamResults";

    /**
     * Constant for Properties of the Service that could be configured using the
     * {@link Constants#PID_DATASOURCE}.
     *
     * Default auto-commit mode of the connections handed out. On by default. Driver option {@code jdbc_auto_commit}.
     *
     * {@link org.eclipse.daanse.jdbc.datasource.duckdb.api.ocd.BaseConfig#autoCommit()}
     */
    public static final String DATASOURCE_PROPERTY_AUTO_COMMIT = "autoCommit";

    /**
     * Constant for Properties of the Service that could be configured using the
     * {@link Constants#PID_DATASOURCE}.
     *
     * Keep the database instance alive after its last connection closes. The
     * DataSource holds the database open for its whole life anyway and releases the
     * pin when it is closed. Off by default; on by default for {@code ducklake:}
     * databases. Driver option {@code jdbc_pin_db}.
     *
     * {@link org.eclipse.daanse.jdbc.datasource.duckdb.api.ocd.BaseConfig#pinDb()}
     */
    public static final String DATASOURCE_PROPERTY_PIN_DB = "pinDb";

    /**
     * Constant for Properties of the Service that could be configured using the
     * {@link Constants#PID_DATASOURCE}.
     *
     * Reuse the process-wide database instance for the same database. On by
     * default; off creates an isolated instance, which may run into file lock
     * conflicts. Driver option {@code jdbc_instance_cache}.
     *
     * {@link org.eclipse.daanse.jdbc.datasource.duckdb.api.ocd.BaseConfig#instanceCache()}
     */
    public static final String DATASOURCE_PROPERTY_INSTANCE_CACHE = "instanceCache";

    /**
     * Constant for Properties of the Service that could be configured using the
     * {@link Constants#PID_DATASOURCE}.
     *
     * Silently drop settings DuckDB does not know instead of refusing to connect. Driver option {@code jdbc_ignore_unsupported_options}.
     *
     * {@link org.eclipse.daanse.jdbc.datasource.duckdb.api.ocd.BaseConfig#ignoreUnsupportedOptions()}
     */
    public static final String DATASOURCE_PROPERTY_IGNORE_UNSUPPORTED_OPTIONS = "ignoreUnsupportedOptions";

    /**
     * Constant for Properties of the Service that could be configured using the
     * {@link Constants#PID_DATASOURCE}.
     *
     * Name under which the database instance is tracked in the
     * {@code duckdb.MemoryUsage} JFR event; empty disables the monitoring. Driver option {@code jdbc_jfr_memory_monitor}.
     *
     * {@link org.eclipse.daanse.jdbc.datasource.duckdb.api.ocd.BaseConfig#jfrMemoryMonitor()}
     */
    public static final String DATASOURCE_PROPERTY_JFR_MEMORY_MONITOR = "jfrMemoryMonitor";

    /**
     * Constant for Properties of the Service that could be configured using the
     * {@link Constants#PID_DATASOURCE}.
     *
     * Path of a SQL file (at most 1MB) run before a connection is handed out: the
     * part above the marker comment
     * {@code DUCKDB_CONNECTION_INIT_BELOW_MARKER} once per database instance, the
     * part below it for every connection. Runs with the full privileges of the
     * connection. Must not contain {@code ;} or {@code =}. Driver option {@code session_init_sql_file}.
     *
     * {@link org.eclipse.daanse.jdbc.datasource.duckdb.api.ocd.BaseConfig#sessionInitSqlFile()}
     */
    public static final String DATASOURCE_PROPERTY_SESSION_INIT_SQL_FILE = "sessionInitSqlFile";

    /**
     * Constant for Properties of the Service that could be configured using the
     * {@link Constants#PID_DATASOURCE}.
     *
     * Expected SHA-256 digest (hex) of the session init SQL file; the connection is
     * refused on a mismatch. Needs the session init SQL file. Driver option {@code session_init_sql_file_sha256}.
     *
     * {@link org.eclipse.daanse.jdbc.datasource.duckdb.api.ocd.BaseConfig#sessionInitSqlFileSha256()}
     */
    public static final String DATASOURCE_PROPERTY_SESSION_INIT_SQL_FILE_SHA256 = "sessionInitSqlFileSha256";

    /**
     * Constant for Properties of the Service that could be configured using the
     * {@link Constants#PID_DATASOURCE}.
     *
     * Engine settings as {@code name=value}, passed to DuckDB as connection
     * properties: {@code threads=2}, {@code memory_limit=4GB},
     * {@code access_mode=READ_ONLY}, or anything else the engine accepts —
     * deliberately not enumerated here. They configure the database instance, not
     * a connection: a second DataSource on the same database has to use the same
     * settings. The dedicated attributes win over an entry of the same option.
     * <p>
     * {@code access_mode} is {@code AUTOMATIC} (default), {@code READ_ONLY} or
     * {@code READ_WRITE}. {@code access_mode=READ_ONLY} opens a file database
     * read-only, the same as {@code readOnly=true}; DuckDB refuses a
     * {@code readOnly=true} that is combined with a different {@code access_mode},
     * and a read-only in-memory database.
     *
     * {@link org.eclipse.daanse.jdbc.datasource.duckdb.api.ocd.BaseConfig#settings()}
     */
    public static final String DATASOURCE_PROPERTY_SETTINGS = "settings";

}
