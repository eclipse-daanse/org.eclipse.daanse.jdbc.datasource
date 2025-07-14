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
import java.util.function.Supplier;

import org.eclipse.daanse.jdbc.datasource.metatype.oracle.api.Constants;

import oracle.jdbc.datasource.impl.OracleDataSource;

public class Util {

    private Util() {
        // constructor
    }

    public static void doConfig(OracleDataSource ds, OracleConfig config, Map<String, Object> configMap) {
        ds.setDriverType("thin");
        setValueIfNotNull(ds::setUser, config::user, configMap, Constants.DATASOURCE_PROPERTY_USER);
        setValueIfNotNull(ds::setServerName, config::_password, configMap, Constants.DATASOURCE_PROPERTY_PASSWORD);

        setValueIfNotNull(ds::setServiceName, config::serviceName, configMap,
                Constants.DATASOURCE_PROPERTY_SERVICENAME);
        setValueIfNotNull(ds::setPortNumber, config::portNumber, configMap, Constants.DATASOURCE_PROPERTY_PORTNUMBER);
        setValueIfNotNull(ds::setServerName, config::serverName, configMap, Constants.DATASOURCE_PROPERTY_SERVERNAME);
        setValueIfNotNull(ds::setDatabaseName, config::databaseName, configMap,
                Constants.DATASOURCE_PROPERTY_DATABASENAME);

    }

    private static <T> void setValueIfNotNull(Consumer<T> setterMethod, Supplier<T> value,
            Map<String, Object> configMap, String propName) {
        if (configMap.containsKey(propName)) {
            setterMethod.accept(value.get());
        }
    }
}
