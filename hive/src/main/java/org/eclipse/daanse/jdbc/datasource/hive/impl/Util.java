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
package org.eclipse.daanse.jdbc.datasource.hive.impl;

import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

import org.eclipse.daanse.jdbc.datasource.hive.api.Constants;

public class Util {

    private Util() {
        // constructor
    }

    /**
     * Builds a HiveServer2 URL of the form
     * {@code jdbc:hive2://<host>:<port>/<db>[;ssl=true][;<sessionConf>]} and
     * wraps it in a {@link HiveDataSource}.
     *
     * <p>
     * The same bundle also reaches Apache Impala: Impala serves the
     * HiveServer2 protocol (default port 21050), typically requiring the
     * session conf {@code auth=noSasl}.
     */
    public static HiveDataSource createDataSource(Map<String, Object> configMap) throws SQLException {
        return new HiveDataSource(buildUrl(configMap), buildProperties(configMap));
    }

    static String buildUrl(Map<String, Object> configMap) {
        String serverName = stringValue(configMap, Constants.DATASOURCE_PROPERTY_SERVERNAME, "localhost");
        int portNumber = intValue(configMap, Constants.DATASOURCE_PROPERTY_PORTNUMBER, 10000);
        String databaseName = stringValue(configMap, Constants.DATASOURCE_PROPERTY_DATABASENAME, "default");

        StringBuilder url = new StringBuilder("jdbc:hive2://").append(serverName).append(":").append(portNumber);
        if (!databaseName.isEmpty()) {
            url.append("/").append(databaseName);
        }
        if (booleanValue(configMap, Constants.DATASOURCE_PROPERTY_SSL, false)) {
            url.append(";ssl=true");
        }
        String sessionConf = stringValue(configMap, Constants.DATASOURCE_PROPERTY_SESSION_CONF, "");
        if (!sessionConf.isEmpty()) {
            url.append(";").append(sessionConf);
        }
        return url.toString();
    }

    static Properties buildProperties(Map<String, Object> configMap) {
        Properties properties = new Properties();
        String user = stringValue(configMap, Constants.DATASOURCE_PROPERTY_USER, "");
        if (!user.isEmpty()) {
            properties.setProperty("user", user);
        }
        String password = stringValue(configMap, Constants.DATASOURCE_PROPERTY_PASSWORD, "");
        if (!password.isEmpty()) {
            properties.setProperty("password", password);
        }
        return properties;
    }

    private static String stringValue(Map<String, Object> configMap, String propName, String fallback) {
        Object value = configMap.get(propName);
        return value == null ? fallback : String.valueOf(value);
    }

    private static int intValue(Map<String, Object> configMap, String propName, int fallback) {
        Object value = configMap.get(propName);
        if (value instanceof Integer integer) {
            return integer;
        }
        if (value instanceof String string) {
            try {
                return Integer.parseInt(string);
            } catch (NumberFormatException e) {
                return fallback;
            }
        }
        return fallback;
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
