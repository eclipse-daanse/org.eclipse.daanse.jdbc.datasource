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
package org.eclipse.daanse.jdbc.datasource.duckdb.api.ocd;

import org.eclipse.daanse.jdbc.datasource.duckdb.api.Constants;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.osgi.service.metatype.annotations.Option;

/**
 * Embedded DuckDB: the DataSource owns its database.
 * {@link Constants#PID_DATASOURCE}.
 */
@ObjectClassDefinition(name = DsConfig.L10N_OCD_DS_NAME, description = DsConfig.L10N_OCD_DS_DESCRIPTION, localization = BaseConfig.OCD_LOCALIZATION)
public interface DsConfig extends BaseConfig {

    String L10N_OCD_DS_NAME = L10N_PREFIX + "ocd" + ".ds" + L10N_POSTFIX_NAME;
    String L10N_OCD_DS_DESCRIPTION = L10N_PREFIX + "ocd" + ".ds" + L10N_POSTFIX_DESCRIPTION;

    String L10N_DATABASENAME_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_DATABASENAME + L10N_POSTFIX_NAME;
    String L10N_DATABASENAME_DESCRIPTION = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_DATABASENAME
            + L10N_POSTFIX_DESCRIPTION;

    String L10N_READ_ONLY_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_READ_ONLY + L10N_POSTFIX_NAME;
    String L10N_READ_ONLY_DESCRIPTION = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_READ_ONLY
            + L10N_POSTFIX_DESCRIPTION;

    String L10N_ACCESS_MODE_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_ACCESS_MODE + L10N_POSTFIX_NAME;
    String L10N_ACCESS_MODE_DESCRIPTION = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_ACCESS_MODE
            + L10N_POSTFIX_DESCRIPTION;

    String L10N_PIN_DB_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_PIN_DB + L10N_POSTFIX_NAME;
    String L10N_PIN_DB_DESCRIPTION = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_PIN_DB
            + L10N_POSTFIX_DESCRIPTION;

    String L10N_INSTANCE_CACHE_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_INSTANCE_CACHE + L10N_POSTFIX_NAME;
    String L10N_INSTANCE_CACHE_DESCRIPTION = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_INSTANCE_CACHE
            + L10N_POSTFIX_DESCRIPTION;

    String L10N_SESSION_INIT_SQL_FILE_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_SESSION_INIT_SQL_FILE + L10N_POSTFIX_NAME;
    String L10N_SESSION_INIT_SQL_FILE_DESCRIPTION = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_SESSION_INIT_SQL_FILE
            + L10N_POSTFIX_DESCRIPTION;

    String L10N_SESSION_INIT_SQL_FILE_SHA256_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_SESSION_INIT_SQL_FILE_SHA256 + L10N_POSTFIX_NAME;
    String L10N_SESSION_INIT_SQL_FILE_SHA256_DESCRIPTION = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_SESSION_INIT_SQL_FILE_SHA256
            + L10N_POSTFIX_DESCRIPTION;

    // Default value constants
    String DEFAULT_DATABASE_NAME = "";
    boolean DEFAULT_READ_ONLY = false;
    String DEFAULT_ACCESS_MODE = Constants.ACCESS_MODE_AUTOMATIC;
    boolean DEFAULT_PIN_DB = false;
    boolean DEFAULT_INSTANCE_CACHE = true;
    String DEFAULT_SESSION_INIT_SQL_FILE = "";
    String DEFAULT_SESSION_INIT_SQL_FILE_SHA256 = "";

    /**
     * File path for a persistent database, empty / {@code :memory:} for an
     * in-memory database, {@code memory:<label>} for a named in-memory database or
     * {@code ducklake:<metadata path>}. Must not contain {@code ;}.
     */
    @AttributeDefinition(name = L10N_DATABASENAME_NAME, description = L10N_DATABASENAME_DESCRIPTION, defaultValue = DEFAULT_DATABASE_NAME)
    default String databaseName() {
        return DEFAULT_DATABASE_NAME;
    }

    /**
     * Open a file database read-only; shorthand for the setting
     * {@code access_mode=READ_ONLY}.
     */
    @AttributeDefinition(name = L10N_READ_ONLY_NAME, description = L10N_READ_ONLY_DESCRIPTION, defaultValue = DEFAULT_READ_ONLY
            + "")
    default boolean readOnly() {
        return DEFAULT_READ_ONLY;
    }

    /**
     * DuckDB {@code access_mode}; {@code READ_ONLY} opens a file database
     * read-only, the same as {@link #readOnly()}.
     */
    @AttributeDefinition(name = L10N_ACCESS_MODE_NAME, description = L10N_ACCESS_MODE_DESCRIPTION, required = false, defaultValue = DEFAULT_ACCESS_MODE, options = {
            @Option(label = Constants.ACCESS_MODE_AUTOMATIC, value = Constants.ACCESS_MODE_AUTOMATIC),
            @Option(label = Constants.ACCESS_MODE_READ_ONLY, value = Constants.ACCESS_MODE_READ_ONLY),
            @Option(label = Constants.ACCESS_MODE_READ_WRITE, value = Constants.ACCESS_MODE_READ_WRITE) })
    default String accessMode() {
        return DEFAULT_ACCESS_MODE;
    }

    /**
     * Keep the database instance alive after its last connection closes. The
     * DataSource holds the database open for its whole life anyway and releases the
     * pin when it is closed. Off by default; on by default for {@code ducklake:}
     * databases.
     */
    @AttributeDefinition(name = L10N_PIN_DB_NAME, description = L10N_PIN_DB_DESCRIPTION, required = false, defaultValue = DEFAULT_PIN_DB
            + "")
    default boolean pinDb() {
        return DEFAULT_PIN_DB;
    }

    /**
     * Reuse the process-wide database instance for the same database. On by
     * default; off creates an isolated instance, which may run into file lock
     * conflicts.
     */
    @AttributeDefinition(name = L10N_INSTANCE_CACHE_NAME, description = L10N_INSTANCE_CACHE_DESCRIPTION, required = false, defaultValue = DEFAULT_INSTANCE_CACHE
            + "")
    default boolean instanceCache() {
        return DEFAULT_INSTANCE_CACHE;
    }

    /**
     * Path of a SQL file (at most 1MB) run before a connection is handed out: the
     * part above the marker comment {@code DUCKDB_CONNECTION_INIT_BELOW_MARKER}
     * once per database instance, the part below it for every connection. Runs with
     * the full privileges of the connection. Must not contain {@code ;} or
     * {@code =}.
     */
    @AttributeDefinition(name = L10N_SESSION_INIT_SQL_FILE_NAME, description = L10N_SESSION_INIT_SQL_FILE_DESCRIPTION, required = false, defaultValue = DEFAULT_SESSION_INIT_SQL_FILE)
    default String sessionInitSqlFile() {
        return DEFAULT_SESSION_INIT_SQL_FILE;
    }

    /**
     * Expected SHA-256 digest (hex) of the session init SQL file; the connection is
     * refused on a mismatch. Needs the session init SQL file.
     */
    @AttributeDefinition(name = L10N_SESSION_INIT_SQL_FILE_SHA256_NAME, description = L10N_SESSION_INIT_SQL_FILE_SHA256_DESCRIPTION, required = false, defaultValue = DEFAULT_SESSION_INIT_SQL_FILE_SHA256)
    default String sessionInitSqlFileSha256() {
        return DEFAULT_SESSION_INIT_SQL_FILE_SHA256;
    }
}
