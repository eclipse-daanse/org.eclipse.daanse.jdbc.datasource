/*
* Copyright (c) 2024 Contributors to the Eclipse Foundation.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
*
* Contributors:
*   SmartCity Jena - initial
*   Stefan Bischof (bipolis.org) - initial
*/
package org.eclipse.daanse.jdbc.datasource.sqlite.api;

import javax.sql.ConnectionPoolDataSource;
import javax.sql.DataSource;
import javax.sql.XADataSource;

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
     *
     */
    public static final String SUBPROTOCOL = "sqlite";

    /**
     * Constant for the {@link org.osgi.framework.Constants#SERVICE_PID} of a {@link DataSource} and
     * {@link XADataSource} and {@link ConnectionPoolDataSource} - Service.
     */
    public static final String PID_DATASOURCE = "daanse.jdbc.datasource.sqlite.DataSource";

    /**
     * Constant for the {@link org.osgi.framework.Constants#SERVICE_PID} of a
     * {@link ConnectionPoolDataSource} - Service.
     */
    public static final String PID_DATASOURCE_CP = "daanse.jdbc.datasource.sqlite.ConnectionPoolDataSource";

    /**
     * Constant for Properties of the Service that could be configured using the
     * {@link Constants#PID_DATASOURCE}.
     *
     * {@link org.eclipse.daanse.jdbc.datasource.sqlite.impl.SqliteConfig#_password()}
     */
    public static final String DATASOURCE_PROPERTY_PASSWORD = ".password";

    /**
     * Constant for Properties of the Service that could be configured using the
     * {@link Constants#PID_DATASOURCE}.
     *
     * {@link org.eclipse.daanse.jdbc.datasource.sqlite.impl.SqliteConfig#user()}
     */
    public static final String DATASOURCE_PROPERTY_USER = "user";

    /**
     * Constant for Properties of the Service that could be configured using the
     * {@link Constants#PID_DATASOURCE}.
     *
     * {@link org.eclipse.daanse.jdbc.datasource.sqlite.impl.SqliteConfig#url()}
     */
    public static final String DATASOURCE_PROPERTY_URL = "url";

    // Additional SQLite driver properties

    /**
     * SQLite shared cache setting
     */
    public static final String DATASOURCE_PROPERTY_SHARED_CACHE = "sharedCache";

    /**
     * SQLite load extension enabled
     */
    public static final String DATASOURCE_PROPERTY_LOAD_EXTENSION_ENABLED = "loadExtensionEnabled";

    /**
     * SQLite read only mode
     */
    public static final String DATASOURCE_PROPERTY_READ_ONLY = "readOnly";

    /**
     * SQLite cache size
     */
    public static final String DATASOURCE_PROPERTY_CACHE_SIZE = "cacheSize";

    /**
     * SQLite case sensitive LIKE
     */
    public static final String DATASOURCE_PROPERTY_CASE_SENSITIVE_LIKE = "caseSensitiveLike";

    /**
     * SQLite default cache size
     */
    public static final String DATASOURCE_PROPERTY_DEFAULT_CACHE_SIZE = "defaultCacheSize";

    /**
     * SQLite defer foreign keys
     */
    public static final String DATASOURCE_PROPERTY_DEFER_FOREIGN_KEYS = "deferForeignKeys";

    /**
     * SQLite enforce foreign keys
     */
    public static final String DATASOURCE_PROPERTY_ENFORCE_FOREIGN_KEYS = "enforceForeignKeys";

    /**
     * SQLite full sync
     */
    public static final String DATASOURCE_PROPERTY_FULL_SYNC = "fullSync";

    /**
     * SQLite incremental vacuum
     */
    public static final String DATASOURCE_PROPERTY_INCREMENTAL_VACUUM = "incrementalVacuum";

    /**
     * SQLite journal mode
     */
    public static final String DATASOURCE_PROPERTY_JOURNAL_MODE = "journalMode";

    /**
     * SQLite journal size limit
     */
    public static final String DATASOURCE_PROPERTY_JOURNAL_SIZE_LIMIT = "jounalSizeLimit";

    /**
     * SQLite legacy file format
     */
    public static final String DATASOURCE_PROPERTY_LEGACY_FILE_FORMAT = "legacyFileFormat";

    /**
     * SQLite locking mode
     */
    public static final String DATASOURCE_PROPERTY_LOCKING_MODE = "lockingMode";

    /**
     * SQLite page size
     */
    public static final String DATASOURCE_PROPERTY_PAGE_SIZE = "pageSize";

    /**
     * SQLite max page count
     */
    public static final String DATASOURCE_PROPERTY_MAX_PAGE_COUNT = "maxPageCount";

    /**
     * SQLite read uncommitted
     */
    public static final String DATASOURCE_PROPERTY_READ_UNCOMMITTED = "readUncommited";

    /**
     * SQLite recursive triggers
     */
    public static final String DATASOURCE_PROPERTY_RECURSIVE_TRIGGERS = "recursiveTriggers";

    /**
     * SQLite reverse unordered selects
     */
    public static final String DATASOURCE_PROPERTY_REVERSE_UNORDERED_SELECTS = "reverseUnorderedSelects";

    /**
     * SQLite short column names
     */
    public static final String DATASOURCE_PROPERTY_SHORT_COLUMN_NAMES = "shortColumnNames";

    /**
     * SQLite synchronous mode
     */
    public static final String DATASOURCE_PROPERTY_SYNCHRONOUS = "synchronous";

    /**
     * SQLite hex key mode
     */
    public static final String DATASOURCE_PROPERTY_HEX_KEY_MODE = "hexKeyMode";

    /**
     * SQLite temp store
     */
    public static final String DATASOURCE_PROPERTY_TEMP_STORE = "tempStore";

    /**
     * SQLite temp store directory
     */
    public static final String DATASOURCE_PROPERTY_TEMP_STORE_DIRECTORY = "tempStoreDirectory";

    /**
     * SQLite user version
     */
    public static final String DATASOURCE_PROPERTY_USER_VERSION = "userVersion";

    /**
     * SQLite application ID
     */
    public static final String DATASOURCE_PROPERTY_APPLICATION_ID = "applicationId";

    /**
     * SQLite transaction mode
     */
    public static final String DATASOURCE_PROPERTY_TRANSACTION_MODE = "transactionMode";

    /**
     * SQLite date precision
     */
    public static final String DATASOURCE_PROPERTY_DATE_PRECISION = "datePrecision";

    /**
     * SQLite date class
     */
    public static final String DATASOURCE_PROPERTY_DATE_CLASS = "dateClass";

    /**
     * SQLite date string format
     */
    public static final String DATASOURCE_PROPERTY_DATE_STRING_FORMAT = "dateStringFormat";

    /**
     * SQLite busy timeout
     */
    public static final String DATASOURCE_PROPERTY_BUSY_TIMEOUT = "busyTimeout";

}
