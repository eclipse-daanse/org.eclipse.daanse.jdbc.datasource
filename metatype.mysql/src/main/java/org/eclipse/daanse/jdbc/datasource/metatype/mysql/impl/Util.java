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

import java.util.Map;

import org.eclipse.daanse.jdbc.datasource.metatype.mysql.api.Constants;

import com.mysql.cj.jdbc.MysqlDataSource;

public class Util {

    private Util() {
        // constructor
    }

    public static void doConfig(MysqlDataSource ds, MySqlConfig config, Map<String, Object> configMap) {

        if (configMap.containsKey(Constants.DATASOURCE_PROPERTY_USER)) {
            ds.setUser(config.user());
        }
        if (configMap.containsKey(Constants.DATASOURCE_PROPERTY_PASSWORD)) {
            ds.setPassword(config._password());
        }
        if (configMap.containsKey(Constants.DATASOURCE_PROPERTY_HOST)) {
            ds.setServerName(config.host());
        }
        if (configMap.containsKey(Constants.DATASOURCE_PROPERTY_DATABASENAME)) {
            ds.setDatabaseName(config.databaseName());
        }
        if (configMap.containsKey(Constants.DATASOURCE_PROPERTY_PORT)) {
            ds.setPort(config.port());
        }

    }
}
