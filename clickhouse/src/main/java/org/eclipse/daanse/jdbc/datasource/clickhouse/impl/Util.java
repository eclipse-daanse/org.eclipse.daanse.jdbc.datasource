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
package org.eclipse.daanse.jdbc.datasource.clickhouse.impl;

import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

import org.eclipse.daanse.jdbc.datasource.clickhouse.api.Constants;

import com.clickhouse.jdbc.DataSourceImpl;

public class Util {

    private Util() {
        // constructor
    }

    /**
     * The ClickHouse V2 DataSource ({@code DataSourceImpl}
     *
     * <p>
     * Per-session server settings (e.g. {@code default_table_engine}) still travel
     * via the {@code custom_settings} connection property: the V2 driver's
     * {@code DriverProperties.CUSTOM_SETTINGS} maps each comma separated
     * {@code key=value} pair to a server setting.
     */
    public static DataSourceImpl createDataSource(Map<String, Object> configMap) throws SQLException {
        String serverName = stringValue(configMap, Constants.DATASOURCE_PROPERTY_SERVERNAME, "localhost");
        int portNumber = intValue(configMap, Constants.DATASOURCE_PROPERTY_PORTNUMBER, 8123);
        String databaseName = stringValue(configMap, Constants.DATASOURCE_PROPERTY_DATABASENAME, "");

        StringBuilder url = new StringBuilder("jdbc:clickhouse://").append(serverName).append(":").append(portNumber);
        if (!databaseName.isEmpty()) {
            url.append("/").append(databaseName);
        }

        Properties properties = new Properties();
        String user = stringValue(configMap, Constants.DATASOURCE_PROPERTY_USER, "");
        if (!user.isEmpty()) {
            properties.setProperty("user", user);
        }
        String password = stringValue(configMap, Constants.DATASOURCE_PROPERTY_PASSWORD, "");
        if (!password.isEmpty()) {
            properties.setProperty("password", password);
        }
        String customSettings = stringValue(configMap, Constants.DATASOURCE_PROPERTY_CUSTOM_SETTINGS, "");
        if (!customSettings.isEmpty()) {
            properties.setProperty("custom_settings", customSettings);
        }

        return new DataSourceImpl(url.toString(), properties);
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
}
