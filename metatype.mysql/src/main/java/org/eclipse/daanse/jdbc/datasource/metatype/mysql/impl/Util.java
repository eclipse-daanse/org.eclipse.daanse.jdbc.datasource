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
package org.eclipse.daanse.jdbc.datasource.metatype.mysql.impl;

import java.sql.SQLException;
import java.util.Map;
import java.util.function.Supplier;

import org.eclipse.daanse.jdbc.datasource.metatype.mysql.api.Constants;

import com.mysql.cj.jdbc.MysqlDataSource;

public class Util {

    private Util() {
        // constructor
    }

    public static void doConfig(MysqlDataSource ds, Map<String, Object> configMap)
            throws SQLException {

        // Basic connection properties
        setStringPropertyFromMap(ds::setUser, configMap, Constants.DATASOURCE_PROPERTY_USER);
        setStringPropertyFromMap(ds::setPassword, configMap,
                Constants.DATASOURCE_PROPERTY_PASSWORD);
        setStringPropertyFromMap(ds::setServerName, configMap, Constants.DATASOURCE_PROPERTY_HOST);
        setStringPropertyFromMap(ds::setDatabaseName, configMap,
                Constants.DATASOURCE_PROPERTY_DATABASENAME);
        setValueFromMap(ds::setPort, configMap, Constants.DATASOURCE_PROPERTY_PORT);

        // Additional MySQL properties
        setValueFromMap(ds::setAutoReconnect, configMap,
                Constants.DATASOURCE_PROPERTY_AUTO_RECONNECT);
        setValueFromMap(ds::setConnectTimeout, configMap,
                Constants.DATASOURCE_PROPERTY_CONNECTION_TIMEOUT);
        setValueFromMap(ds::setSocketTimeout, configMap,
                Constants.DATASOURCE_PROPERTY_SOCKET_TIMEOUT);
        setValueFromMap(ds::setUseSSL, configMap, Constants.DATASOURCE_PROPERTY_USE_SSL);
        setStringPropertyFromMap(ds::setCharacterEncoding, configMap,
                Constants.DATASOURCE_PROPERTY_CHARACTER_ENCODING);
        setValueFromMap(ds::setUseServerPrepStmts, configMap,
                Constants.DATASOURCE_PROPERTY_USE_SERVER_PREPARED_STMTS);
        setValueFromMap(ds::setCachePrepStmts, configMap,
                Constants.DATASOURCE_PROPERTY_CACHE_PREPARED_STMTS);
        setValueFromMap(ds::setPrepStmtCacheSize, configMap,
                Constants.DATASOURCE_PROPERTY_PREPARED_STATEMENT_CACHE_SIZE);
        setValueFromMap(ds::setUseCompression, configMap,
                Constants.DATASOURCE_PROPERTY_USE_COMPRESSION);
        setStringPropertyFromMap(ds::setZeroDateTimeBehavior, configMap,
                Constants.DATASOURCE_PROPERTY_ZERO_DATETIME_BEHAVIOR);
        setStringPropertyFromMap(ds::setServerTimezone, configMap,
                Constants.DATASOURCE_PROPERTY_SERVER_TIMEZONE);
        setValueFromMap(ds::setUseAffectedRows, configMap,
                Constants.DATASOURCE_PROPERTY_USE_AFFECTED_ROWS);
        setValueFromMap(ds::setAllowMultiQueries, configMap,
                Constants.DATASOURCE_PROPERTY_ALLOW_MULTI_QUERIES);
        setValueFromMap(ds::setRewriteBatchedStatements, configMap,
                Constants.DATASOURCE_PROPERTY_REWRITE_BATCHED_STATEMENTS);
        setValueFromMap(ds::setUseInformationSchema, configMap,
                Constants.DATASOURCE_PROPERTY_USE_INFORMATION_SCHEMA);
        setStringPropertyFromMap(ds::setLogger, configMap,
                Constants.DATASOURCE_PROPERTY_LOGGER_CLASS_NAME);

    }

    @FunctionalInterface
    private interface SqlConsumer<T> {
        void accept(T t) throws SQLException;
    }

    @SuppressWarnings("unchecked")
    private static <T> void setValueFromMap(SqlConsumer<T> setterMethod,
            Map<String, Object> configMap, String propName) throws SQLException {
        if (configMap.containsKey(propName)) {
            T value = (T) configMap.get(propName);
            if (value != null) {
                setterMethod.accept(value);
            }
        }
    }

    private static void setStringPropertyFromMap(SqlConsumer<String> setterMethod,
            Map<String, Object> configMap, String propName) throws SQLException {
        if (configMap.containsKey(propName)) {
            String val = (String) configMap.get(propName);
            if (val != null && !val.trim().isEmpty()) {
                setterMethod.accept(val);
            }
        }
    }
}
