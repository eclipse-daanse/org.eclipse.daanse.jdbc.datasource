/*
* Copyright (c) 2026 Contributors to the Eclipse Foundation.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
*
* Contributors:
*   SmartCity Jena - initial
*/
package org.eclipse.daanse.jdbc.datasource.clickhouse.api;

import javax.sql.DataSource;

import org.eclipse.daanse.jdbc.datasource.common.annotation.prototype.DataSourceMetaData;
import org.osgi.framework.Bundle;

/**
 * Constants of this {@link Bundle}.
 */
public class Constants {

    private Constants() {
    }

    /**
     * Constant for the {@link DataSourceMetaData#subprotocol()}
     */
    public static final String SUBPROTOCOL = "clickhouse";

    /**
     * Constant for the {@link org.osgi.framework.Constants#SERVICE_PID} of a
     * {@link DataSource} - Service.
     */
    public static final String PID_DATASOURCE = "daanse.jdbc.datasource.clickhouse.DataSource";

    /**
     * Constant for Properties of the Service that could be configured using the
     * {@link Constants#PID_DATASOURCE}.
     *
     * {@link org.eclipse.daanse.jdbc.datasource.clickhouse.api.ocd.BaseConfig#_password()}
     */
    public static final String DATASOURCE_PROPERTY_PASSWORD = ".password";

    /**
     * Constant for Properties of the Service that could be configured using the
     * {@link Constants#PID_DATASOURCE}.
     *
     * {@link org.eclipse.daanse.jdbc.datasource.clickhouse.api.ocd.BaseConfig#user()}
     */
    public static final String DATASOURCE_PROPERTY_USER = "user";

    /**
     * Constant for Properties of the Service that could be configured using the
     * {@link Constants#PID_DATASOURCE}.
     *
     * {@link org.eclipse.daanse.jdbc.datasource.clickhouse.api.ocd.BaseConfig#serverName()}
     */
    public static final String DATASOURCE_PROPERTY_SERVERNAME = "serverName";

    /**
     * Constant for Properties of the Service that could be configured using the
     * {@link Constants#PID_DATASOURCE}.
     *
     * {@link org.eclipse.daanse.jdbc.datasource.clickhouse.api.ocd.BaseConfig#databaseName()}
     */
    public static final String DATASOURCE_PROPERTY_DATABASENAME = "databaseName";

    /**
     * Constant for Properties of the Service that could be configured using the
     * {@link Constants#PID_DATASOURCE}.
     *
     * {@link org.eclipse.daanse.jdbc.datasource.clickhouse.api.ocd.BaseConfig#portNumber()}
     */
    public static final String DATASOURCE_PROPERTY_PORTNUMBER = "portNumber";

    /**
     * Constant for Properties of the Service that could be configured using the
     * {@link Constants#PID_DATASOURCE}.
     *
     * Comma separated {@code key=value} pairs handed to the driver as
     * {@code custom_settings} (per-session ClickHouse server settings, e.g.
     * {@code default_table_engine=MergeTree}).
     *
     * {@link org.eclipse.daanse.jdbc.datasource.clickhouse.api.ocd.BaseConfig#customSettings()}
     */
    public static final String DATASOURCE_PROPERTY_CUSTOM_SETTINGS = "customSettings";

}
