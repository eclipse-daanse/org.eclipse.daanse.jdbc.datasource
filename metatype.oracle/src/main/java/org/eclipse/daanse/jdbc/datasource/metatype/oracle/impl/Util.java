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
package org.eclipse.daanse.jdbc.datasource.metatype.oracle.impl;

import java.util.Map;
import java.util.function.Consumer;

import org.eclipse.daanse.jdbc.datasource.metatype.oracle.api.Constants;

import oracle.jdbc.datasource.impl.OracleDataSource;

public class Util {

    private Util() {
        // constructor
    }

    public static void doConfig(OracleDataSource ds, Map<String, Object> configMap) {
        ds.setDriverType("thin");
        setValueFromMap(ds::setUser, configMap, Constants.DATASOURCE_PROPERTY_USER);
        setValueFromMap(ds::setPassword, configMap, Constants.DATASOURCE_PROPERTY_PASSWORD);

        setValueFromMap(ds::setServiceName, configMap,
                Constants.DATASOURCE_PROPERTY_SERVICENAME);
        setValueFromMap(ds::setPortNumber, configMap, Constants.DATASOURCE_PROPERTY_PORTNUMBER);
        setValueFromMap(ds::setServerName, configMap, Constants.DATASOURCE_PROPERTY_SERVERNAME);
        setValueFromMap(ds::setDatabaseName, configMap,
                Constants.DATASOURCE_PROPERTY_DATABASENAME);

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
}
