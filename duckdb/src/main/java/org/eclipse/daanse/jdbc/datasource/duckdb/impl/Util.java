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

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.duckdb.DuckDBDriver;
import org.eclipse.daanse.jdbc.datasource.duckdb.api.Constants;

public class Util {

    private Util() {
        // constructor
    }

    /**
     * DuckDB is URL-based ({@code jdbc:duckdb:<path>}); an empty or
     * {@code :memory:} databaseName yields an in-memory database (which is private
     * to each connection).
     */
    public static DuckDbDataSource createDataSource(Map<String, Object> configMap) {
        String databaseName = stringValue(configMap, Constants.DATASOURCE_PROPERTY_DATABASENAME, "");
        if (":memory:".equals(databaseName)) {
            databaseName = "";
        }
        String url = "jdbc:duckdb:" + databaseName;

        Properties properties = new Properties();
        if (booleanValue(configMap, Constants.DATASOURCE_PROPERTY_READ_ONLY, false)) {
            properties.setProperty(DuckDBDriver.DUCKDB_READONLY_PROPERTY, "true");
        }
        // Passed through unread; an unknown setting is DuckDB's to reject.
        for (String setting : settings(configMap)) {
            int equals = setting.indexOf('=');
            if (equals > 0) {
                properties.setProperty(setting.substring(0, equals).trim(), setting.substring(equals + 1).trim());
            }
        }

        return new DuckDbDataSource(url, properties);
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
