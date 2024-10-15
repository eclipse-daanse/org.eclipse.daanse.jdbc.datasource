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
package org.eclipse.daanse.jdbc.datasource.metatype.mysql.api;

import javax.sql.ConnectionPoolDataSource;
import javax.sql.DataSource;
import javax.sql.XADataSource;

import org.eclipse.daanse.jdbc.datasource.metatype.common.annotation.prototype.DataSourceMetaData;
import org.osgi.framework.Bundle;

/**
 * Constants of this {@link Bundle}.
 */
public class Constants {

    private Constants() {
    }
    /**
     * Constant for the {@link DataSourceMetaData#subprotocol()}
     *
     */
    public static final String SUBPROTOCOL = "mysql";


    /**
     * Constant for the {@link org.osgi.framework.Constants#SERVICE_PID} of a
     * {@link DataSource}
     */
    public static final String PID_DATASOURCE = "org.eclipse.daanse.jdbc.datasource.metatype.mysql.DataSource";

    /**
     * Constant for the {@link org.osgi.framework.Constants#SERVICE_PID} of a
     * {@link ConnectionPoolDataSource} - Service.
     */
    public static final String PID_DATASOURCE_CP = "org.eclipse.daanse.jdbc.datasource.metatype.mysql.ConnectionPoolDataSource";

    /**
     * Constant for the {@link org.osgi.framework.Constants#SERVICE_PID} of a
     * {@link XADataSource} - Service.
     */
    public static final String PID_DATASOURCE_XA = "org.eclipse.daanse.jdbc.datasource.metatype.mysql.XADataSource";

    /**
     * Constant for Properties of the Service that could be configured.
     *
     * {@link org.eclipse.daanse.jdbc.datasource.metatype.mysql.impl.MySqlConfig#_password()}
     */
    public static final String DATASOURCE_PROPERTY_PASSWORD = ".password";

    /**
     * Constant for Properties of the Service that could be configured.
     *
     * {@link org.eclipse.daanse.jdbc.datasource.metatype.mysql.impl.MySqlConfig#user()}
     */
    public static final String DATASOURCE_PROPERTY_USER = "user";

    /**
     * Constant for Properties of the Service that could be configured.
     *
     * {@link org.eclipse.daanse.jdbc.datasource.metatype.mysql.impl.MySqlConfig#host()}
     */
    public static final String DATASOURCE_PROPERTY_HOST = "host";

    /**
     * Constant for Properties of the Service that could be configured.
     *
     * {@link org.eclipse.daanse.jdbc.datasource.metatype.mysql.impl.MySqlConfig#databaseName()}
     */
    public static final String DATASOURCE_PROPERTY_DATABASENAME = "databaseName";

    /**
     * Constant for Properties of the Service that could be configured.
     *
     * {@link org.eclipse.daanse.jdbc.datasource.metatype.mysql.impl.MySqlConfig#port()}
     */
    public static final String DATASOURCE_PROPERTY_PORT = "port";

}
