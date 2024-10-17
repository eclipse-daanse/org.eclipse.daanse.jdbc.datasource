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

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

public class Util {

    private Util() {
        // constructor
    }

    public static void doConfig(SQLServerDataSource ds, MssqlserverConfig config, Map<String, Object> configMap) {

        ds.setUser(config.user());
        ds.setPassword(config._password());
        ds.setApplicationName(config.applicationName());
        ds.setPortNumber(config.portNumber());
        ds.setServerName(config.serverName());
        ds.setInstanceName(config.instanceName());
    }
}
