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
import java.util.function.Supplier;

import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteDataSource;

public class Util {
    private Util() {
        // constructor
    }

    public static void doConfig(SQLiteDataSource ds, SqliteConfig config, Map<String, Object> configMap)
            throws SQLException {
        SQLiteConfig c = new SQLiteConfig();

        ds.setUrl(config.url());

//        c.setPragma(Pragma.PASSWORD, config._password());

        setValueIfNotNull(c::setApplicationId, config::applicationId, configMap, "");
        setValueIfNotNull(c::setBusyTimeout, config::busyTimeout, configMap, "");
        setValueIfNotNull(c::setDateClass, () -> config.dateClass().getValue(), configMap, "");

        setValueIfNotNull(c::setDateStringFormat, config::dateStringFormat, configMap, "");
        setValueIfNotNull(c::deferForeignKeys, config::deferForeignKeys, configMap, "");
        setValueIfNotNull(c::setDefaultCacheSize, config::defaultCacheSize, configMap, "");
        setValueIfNotNull(c::deferForeignKeys, config::deferForeignKeys, configMap, "");
        setValueIfNotNull(c::enableCaseSensitiveLike, config::caseSensitiveLike, configMap, "");
        setValueIfNotNull(c::enableFullSync, config::fullSync, configMap, "");
        setValueIfNotNull(c::enforceForeignKeys, config::enforceForeignKeys, configMap, "");
        setValueIfNotNull(c::setHexKeyMode, config::hexKeyMode, configMap, "");
        setValueIfNotNull(c::incrementalVacuum, config::incrementalVacuum, configMap, "");
        setValueIfNotNull(c::setCacheSize, config::cacheSize, configMap, "");
        setValueIfNotNull(c::setJournalMode, config::journalMode, configMap, "");
        setValueIfNotNull(c::setJournalMode, config::journalMode, configMap, "");
        setValueIfNotNull(c::useLegacyFileFormat, config::legacyFileFormat, configMap, "");
        setValueIfNotNull(c::setLockingMode, config::lockingMode, configMap, "");
        setValueIfNotNull(c::enableLoadExtension, config::loadExtensionEnabled, configMap, "");
        setValueIfNotNull(c::setMaxPageCount, config::maxPageCount, configMap, "");
        setValueIfNotNull(c::setPageSize, config::pageSize, configMap, "");
        setValueIfNotNull(c::setReadOnly, config::readOnly, configMap, "");
        setValueIfNotNull(c::enableRecursiveTriggers, config::recursiveTriggers, configMap, "");
        setValueIfNotNull(c::enableReverseUnorderedSelects, config::reverseUnorderedSelects, configMap, "");
        setValueIfNotNull(c::setSharedCache, config::sharedCache, configMap, "");
        setValueIfNotNull(c::enableShortColumnNames, config::shortColumnNames, configMap, "");
        setValueIfNotNull(c::setSynchronous, config::synchronous, configMap, "");
        setValueIfNotNull(c::setTempStore, config::tempStore, configMap, "");
        setValueIfNotNull(c::setTempStoreDirectory, config::tempStoreDirectory, configMap, "");
        setValueIfNotNull(c::setTransactionMode, config::transactionMode, configMap, "");
        setValueIfNotNull(c::setUserVersion, config::userVersion, configMap, "");

        ds.setConfig(c);
    }

    private static <T> void setValueIfNotNull(Consumer<T> setterMethod, Supplier<T> value,
            Map<String, Object> configMap, String propName) {
        if (configMap.containsKey(propName)) {
            setterMethod.accept(value.get());
        }
    }
}
