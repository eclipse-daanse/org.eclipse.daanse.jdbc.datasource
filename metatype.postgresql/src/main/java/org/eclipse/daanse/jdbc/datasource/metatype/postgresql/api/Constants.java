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
package org.eclipse.daanse.jdbc.datasource.metatype.postgresql.api;

import javax.sql.ConnectionPoolDataSource;
import javax.sql.DataSource;
import javax.sql.XADataSource;

import org.eclipse.daanse.jdbc.datasource.metatype.common.annotation.prototype.DataSourceMetaData;
import org.osgi.framework.Bundle;
import org.postgresql.PGProperty;

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
    public static final String SUBPROTOCOL = "postgresql";


    /**
     * Constant for the {@link org.osgi.framework.Constants#SERVICE_PID} of a
     * {@link DataSource}
     */
    public static final String PID_DATASOURCE = "org.eclipse.daanse.jdbc.datasource.metatype.postgresql.DataSource";

    /**
     * Constant for the {@link org.osgi.framework.Constants#SERVICE_PID} of a
     * {@link ConnectionPoolDataSource} - Service.
     */
    public static final String PID_DATASOURCE_CP = "org.eclipse.daanse.jdbc.datasource.metatype.postgresql.ConnectionPoolDataSource";

    /**
     * Constant for the {@link org.osgi.framework.Constants#SERVICE_PID} of a
     * {@link XADataSource} - Service.
     */
    public static final String PID_DATASOURCE_XA = "org.eclipse.daanse.jdbc.datasource.metatype.postgresql.XADataSource";

    /**
     * Constant for Properties of the Service that could be configured.
     *
     * {@link org.eclipse.daanse.jdbc.datasource.metatype.postgresql.impl.PostgresConfig#_password()}
     * {@link PGProperty#PASSWORD}
     */
    public static final String DATASOURCE_PROPERTY_PASSWORD = ".password";

    /**
     * Constant for Properties of the Service that could be configured.
     *
     * {@link org.eclipse.daanse.jdbc.datasource.metatype.postgresql.impl.PostgresConfig#user()}
     * {@link PGProperty#USER}
     */
    public static final String DATASOURCE_PROPERTY_USER = "user";

    /**
     * Constant for Properties of the Service that could be configured.
     *
     * {@link org.eclipse.daanse.jdbc.datasource.metatype.postgresql.impl.PostgresConfig#host()}
     * {@link PGProperty#PG_HOST}
     */
    public static final String DATASOURCE_PROPERTY_PG_HOST = "pg.host";

    /**
     * Constant for Properties of the Service that could be configured.
     *
     * {@link org.eclipse.daanse.jdbc.datasource.metatype.postgresql.impl.PostgresConfig#dbname()}
     * {@link PGProperty#PG_DBNAME}
     */
    public static final String DATASOURCE_PROPERTY_PG_DBNAME = "pg.dbname";

    /**
     * Constant for Properties of the Service that could be configured.
     *
     * {@link org.eclipse.daanse.jdbc.datasource.metatype.postgresql.impl.PostgresConfig#ports()}
     * {@link PGProperty#PG_PORT}
     */
    public static final String DATASOURCE_PROPERTY_PG_PORT = "pg.port";

}
