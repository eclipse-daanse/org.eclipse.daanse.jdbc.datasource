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
package org.eclipse.daanse.jdbc.datasource.metatype.mssqlserver.api;

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
    public static final String SUBPROTOCOL = "mssqlserver";

    /**
     * Constant for the {@link org.osgi.framework.Constants#SERVICE_PID} of a {@link DataSource} and
     * {@link XADataSource} and {@link ConnectionPoolDataSource} - Service.
     */
    public static final String PID_DATASOURCE = "org.eclipse.daanse.jdbc.datasource.metatype.mssqlserver.DataSource";

    /**
     * Constant for the {@link org.osgi.framework.Constants#SERVICE_PID} of a
     * {@link ConnectionPoolDataSource} - Service.
     */
    public static final String PID_DATASOURCE_CP = "org.eclipse.daanse.jdbc.datasource.metatype.mssqlserver.ConnectionPoolDataSource";

    /**
     * Constant for the {@link org.osgi.framework.Constants#SERVICE_PID} of a {@link XADataSource} -
     * Service.
     */
    public static final String PID_DATASOURCE_XA = "org.eclipse.daanse.jdbc.datasource.metatype.mssqlserver.XADataSource";

    /**
     * Constant for Properties of the Service that could be configured using the
     * {@link Constants#PID_DATASOURCE}.
     *
     * {@link org.eclipse.daanse.jdbc.datasource.metatype.mssqlserver.impl.MssqlserverConfig#_password()}
     */
    public static final String DATASOURCE_PROPERTY_PASSWORD = ".password";

    /**
     * Constant for Properties of the Service that could be configured using the
     * {@link Constants#PID_DATASOURCE}.
     *
     * {@link org.eclipse.daanse.jdbc.datasource.metatype.mssqlserver.impl.MssqlserverConfig#user()}
     */
    public static final String DATASOURCE_PROPERTY_USER = "user";

    /**
     * Constant for Properties of the Service that could be configured using the
     * {@link Constants#PID_DATASOURCE}.
     *
     * {@link org.eclipse.daanse.jdbc.datasource.metatype.mssqlserver.impl.MssqlserverConfig#serverName()}
     */
    public static final String DATASOURCE_PROPERTY_SERVERNAME = "serverName";

    /**
     * Constant for Properties of the Service that could be configured using the
     * {@link Constants#PID_DATASOURCE}.
     *
     * {@link org.eclipse.daanse.jdbc.datasource.metatype.mssqlserver.impl.MssqlserverConfig#instanceName()}
     */
    public static final String DATASOURCE_PROPERTY_INSTANCENAME = "instanceName";

    /**
     * Constant for Properties of the Service that could be configured using the
     * {@link Constants#PID_DATASOURCE}.
     *
     * {@link org.eclipse.daanse.jdbc.datasource.metatype.mssqlserver.impl.MssqlserverConfig#applicationName()}
     */
    public static final String DATASOURCE_PROPERTY_APPLICATIONNAME = "applicationName";

    /**
     * Constant for Properties of the Service that could be configured using the
     * {@link Constants#PID_DATASOURCE}.
     *
     * {@link org.eclipse.daanse.jdbc.datasource.metatype.mssqlserver.impl.MssqlserverConfig#portNumber()}
     */
    public static final String DATASOURCE_PROPERTY_PORTNUMBER = "portNumber";

}
