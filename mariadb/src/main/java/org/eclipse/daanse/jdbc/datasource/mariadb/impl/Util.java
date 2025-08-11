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
package org.eclipse.daanse.jdbc.datasource.mariadb.impl;

import java.sql.SQLException;
import java.util.Map;
import java.util.StringJoiner;

import org.eclipse.daanse.jdbc.datasource.mariadb.api.Constants;
import org.mariadb.jdbc.MariaDbDataSource;

public class Util {

    private Util() {
        // constructor
    }

    public static void doConfig(MariaDbDataSource ds, Map<String, Object> configMap) throws SQLException {

        // Basic connection properties
        setStringPropertyFromMap(ds::setUser, configMap, Constants.DATASOURCE_PROPERTY_USER);
        setStringPropertyFromMap(ds::setPassword, configMap, Constants.DATASOURCE_PROPERTY_PASSWORD);

        // Build the URL with properties since MariaDB uses setUrl() for configuration
        String url = buildUrl(configMap);
        if (url != null && !url.trim().isEmpty()) {
            ds.setUrl(url);
        }
    }

    private static String buildUrl(Map<String, Object> configMap) {
        // Get basic connection info
        String host = getStringValue(configMap, Constants.DATASOURCE_PROPERTY_HOST, "localhost");
        Integer port = getIntegerValue(configMap, Constants.DATASOURCE_PROPERTY_PORT, 3306);
        String databaseName = getStringValue(configMap, Constants.DATASOURCE_PROPERTY_DATABASENAME, "");

        // Build base URL
        StringBuilder urlBuilder = new StringBuilder("jdbc:mariadb://");
        urlBuilder.append(host).append(":").append(port);
        if (!databaseName.trim().isEmpty()) {
            urlBuilder.append("/").append(databaseName);
        }

        // Add connection properties as URL parameters
        StringJoiner params = new StringJoiner("&");

        // Connection timeout
        Integer connectTimeout = getIntegerValue(configMap, Constants.DATASOURCE_PROPERTY_CONNECT_TIMEOUT, null);
        if (connectTimeout != null) {
            params.add("connectTimeout=" + connectTimeout);
        }

        // Use server prepared statements
        Boolean useServerPrepStmts = getBooleanValue(configMap, Constants.DATASOURCE_PROPERTY_USE_SERVER_PREPARED_STMTS, null);
        if (useServerPrepStmts != null) {
            params.add("useServerPrepStmts=" + useServerPrepStmts);
        }

        // Allow local infile
        Boolean allowLocalInfile = getBooleanValue(configMap, Constants.DATASOURCE_PROPERTY_ALLOW_LOCAL_INFILE, null);
        if (allowLocalInfile != null) {
            params.add("allowLocalInfile=" + allowLocalInfile);
        }

        // SSL Mode
        String sslMode = getStringValue(configMap, Constants.DATASOURCE_PROPERTY_SSL_MODE, null);
        if (sslMode != null && !sslMode.trim().isEmpty()) {
            params.add("sslMode=" + sslMode);
        }

        // Use compression
        Boolean useCompression = getBooleanValue(configMap, Constants.DATASOURCE_PROPERTY_USE_COMPRESSION, null);
        if (useCompression != null) {
            params.add("useCompression=" + useCompression);
        }

        // Socket timeout
        Integer socketTimeout = getIntegerValue(configMap, Constants.DATASOURCE_PROPERTY_SOCKET_TIMEOUT, null);
        if (socketTimeout != null) {
            params.add("socketTimeout=" + socketTimeout);
        }

        // Allow multi queries
        Boolean allowMultiQueries = getBooleanValue(configMap, Constants.DATASOURCE_PROPERTY_ALLOW_MULTI_QUERIES, null);
        if (allowMultiQueries != null) {
            params.add("allowMultiQueries=" + allowMultiQueries);
        }

        // Session variables
        String sessionVariables = getStringValue(configMap, Constants.DATASOURCE_PROPERTY_SESSION_VARIABLES, null);
        if (sessionVariables != null && !sessionVariables.trim().isEmpty()) {
            params.add("sessionVariables=" + sessionVariables);
        }

        // Add parameters to URL if any exist
        if (params.length() > 0) {
            urlBuilder.append("?").append(params.toString());
        }

        return urlBuilder.toString();
    }

    @FunctionalInterface
    private interface SqlConsumer<T> {
        void accept(T t) throws SQLException;
    }

    private static void setStringPropertyFromMap(SqlConsumer<String> setterMethod, Map<String, Object> configMap,
            String propName) throws SQLException {
        if (configMap.containsKey(propName)) {
            String val = (String) configMap.get(propName);
            if (val != null && !val.trim().isEmpty()) {
                setterMethod.accept(val);
            }
        }
    }

    private static String getStringValue(Map<String, Object> configMap, String key, String defaultValue) {
        Object value = configMap.get(key);
        if (value instanceof String) {
            String strValue = (String) value;
            return strValue.trim().isEmpty() ? defaultValue : strValue;
        }
        return defaultValue;
    }

    private static Integer getIntegerValue(Map<String, Object> configMap, String key, Integer defaultValue) {
        Object value = configMap.get(key);
        if (value instanceof Integer) {
            return (Integer) value;
        }
        if (value instanceof String) {
            try {
                return Integer.parseInt((String) value);
            } catch (NumberFormatException e) {
                return defaultValue;
            }
        }
        return defaultValue;
    }

    private static Boolean getBooleanValue(Map<String, Object> configMap, String key, Boolean defaultValue) {
        Object value = configMap.get(key);
        if (value instanceof Boolean) {
            return (Boolean) value;
        }
        if (value instanceof String) {
            return Boolean.parseBoolean((String) value);
        }
        return defaultValue;
    }
}
