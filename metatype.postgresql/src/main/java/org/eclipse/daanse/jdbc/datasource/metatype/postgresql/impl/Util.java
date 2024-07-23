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
package org.eclipse.daanse.jdbc.datasource.metatype.postgresql.impl;

import java.util.Map;

import org.postgresql.PGProperty;
import org.postgresql.ds.common.BaseDataSource;

public class Util {

    private Util() {
        // constructor
    }

    public static void doConfig(BaseDataSource ds, PostgresConfig config, Map<String, Object> configMap) {

        ds.setProperty(PGProperty.PG_HOST, config.host());
        ds.setProperty(PGProperty.PG_DBNAME, config.dbname());
        ds.setProperty(PGProperty.USER, config.user());
        ds.setProperty(PGProperty.PASSWORD, config._password());
        ds.setPortNumbers( config.ports() );

    }
}
