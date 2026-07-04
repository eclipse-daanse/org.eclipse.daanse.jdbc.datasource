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
package org.eclipse.daanse.jdbc.datasource.duckdb.api;

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
    public static final String SUBPROTOCOL = "duckdb";

    /**
     * Constant for the {@link org.osgi.framework.Constants#SERVICE_PID} of a
     * {@link DataSource} - Service.
     */
    public static final String PID_DATASOURCE = "daanse.jdbc.datasource.duckdb.DataSource";

    /**
     * Constant for Properties of the Service that could be configured using the
     * {@link Constants#PID_DATASOURCE}.
     *
     * Path of the database file for a persistent database, or empty /
     * {@code :memory:} for an in-memory database. NOTE: a DuckDB in-memory database
     * is private to each connection — data written over one connection is not
     * visible to another.
     *
     * {@link org.eclipse.daanse.jdbc.datasource.duckdb.api.ocd.BaseConfig#databaseName()}
     */
    public static final String DATASOURCE_PROPERTY_DATABASENAME = "databaseName";

    /**
     * Constant for Properties of the Service that could be configured using the
     * {@link Constants#PID_DATASOURCE}.
     *
     * Open the database in read-only mode (file databases only).
     *
     * {@link org.eclipse.daanse.jdbc.datasource.duckdb.api.ocd.BaseConfig#readOnly()}
     */
    public static final String DATASOURCE_PROPERTY_READ_ONLY = "readOnly";

}
