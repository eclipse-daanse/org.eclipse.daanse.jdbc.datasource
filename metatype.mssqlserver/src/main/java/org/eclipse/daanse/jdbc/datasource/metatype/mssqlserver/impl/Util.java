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
package org.eclipse.daanse.jdbc.datasource.metatype.mssqlserver.impl;

import java.util.Map;
import java.util.function.Consumer;

import org.eclipse.daanse.jdbc.datasource.metatype.mssqlserver.api.Constants;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

public class Util {

    private Util() {
        // constructor
    }

    public static void doConfig(SQLServerDataSource ds, Map<String, Object> configMap) {

        // Basic connection properties
        setStringValueFromMap(ds::setUser, configMap, Constants.DATASOURCE_PROPERTY_USER);
        setStringValueFromMap(ds::setPassword, configMap, Constants.DATASOURCE_PROPERTY_PASSWORD);
        setStringValueFromMap(ds::setApplicationName, configMap, Constants.DATASOURCE_PROPERTY_APPLICATIONNAME);
        setIntValueFromMap(ds::setPortNumber, configMap, Constants.DATASOURCE_PROPERTY_PORTNUMBER);
        setStringValueFromMap(ds::setServerName, configMap, Constants.DATASOURCE_PROPERTY_SERVERNAME);
        setStringValueFromMap(ds::setInstanceName, configMap, Constants.DATASOURCE_PROPERTY_INSTANCENAME);

        // Additional properties with null checks and defaults
        setStringPropertyIfNotEmpty(ds::setDatabaseName, configMap, Constants.DATASOURCE_PROPERTY_DATABASENAME);
        setStringPropertyIfNotEmpty(ds::setEncrypt, configMap, Constants.DATASOURCE_PROPERTY_ENCRYPT);
        setStringPropertyIfNotEmpty(ds::setAuthentication, configMap, Constants.DATASOURCE_PROPERTY_AUTHENTICATION);
        setStringPropertyIfNotEmpty(ds::setResponseBuffering, configMap, Constants.DATASOURCE_PROPERTY_RESPONSE_BUFFERING);
        setStringPropertyIfNotEmpty(ds::setSelectMethod, configMap, Constants.DATASOURCE_PROPERTY_SELECT_METHOD);
        setStringPropertyIfNotEmpty(ds::setApplicationIntent, configMap, Constants.DATASOURCE_PROPERTY_APPLICATION_INTENT);
        setStringPropertyIfNotEmpty(ds::setFailoverPartner, configMap, Constants.DATASOURCE_PROPERTY_FAILOVER_PARTNER);
        setStringPropertyIfNotEmpty(ds::setColumnEncryptionSetting, configMap, Constants.DATASOURCE_PROPERTY_COLUMN_ENCRYPTION);

        // Integer properties
        setIntPropertyIfDifferent(ds::setLoginTimeout, configMap, Constants.DATASOURCE_PROPERTY_LOGIN_TIMEOUT, 30);
        setIntPropertyIfDifferent(ds::setQueryTimeout, configMap, Constants.DATASOURCE_PROPERTY_QUERY_TIMEOUT, -1);
        setIntPropertyIfNotZero(ds::setSocketTimeout, configMap, Constants.DATASOURCE_PROPERTY_SOCKET_TIMEOUT);
        setIntPropertyIfDifferent(ds::setPacketSize, configMap, Constants.DATASOURCE_PROPERTY_PACKET_SIZE, 4096);
        setIntPropertyIfDifferent(ds::setLockTimeout, configMap, Constants.DATASOURCE_PROPERTY_LOCK_TIMEOUT, -1);

        // Boolean properties
        setBooleanPropertyIfTrue(ds::setTrustServerCertificate, configMap, Constants.DATASOURCE_PROPERTY_TRUST_SERVER_CERTIFICATE);
        setBooleanPropertyIfTrue(ds::setIntegratedSecurity, configMap, Constants.DATASOURCE_PROPERTY_INTEGRATED_SECURITY);
        setBooleanPropertyIfTrue(ds::setMultiSubnetFailover, configMap, Constants.DATASOURCE_PROPERTY_MULTI_SUBNET_FAILOVER);
        setBooleanPropertyIfDifferent(ds::setSendStringParametersAsUnicode, configMap, Constants.DATASOURCE_PROPERTY_SEND_STRING_PARAMETERS_AS_UNICODE, true);
    }

    private static void setStringPropertyIfNotEmpty(Consumer<String> setterMethod,
            Map<String, Object> configMap, String configKey) {
        if (configMap.containsKey(configKey)) {
            String val = (String) configMap.get(configKey);
            if (val != null && !val.trim().isEmpty()) {
                setterMethod.accept(val);
            }
        }
    }

    private static void setIntPropertyIfNotZero(Consumer<Integer> setterMethod,
            Map<String, Object> configMap, String configKey) {
        if (configMap.containsKey(configKey)) {
            Integer val = (Integer) configMap.get(configKey);
            if (val != null && val != 0) {
                setterMethod.accept(val);
            }
        }
    }

    private static void setIntPropertyIfDifferent(Consumer<Integer> setterMethod,
            Map<String, Object> configMap, String configKey, int defaultValue) {
        if (configMap.containsKey(configKey)) {
            Integer val = (Integer) configMap.get(configKey);
            if (val != null && val != defaultValue) {
                setterMethod.accept(val);
            }
        }
    }

    private static void setBooleanPropertyIfTrue(Consumer<Boolean> setterMethod,
            Map<String, Object> configMap, String configKey) {
        if (configMap.containsKey(configKey)) {
            Boolean val = (Boolean) configMap.get(configKey);
            if (val != null && val) {
                setterMethod.accept(val);
            }
        }
    }

    private static void setBooleanPropertyIfDifferent(Consumer<Boolean> setterMethod,
            Map<String, Object> configMap, String configKey, boolean defaultValue) {
        if (configMap.containsKey(configKey)) {
            Boolean val = (Boolean) configMap.get(configKey);
            if (val != null && val != defaultValue) {
                setterMethod.accept(val);
            }
        }
    }

    private static void setStringValueFromMap(Consumer<String> setterMethod,
            Map<String, Object> configMap, String configKey) {
        if (configMap.containsKey(configKey)) {
            String val = (String) configMap.get(configKey);
            if (val != null) {
                setterMethod.accept(val);
            }
        }
    }

    private static void setIntValueFromMap(Consumer<Integer> setterMethod,
            Map<String, Object> configMap, String configKey) {
        if (configMap.containsKey(configKey)) {
            Integer val = (Integer) configMap.get(configKey);
            if (val != null) {
                setterMethod.accept(val);
            }
        }
    }
}
