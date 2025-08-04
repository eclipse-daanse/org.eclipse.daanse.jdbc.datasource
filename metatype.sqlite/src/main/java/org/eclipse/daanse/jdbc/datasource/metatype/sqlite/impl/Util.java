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

import java.sql.SQLException;
import java.util.Map;
import java.util.function.Consumer;

import org.eclipse.daanse.jdbc.datasource.metatype.sqlite.api.Constants;
import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteConfig.Pragma;
import org.sqlite.SQLiteDataSource;

public class Util {
    private Util() {
        // constructor
    }

    public static void doConfig(SQLiteDataSource ds, Map<String, Object> configMap)
            throws SQLException {
        SQLiteConfig c = new SQLiteConfig();

        // Set URL from configMap
        if (configMap.containsKey(Constants.DATASOURCE_PROPERTY_URL)) {
            ds.setUrl((String) configMap.get(Constants.DATASOURCE_PROPERTY_URL));
        }

        // Handle user and password on DataSource
        if (configMap.containsKey(Constants.DATASOURCE_PROPERTY_USER)) {
//            c.setPragma(Pragma.PASSWORD, null);
        }
        if (configMap.containsKey(Constants.DATASOURCE_PROPERTY_PASSWORD)) {
            String password = (String) configMap.get(Constants.DATASOURCE_PROPERTY_PASSWORD);
            if (password != null) {
                c.setPragma(Pragma.PASSWORD, password);
            }
        }

        setValueFromMap(c::setApplicationId, configMap, Constants.DATASOURCE_PROPERTY_APPLICATION_ID);
        setValueFromMap(c::setBusyTimeout, configMap, Constants.DATASOURCE_PROPERTY_BUSY_TIMEOUT);
        setStringValueFromMap(c::setDateClass, configMap, Constants.DATASOURCE_PROPERTY_DATE_CLASS);
        setStringValueFromMap(c::setDatePrecision, configMap, Constants.DATASOURCE_PROPERTY_DATE_PRECISION);
        setValueFromMap(c::setDateStringFormat, configMap, Constants.DATASOURCE_PROPERTY_DATE_STRING_FORMAT);
        setValueFromMap(c::deferForeignKeys, configMap, Constants.DATASOURCE_PROPERTY_DEFER_FOREIGN_KEYS);
        setValueFromMap(c::setDefaultCacheSize, configMap, Constants.DATASOURCE_PROPERTY_DEFAULT_CACHE_SIZE);
        setValueFromMap(c::enableCaseSensitiveLike, configMap, Constants.DATASOURCE_PROPERTY_CASE_SENSITIVE_LIKE);
        setValueFromMap(c::enableFullSync, configMap, Constants.DATASOURCE_PROPERTY_FULL_SYNC);
        setValueFromMap(c::enforceForeignKeys, configMap, Constants.DATASOURCE_PROPERTY_ENFORCE_FOREIGN_KEYS);
        setValueFromMap(c::setHexKeyMode, configMap, Constants.DATASOURCE_PROPERTY_HEX_KEY_MODE);
        setValueFromMap(c::incrementalVacuum, configMap, Constants.DATASOURCE_PROPERTY_INCREMENTAL_VACUUM);
        setValueFromMap(c::setCacheSize, configMap, Constants.DATASOURCE_PROPERTY_CACHE_SIZE);
        setPragmaFromMap(c, Pragma.JOURNAL_MODE, configMap, Constants.DATASOURCE_PROPERTY_JOURNAL_MODE);
        setValueFromMap(c::setJournalSizeLimit, configMap, Constants.DATASOURCE_PROPERTY_JOURNAL_SIZE_LIMIT);
        setValueFromMap(c::useLegacyFileFormat, configMap, Constants.DATASOURCE_PROPERTY_LEGACY_FILE_FORMAT);
        setPragmaFromMap(c, Pragma.LOCKING_MODE, configMap, Constants.DATASOURCE_PROPERTY_LOCKING_MODE);
        setValueFromMap(c::enableLoadExtension, configMap, Constants.DATASOURCE_PROPERTY_LOAD_EXTENSION_ENABLED);
        setValueFromMap(c::setMaxPageCount, configMap, Constants.DATASOURCE_PROPERTY_MAX_PAGE_COUNT);
        setValueFromMap(c::setPageSize, configMap, Constants.DATASOURCE_PROPERTY_PAGE_SIZE);
        setValueFromMap(c::setReadOnly, configMap, Constants.DATASOURCE_PROPERTY_READ_ONLY);
//        setValueFromMap(c::setReadUncommited, configMap, Constants.DATASOURCE_PROPERTY_READ_UNCOMMITTED);
        setValueFromMap(c::enableRecursiveTriggers, configMap, Constants.DATASOURCE_PROPERTY_RECURSIVE_TRIGGERS);
        setValueFromMap(c::enableReverseUnorderedSelects, configMap, Constants.DATASOURCE_PROPERTY_REVERSE_UNORDERED_SELECTS);
        setValueFromMap(c::setSharedCache, configMap, Constants.DATASOURCE_PROPERTY_SHARED_CACHE);
        setValueFromMap(c::enableShortColumnNames, configMap, Constants.DATASOURCE_PROPERTY_SHORT_COLUMN_NAMES);
        setPragmaFromMap(c, Pragma.SYNCHRONOUS, configMap, Constants.DATASOURCE_PROPERTY_SYNCHRONOUS);
        setPragmaFromMap(c, Pragma.TEMP_STORE, configMap, Constants.DATASOURCE_PROPERTY_TEMP_STORE);
        setValueFromMap(c::setTempStoreDirectory, configMap, Constants.DATASOURCE_PROPERTY_TEMP_STORE_DIRECTORY);
        setPragmaFromMap(c, Pragma.TRANSACTION_MODE, configMap, Constants.DATASOURCE_PROPERTY_TRANSACTION_MODE);
        setValueFromMap(c::setUserVersion, configMap, Constants.DATASOURCE_PROPERTY_USER_VERSION);

        ds.setConfig(c);
    }

    @SuppressWarnings("unchecked")
    private static <T> void setValueFromMap(Consumer<T> setterMethod,
            Map<String, Object> configMap, String propName) {
        if (configMap.containsKey(propName)) {
            T value = (T) configMap.get(propName);
            if (value != null) {
                setterMethod.accept(value);
            }
        }
    }

    private static void setStringValueFromMap(Consumer<String> setterMethod,
            Map<String, Object> configMap, String propName) {
        if (configMap.containsKey(propName)) {
            Object value = configMap.get(propName);
            if (value != null) {
                setterMethod.accept(value.toString());
            }
        }
    }

    private static void setPragmaFromMap(SQLiteConfig config, Pragma pragma,
            Map<String, Object> configMap, String propName) {
        if (configMap.containsKey(propName)) {
            Object value = configMap.get(propName);
            if (value != null) {
                config.setPragma(pragma, value.toString());
            }
        }
    }
}
