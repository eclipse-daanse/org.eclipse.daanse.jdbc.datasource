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
package org.eclipse.daanse.jdbc.datasource.duckdb.api.ocd;

import org.eclipse.daanse.jdbc.datasource.duckdb.api.Constants;
import org.osgi.service.metatype.annotations.AttributeDefinition;

public interface BaseConfig {

    String OCD_LOCALIZATION = "OSGI-INF/l10n/org.eclipse.daanse.jdbc.datasource.duckdb.ocd";
    String L10N_PREFIX = "%";

    String L10N_POSTFIX_DESCRIPTION = ".description";
    String L10N_POSTFIX_NAME = ".name";

    String L10N_DATABASENAME_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_DATABASENAME + L10N_POSTFIX_NAME;
    String L10N_DATABASENAME_DESCRIPTION = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_DATABASENAME
            + L10N_POSTFIX_DESCRIPTION;

    String L10N_READ_ONLY_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_READ_ONLY + L10N_POSTFIX_NAME;
    String L10N_READ_ONLY_DESCRIPTION = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_READ_ONLY
            + L10N_POSTFIX_DESCRIPTION;

    // Default value constants
    String DEFAULT_DATABASE_NAME = "";
    boolean DEFAULT_READ_ONLY = false;

    /**
     * File path for a persistent database, or empty / {@code :memory:} for an
     * in-memory database. NOTE: a DuckDB in-memory database is private to each
     * connection.
     */
    @AttributeDefinition(name = L10N_DATABASENAME_NAME, description = L10N_DATABASENAME_DESCRIPTION, defaultValue = DEFAULT_DATABASE_NAME)
    default String databaseName() {
        return DEFAULT_DATABASE_NAME;
    }

    @AttributeDefinition(name = L10N_READ_ONLY_NAME, description = L10N_READ_ONLY_DESCRIPTION, defaultValue = DEFAULT_READ_ONLY
            + "")
    default boolean readOnly() {
        return DEFAULT_READ_ONLY;
    }
}
