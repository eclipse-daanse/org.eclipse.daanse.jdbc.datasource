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

        return new DuckDbDataSource(url, properties);
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
