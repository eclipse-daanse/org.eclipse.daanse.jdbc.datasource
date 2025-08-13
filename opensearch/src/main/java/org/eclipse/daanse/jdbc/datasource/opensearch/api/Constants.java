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
package org.eclipse.daanse.jdbc.datasource.opensearch.api;

import javax.sql.ConnectionPoolDataSource;
import javax.sql.DataSource;
import javax.sql.XADataSource;

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
     *
     */
    public static final String SUBPROTOCOL = "opensearch";

    /**
     * Constant for the {@link org.osgi.framework.Constants#SERVICE_PID} of a {@link DataSource} and
     * {@link XADataSource} and {@link ConnectionPoolDataSource} - Service.
     */
    public static final String PID_DATASOURCE = "daanse.jdbc.datasource.opensearch.OSDataSource";

    /**
     * Constant for Properties of the Service that could be configured using the
     * {@link Constants#PID_DATASOURCE}.
     *
     * {@link org.eclipse.daanse.jdbc.datasource.opensearch.api.ocd.BaseConfig#host()}
     */
    public static final String DATASOURCE_PROPERTY_HOST = "host";

    /**
     * Constant for Properties of the Service that could be configured using the
     * {@link Constants#PID_DATASOURCE}.
     *
     * {@link org.eclipse.daanse.jdbc.datasource.opensearch.api.ocd.BaseConfig#_password()}
     */
    public static final String DATASOURCE_PROPERTY_PASSWORD = ".password";

    /**
     * Constant for Properties of the Service that could be configured using the
     * {@link Constants#PID_DATASOURCE}.
     *
     * {@link org.eclipse.daanse.jdbc.datasource.opensearch.api.ocd.BaseConfig#username()}
     */
    public static final String DATASOURCE_PROPERTY_USERNAME = "username";

    /**
     * Constant for Properties of the Service that could be configured using the
     * {@link Constants#PID_DATASOURCE}.
     *
     * {@link org.eclipse.daanse.jdbc.datasource.opensearch.api.ocd.BaseConfig#port()}
     */
    public static final String DATASOURCE_PROPERTY_PORT = "port";

    /**
     * Constant for Properties of the Service that could be configured using the
     * {@link Constants#PID_DATASOURCE}.
     *
     * {@link org.eclipse.daanse.jdbc.datasource.opensearch.api.ocd.BaseConfig#path()}
     */
    public static final String DATASOURCE_PROPERTY_PATH = "path";

    /**
     * Constant for Properties of the Service that could be configured using the
     * {@link Constants#PID_DATASOURCE}.
     *
     * {@link org.eclipse.daanse.jdbc.datasource.opensearch.api.ocd.BaseConfig#usessl()}
     */
    public static final String DATASOURCE_PROPERTY_USESSL = "usessl";

    /**
     * Constant for Properties of the Service that could be configured using the
     * {@link Constants#PID_DATASOURCE}.
     *
     * {@link org.eclipse.daanse.jdbc.datasource.opensearch.api.ocd.BaseConfig#trustSelfSigned()}
     */
    public static final String DATASOURCE_PROPERTY_TRUSTSELFSIGNED = "trustSelfSigned";

    /**
     * Constant for Properties of the Service that could be configured using the
     * {@link Constants#PID_DATASOURCE}.
     *
     * {@link org.eclipse.daanse.jdbc.datasource.opensearch.api.ocd.BaseConfig#auth()}
     */
    public static final String DATASOURCE_PROPERTY_AUTH = "auth";

}
