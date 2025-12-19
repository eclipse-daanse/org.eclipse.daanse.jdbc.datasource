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
package org.eclipse.daanse.jdbc.datasource.postgresql.impl;

import java.util.Map;

import org.eclipse.daanse.jdbc.datasource.postgresql.api.Constants;
import org.postgresql.PGProperty;
import org.postgresql.ds.common.BaseDataSource;

public class Util {

    private Util() {
        // constructor
    }

    public static void doConfig(BaseDataSource ds, Map<String, Object> configMap) {

        // Basic connection properties
        setStringProperty(ds, PGProperty.PG_HOST, configMap, Constants.DATASOURCE_PROPERTY_HOST, "localhost");
        setStringProperty(ds, PGProperty.PG_DBNAME, configMap, Constants.DATASOURCE_PROPERTY_DBNAME, "");
        setStringProperty(ds, PGProperty.USER, configMap, Constants.DATASOURCE_PROPERTY_USER, "");
        setStringProperty(ds, PGProperty.PASSWORD, configMap, Constants.DATASOURCE_PROPERTY_PASSWORD, "");
        setPortNumbers(ds, configMap, Constants.DATASOURCE_PROPERTY_PORT, new int[] { 5432 });

        // Additional properties
        setStringPropertyIfNotEmpty(ds, PGProperty.APPLICATION_NAME, configMap,
                Constants.DATASOURCE_PROPERTY_APPLICATION_NAME);
        setStringPropertyIfNotEmpty(ds, PGProperty.CURRENT_SCHEMA, configMap,
                Constants.DATASOURCE_PROPERTY_CURRENT_SCHEMA);
        setStringPropertyIfNotEmpty(ds, PGProperty.SSL_MODE, configMap, Constants.DATASOURCE_PROPERTY_SSL_MODE);
        setStringPropertyIfNotEmpty(ds, PGProperty.SSL_CERT, configMap, Constants.DATASOURCE_PROPERTY_SSL_CERT);
        setStringPropertyIfNotEmpty(ds, PGProperty.SSL_KEY, configMap, Constants.DATASOURCE_PROPERTY_SSL_KEY);
        setStringPropertyIfNotEmpty(ds, PGProperty.SSL_ROOT_CERT, configMap,
                Constants.DATASOURCE_PROPERTY_SSL_ROOT_CERT);
        setStringPropertyIfNotEmpty(ds, PGProperty.TARGET_SERVER_TYPE, configMap,
                Constants.DATASOURCE_PROPERTY_TARGET_SERVER_TYPE);

        // Integer properties
        setIntPropertyIfNotZero(ds, PGProperty.CONNECT_TIMEOUT, configMap,
                Constants.DATASOURCE_PROPERTY_CONNECT_TIMEOUT);
        setIntPropertyIfNotZero(ds, PGProperty.LOGIN_TIMEOUT, configMap, Constants.DATASOURCE_PROPERTY_LOGIN_TIMEOUT);
        setIntPropertyIfNotZero(ds, PGProperty.SOCKET_TIMEOUT, configMap, Constants.DATASOURCE_PROPERTY_SOCKET_TIMEOUT);
        setIntPropertyIfNotZero(ds, PGProperty.DEFAULT_ROW_FETCH_SIZE, configMap,
                Constants.DATASOURCE_PROPERTY_DEFAULT_ROW_FETCH_SIZE);
        setIntPropertyWithDefault(ds, PGProperty.PREPARE_THRESHOLD, configMap,
                Constants.DATASOURCE_PROPERTY_PREPARE_THRESHOLD, 5);
        setIntPropertyWithDefault(ds, PGProperty.PREPARED_STATEMENT_CACHE_QUERIES, configMap,
                Constants.DATASOURCE_PROPERTY_PREPARED_STATEMENT_CACHE_QUERIES, 256);

        // Boolean properties
        setBooleanPropertyIfTrue(ds, PGProperty.SSL, configMap, Constants.DATASOURCE_PROPERTY_SSL);
        setBooleanPropertyIfTrue(ds, PGProperty.LOAD_BALANCE_HOSTS, configMap,
                Constants.DATASOURCE_PROPERTY_LOAD_BALANCE_HOSTS);
        setBooleanPropertyIfTrue(ds, PGProperty.TCP_KEEP_ALIVE, configMap,
                Constants.DATASOURCE_PROPERTY_TCP_KEEP_ALIVE);
        setBooleanPropertyIfTrue(ds, PGProperty.READ_ONLY, configMap, Constants.DATASOURCE_PROPERTY_READ_ONLY);
    }

    private static void setStringPropertyIfNotEmpty(BaseDataSource ds, PGProperty property,
            Map<String, Object> configMap, String configKey) {
        if (configMap.containsKey(configKey)) {
            String value = (String) configMap.get(configKey);
            if (value != null && !value.trim().isEmpty()) {
                ds.setProperty(property, value);
            }
        }
    }

    private static void setIntPropertyIfNotZero(BaseDataSource ds, PGProperty property, Map<String, Object> configMap,
            String configKey) {
        if (configMap.containsKey(configKey)) {
            Integer value = (Integer) configMap.get(configKey);
            if (value != null && value != 0) {
                ds.setProperty(property, String.valueOf(value));
            }
        }
    }

    private static void setBooleanPropertyIfTrue(BaseDataSource ds, PGProperty property, Map<String, Object> configMap,
            String configKey) {
        if (configMap.containsKey(configKey)) {
            Boolean value = (Boolean) configMap.get(configKey);
            if (value != null && value) {
                ds.setProperty(property, String.valueOf(value));
            }
        }
    }

    private static void setStringProperty(BaseDataSource ds, PGProperty property, Map<String, Object> configMap,
            String configKey, String defaultValue) {
        String value = defaultValue;
        if (configMap.containsKey(configKey)) {
            String configValue = (String) configMap.get(configKey);
            if (configValue != null) {
                value = configValue;
            }
        }
        ds.setProperty(property, value);
    }

    private static void setPortNumbers(BaseDataSource ds, Map<String, Object> configMap, String configKey,
            int[] defaultPorts) {
        if (configMap.containsKey(configKey)) {
            Object oPort = configMap.get(configKey);
            if (oPort != null) {
                switch (oPort) {
                case Integer port:
                    ds.setPortNumbers(new int[] { port });
                    break;
                case int[] port:
                    ds.setPortNumbers(port);
                    break;
                case Integer[] port:
                    Integer[] portArray = (Integer[]) port;
                    int[] ports = new int[portArray.length];
                    for (int i = 0; i < portArray.length; i++) {
                        ports[i] = portArray[i];
                    }
                    ds.setPortNumbers(ports);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid port number type: " + oPort.getClass().getName());
                }
            }
        } else {
            ds.setPortNumbers(defaultPorts);
        }
    }

    private static void setIntPropertyWithDefault(BaseDataSource ds, PGProperty property, Map<String, Object> configMap,
            String configKey, int defaultValue) {
        Integer value = defaultValue;
        if (configMap.containsKey(configKey)) {
            Integer configValue = (Integer) configMap.get(configKey);
            if (configValue != null) {
                value = configValue;
            }
        }
        ds.setProperty(property, String.valueOf(value));
    }
}
