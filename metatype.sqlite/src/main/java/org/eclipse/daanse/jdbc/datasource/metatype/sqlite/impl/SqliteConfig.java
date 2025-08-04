/*
* Copyright (c) 2022 Contributors to the Eclipse Foundation.
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
package org.eclipse.daanse.jdbc.datasource.metatype.sqlite.impl;

import org.eclipse.daanse.jdbc.datasource.metatype.sqlite.api.Constants;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.sqlite.SQLiteConfig.DateClass;
import org.sqlite.SQLiteConfig.DatePrecision;
import org.sqlite.SQLiteConfig.HexKeyMode;
import org.sqlite.SQLiteConfig.JournalMode;
import org.sqlite.SQLiteConfig.LockingMode;
import org.sqlite.SQLiteConfig.SynchronousMode;
import org.sqlite.SQLiteConfig.TempStore;
import org.sqlite.SQLiteConfig.TransactionMode;

@ObjectClassDefinition(name = SqliteConfig.L10N_OCD_NAME, description = SqliteConfig.L10N_OCD_DESCRIPTION)
public @interface SqliteConfig {

    public static final String L10N_PREFIX = "%";
    public static final String L10N_POSTFIX_DESCRIPTION = ".description";
    public static final String L10N_POSTFIX_NAME = ".name";

    public static final String L10N_OCD_NAME = L10N_PREFIX + "ocd" + L10N_POSTFIX_NAME;
    public static final String L10N_OCD_DESCRIPTION = L10N_PREFIX + "ocd" + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_URL_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_URL + L10N_POSTFIX_NAME;
    public static final String L10N_URL_DESCRIPTION = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_URL
            + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_USER_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_USER + L10N_POSTFIX_NAME;
    public static final String L10N_USER_DESCRIPTION = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_USER
            + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_PASSWORD_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_PASSWORD
            + L10N_POSTFIX_NAME;
    public static final String L10N_PASSWORD_DESCRIPTION = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_PASSWORD
            + L10N_POSTFIX_DESCRIPTION;

    // SQLite specific L10N constants

    public static final String L10N_SHARED_CACHE_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_SHARED_CACHE
            + L10N_POSTFIX_NAME;
    public static final String L10N_SHARED_CACHE_DESCRIPTION = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_SHARED_CACHE
            + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_LOAD_EXTENSION_ENABLED_NAME = L10N_PREFIX
            + Constants.DATASOURCE_PROPERTY_LOAD_EXTENSION_ENABLED + L10N_POSTFIX_NAME;
    public static final String L10N_LOAD_EXTENSION_ENABLED_DESCRIPTION = L10N_PREFIX
            + Constants.DATASOURCE_PROPERTY_LOAD_EXTENSION_ENABLED + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_READ_ONLY_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_READ_ONLY
            + L10N_POSTFIX_NAME;
    public static final String L10N_READ_ONLY_DESCRIPTION = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_READ_ONLY
            + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_CACHE_SIZE_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_CACHE_SIZE
            + L10N_POSTFIX_NAME;
    public static final String L10N_CACHE_SIZE_DESCRIPTION = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_CACHE_SIZE
            + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_CASE_SENSITIVE_LIKE_NAME = L10N_PREFIX
            + Constants.DATASOURCE_PROPERTY_CASE_SENSITIVE_LIKE + L10N_POSTFIX_NAME;
    public static final String L10N_CASE_SENSITIVE_LIKE_DESCRIPTION = L10N_PREFIX
            + Constants.DATASOURCE_PROPERTY_CASE_SENSITIVE_LIKE + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_DEFAULT_CACHE_SIZE_NAME = L10N_PREFIX
            + Constants.DATASOURCE_PROPERTY_DEFAULT_CACHE_SIZE + L10N_POSTFIX_NAME;
    public static final String L10N_DEFAULT_CACHE_SIZE_DESCRIPTION = L10N_PREFIX
            + Constants.DATASOURCE_PROPERTY_DEFAULT_CACHE_SIZE + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_DEFER_FOREIGN_KEYS_NAME = L10N_PREFIX
            + Constants.DATASOURCE_PROPERTY_DEFER_FOREIGN_KEYS + L10N_POSTFIX_NAME;
    public static final String L10N_DEFER_FOREIGN_KEYS_DESCRIPTION = L10N_PREFIX
            + Constants.DATASOURCE_PROPERTY_DEFER_FOREIGN_KEYS + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_ENFORCE_FOREIGN_KEYS_NAME = L10N_PREFIX
            + Constants.DATASOURCE_PROPERTY_ENFORCE_FOREIGN_KEYS + L10N_POSTFIX_NAME;
    public static final String L10N_ENFORCE_FOREIGN_KEYS_DESCRIPTION = L10N_PREFIX
            + Constants.DATASOURCE_PROPERTY_ENFORCE_FOREIGN_KEYS + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_FULL_SYNC_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_FULL_SYNC
            + L10N_POSTFIX_NAME;
    public static final String L10N_FULL_SYNC_DESCRIPTION = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_FULL_SYNC
            + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_INCREMENTAL_VACUUM_NAME = L10N_PREFIX
            + Constants.DATASOURCE_PROPERTY_INCREMENTAL_VACUUM + L10N_POSTFIX_NAME;
    public static final String L10N_INCREMENTAL_VACUUM_DESCRIPTION = L10N_PREFIX
            + Constants.DATASOURCE_PROPERTY_INCREMENTAL_VACUUM + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_JOURNAL_MODE_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_JOURNAL_MODE
            + L10N_POSTFIX_NAME;
    public static final String L10N_JOURNAL_MODE_DESCRIPTION = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_JOURNAL_MODE
            + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_JOURNAL_SIZE_LIMIT_NAME = L10N_PREFIX
            + Constants.DATASOURCE_PROPERTY_JOURNAL_SIZE_LIMIT + L10N_POSTFIX_NAME;
    public static final String L10N_JOURNAL_SIZE_LIMIT_DESCRIPTION = L10N_PREFIX
            + Constants.DATASOURCE_PROPERTY_JOURNAL_SIZE_LIMIT + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_LEGACY_FILE_FORMAT_NAME = L10N_PREFIX
            + Constants.DATASOURCE_PROPERTY_LEGACY_FILE_FORMAT + L10N_POSTFIX_NAME;
    public static final String L10N_LEGACY_FILE_FORMAT_DESCRIPTION = L10N_PREFIX
            + Constants.DATASOURCE_PROPERTY_LEGACY_FILE_FORMAT + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_LOCKING_MODE_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_LOCKING_MODE
            + L10N_POSTFIX_NAME;
    public static final String L10N_LOCKING_MODE_DESCRIPTION = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_LOCKING_MODE
            + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_PAGE_SIZE_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_PAGE_SIZE
            + L10N_POSTFIX_NAME;
    public static final String L10N_PAGE_SIZE_DESCRIPTION = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_PAGE_SIZE
            + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_MAX_PAGE_COUNT_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_MAX_PAGE_COUNT
            + L10N_POSTFIX_NAME;
    public static final String L10N_MAX_PAGE_COUNT_DESCRIPTION = L10N_PREFIX
            + Constants.DATASOURCE_PROPERTY_MAX_PAGE_COUNT + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_READ_UNCOMMITTED_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_READ_UNCOMMITTED
            + L10N_POSTFIX_NAME;
    public static final String L10N_READ_UNCOMMITTED_DESCRIPTION = L10N_PREFIX
            + Constants.DATASOURCE_PROPERTY_READ_UNCOMMITTED + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_RECURSIVE_TRIGGERS_NAME = L10N_PREFIX
            + Constants.DATASOURCE_PROPERTY_RECURSIVE_TRIGGERS + L10N_POSTFIX_NAME;
    public static final String L10N_RECURSIVE_TRIGGERS_DESCRIPTION = L10N_PREFIX
            + Constants.DATASOURCE_PROPERTY_RECURSIVE_TRIGGERS + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_REVERSE_UNORDERED_SELECTS_NAME = L10N_PREFIX
            + Constants.DATASOURCE_PROPERTY_REVERSE_UNORDERED_SELECTS + L10N_POSTFIX_NAME;
    public static final String L10N_REVERSE_UNORDERED_SELECTS_DESCRIPTION = L10N_PREFIX
            + Constants.DATASOURCE_PROPERTY_REVERSE_UNORDERED_SELECTS + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_SHORT_COLUMN_NAMES_NAME = L10N_PREFIX
            + Constants.DATASOURCE_PROPERTY_SHORT_COLUMN_NAMES + L10N_POSTFIX_NAME;
    public static final String L10N_SHORT_COLUMN_NAMES_DESCRIPTION = L10N_PREFIX
            + Constants.DATASOURCE_PROPERTY_SHORT_COLUMN_NAMES + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_SYNCHRONOUS_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_SYNCHRONOUS
            + L10N_POSTFIX_NAME;
    public static final String L10N_SYNCHRONOUS_DESCRIPTION = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_SYNCHRONOUS
            + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_HEX_KEY_MODE_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_HEX_KEY_MODE
            + L10N_POSTFIX_NAME;
    public static final String L10N_HEX_KEY_MODE_DESCRIPTION = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_HEX_KEY_MODE
            + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_TEMP_STORE_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_TEMP_STORE
            + L10N_POSTFIX_NAME;
    public static final String L10N_TEMP_STORE_DESCRIPTION = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_TEMP_STORE
            + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_TEMP_STORE_DIRECTORY_NAME = L10N_PREFIX
            + Constants.DATASOURCE_PROPERTY_TEMP_STORE_DIRECTORY + L10N_POSTFIX_NAME;
    public static final String L10N_TEMP_STORE_DIRECTORY_DESCRIPTION = L10N_PREFIX
            + Constants.DATASOURCE_PROPERTY_TEMP_STORE_DIRECTORY + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_USER_VERSION_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_USER_VERSION
            + L10N_POSTFIX_NAME;
    public static final String L10N_USER_VERSION_DESCRIPTION = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_USER_VERSION
            + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_APPLICATION_ID_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_APPLICATION_ID
            + L10N_POSTFIX_NAME;
    public static final String L10N_APPLICATION_ID_DESCRIPTION = L10N_PREFIX
            + Constants.DATASOURCE_PROPERTY_APPLICATION_ID + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_TRANSACTION_MODE_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_TRANSACTION_MODE
            + L10N_POSTFIX_NAME;
    public static final String L10N_TRANSACTION_MODE_DESCRIPTION = L10N_PREFIX
            + Constants.DATASOURCE_PROPERTY_TRANSACTION_MODE + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_DATE_PRECISION_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_DATE_PRECISION
            + L10N_POSTFIX_NAME;
    public static final String L10N_DATE_PRECISION_DESCRIPTION = L10N_PREFIX
            + Constants.DATASOURCE_PROPERTY_DATE_PRECISION + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_DATE_CLASS_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_DATE_CLASS
            + L10N_POSTFIX_NAME;
    public static final String L10N_DATE_CLASS_DESCRIPTION = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_DATE_CLASS
            + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_DATE_STRING_FORMAT_NAME = L10N_PREFIX
            + Constants.DATASOURCE_PROPERTY_DATE_STRING_FORMAT + L10N_POSTFIX_NAME;
    public static final String L10N_DATE_STRING_FORMAT_DESCRIPTION = L10N_PREFIX
            + Constants.DATASOURCE_PROPERTY_DATE_STRING_FORMAT + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_BUSY_TIMEOUT_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_BUSY_TIMEOUT
            + L10N_POSTFIX_NAME;
    public static final String L10N_BUSY_TIMEOUT_DESCRIPTION = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_BUSY_TIMEOUT
            + L10N_POSTFIX_DESCRIPTION;

    @AttributeDefinition(name = L10N_URL_NAME, description = L10N_URL_DESCRIPTION, required = true)
    String url();

    @AttributeDefinition(name = L10N_USER_NAME, description = L10N_USER_DESCRIPTION)
    String user() default "";

    /**
     * OSGi Service Component Spec.:
     *
     * Component properties whose names start with full stop are available to the component instance but
     * are not available as service properties of the registered service.
     *
     * A single low line ('_' \u005F) is converted into a full stop ('.' \u002E)
     *
     * @return password
     */
    @AttributeDefinition(name = L10N_PASSWORD_NAME, description = L10N_PASSWORD_DESCRIPTION, type = AttributeType.PASSWORD)
    String _password();

    @AttributeDefinition(name = L10N_SHARED_CACHE_NAME, description = L10N_SHARED_CACHE_DESCRIPTION)
    boolean sharedCache() default false;

    @AttributeDefinition(name = L10N_LOAD_EXTENSION_ENABLED_NAME, description = L10N_LOAD_EXTENSION_ENABLED_DESCRIPTION)
    boolean loadExtensionEnabled() default false;

    @AttributeDefinition(name = L10N_READ_ONLY_NAME, description = L10N_READ_ONLY_DESCRIPTION)
    boolean readOnly() default false;

    @AttributeDefinition(name = L10N_CACHE_SIZE_NAME, description = L10N_CACHE_SIZE_DESCRIPTION)
    int cacheSize() default 0;

    @AttributeDefinition(name = L10N_CASE_SENSITIVE_LIKE_NAME, description = L10N_CASE_SENSITIVE_LIKE_DESCRIPTION)
    boolean caseSensitiveLike() default false;

    @AttributeDefinition(name = L10N_DEFAULT_CACHE_SIZE_NAME, description = L10N_DEFAULT_CACHE_SIZE_DESCRIPTION)
    int defaultCacheSize() default 0;

    @AttributeDefinition(name = L10N_DEFER_FOREIGN_KEYS_NAME, description = L10N_DEFER_FOREIGN_KEYS_DESCRIPTION)
    boolean deferForeignKeys() default false;

    @AttributeDefinition(name = L10N_ENFORCE_FOREIGN_KEYS_NAME, description = L10N_ENFORCE_FOREIGN_KEYS_DESCRIPTION)
    boolean enforceForeignKeys() default false;

    @AttributeDefinition(name = L10N_FULL_SYNC_NAME, description = L10N_FULL_SYNC_DESCRIPTION)
    boolean fullSync() default false;

    @AttributeDefinition(name = L10N_INCREMENTAL_VACUUM_NAME, description = L10N_INCREMENTAL_VACUUM_DESCRIPTION)
    int incrementalVacuum() default 0;

    @AttributeDefinition(name = L10N_JOURNAL_MODE_NAME, description = L10N_JOURNAL_MODE_DESCRIPTION)
    JournalMode journalMode() default JournalMode.DELETE;

    @AttributeDefinition(name = L10N_JOURNAL_SIZE_LIMIT_NAME, description = L10N_JOURNAL_SIZE_LIMIT_DESCRIPTION)
    int jounalSizeLimit() default 0;

    @AttributeDefinition(name = L10N_LEGACY_FILE_FORMAT_NAME, description = L10N_LEGACY_FILE_FORMAT_DESCRIPTION)
    boolean legacyFileFormat() default false;

    @AttributeDefinition(name = L10N_LOCKING_MODE_NAME, description = L10N_LOCKING_MODE_DESCRIPTION)
    LockingMode lockingMode() default LockingMode.NORMAL;

    @AttributeDefinition(name = L10N_PAGE_SIZE_NAME, description = L10N_PAGE_SIZE_DESCRIPTION)
    int pageSize() default 0;

    @AttributeDefinition(name = L10N_MAX_PAGE_COUNT_NAME, description = L10N_MAX_PAGE_COUNT_DESCRIPTION)
    int maxPageCount() default 0;

    @AttributeDefinition(name = L10N_READ_UNCOMMITTED_NAME, description = L10N_READ_UNCOMMITTED_DESCRIPTION)
    boolean readUncommited() default false;

    @AttributeDefinition(name = L10N_RECURSIVE_TRIGGERS_NAME, description = L10N_RECURSIVE_TRIGGERS_DESCRIPTION)
    boolean recursiveTriggers() default false;

    @AttributeDefinition(name = L10N_REVERSE_UNORDERED_SELECTS_NAME, description = L10N_REVERSE_UNORDERED_SELECTS_DESCRIPTION)
    boolean reverseUnorderedSelects() default false;

    @AttributeDefinition(name = L10N_SHORT_COLUMN_NAMES_NAME, description = L10N_SHORT_COLUMN_NAMES_DESCRIPTION)
    boolean shortColumnNames() default false;

    @AttributeDefinition(name = L10N_SYNCHRONOUS_NAME, description = L10N_SYNCHRONOUS_DESCRIPTION)
    SynchronousMode synchronous() default SynchronousMode.FULL;

    @AttributeDefinition(name = L10N_HEX_KEY_MODE_NAME, description = L10N_HEX_KEY_MODE_DESCRIPTION)
    HexKeyMode hexKeyMode() default HexKeyMode.NONE;

    @AttributeDefinition(name = L10N_TEMP_STORE_NAME, description = L10N_TEMP_STORE_DESCRIPTION)
    TempStore tempStore() default TempStore.DEFAULT;

    @AttributeDefinition(name = L10N_TEMP_STORE_DIRECTORY_NAME, description = L10N_TEMP_STORE_DIRECTORY_DESCRIPTION)
    String tempStoreDirectory() default "";

    @AttributeDefinition(name = L10N_USER_VERSION_NAME, description = L10N_USER_VERSION_DESCRIPTION)
    int userVersion() default 0;

    @AttributeDefinition(name = L10N_APPLICATION_ID_NAME, description = L10N_APPLICATION_ID_DESCRIPTION)
    int applicationId() default 0;

    @AttributeDefinition(name = L10N_TRANSACTION_MODE_NAME, description = L10N_TRANSACTION_MODE_DESCRIPTION)
    TransactionMode transactionMode() default TransactionMode.DEFERRED;

    @AttributeDefinition(name = L10N_DATE_PRECISION_NAME, description = L10N_DATE_PRECISION_DESCRIPTION)
    DatePrecision datePrecision() default DatePrecision.MILLISECONDS;

    @AttributeDefinition(name = L10N_DATE_CLASS_NAME, description = L10N_DATE_CLASS_DESCRIPTION)
    DateClass dateClass() default DateClass.INTEGER;

    @AttributeDefinition(name = L10N_DATE_STRING_FORMAT_NAME, description = L10N_DATE_STRING_FORMAT_DESCRIPTION)
    String dateStringFormat() default "";

    @AttributeDefinition(name = L10N_BUSY_TIMEOUT_NAME, description = L10N_BUSY_TIMEOUT_DESCRIPTION)
    int busyTimeout() default 0;

}
