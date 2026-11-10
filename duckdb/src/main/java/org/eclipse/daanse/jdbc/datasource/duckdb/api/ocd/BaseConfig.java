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

/**
 * Attributes shared by the embedded {@link DsConfig} and the remote
 * {@link RemoteDsConfig}: they configure the local DuckDB instance and the JDBC
 * driver, which a remote connection has as well.
 */
public interface BaseConfig {

    String OCD_LOCALIZATION = "OSGI-INF/l10n/org.eclipse.daanse.jdbc.datasource.duckdb.ocd";
    String L10N_PREFIX = "%";

    String L10N_POSTFIX_DESCRIPTION = ".description";
    String L10N_POSTFIX_NAME = ".name";

    String L10N_CUSTOM_USER_AGENT_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_CUSTOM_USER_AGENT + L10N_POSTFIX_NAME;
    String L10N_CUSTOM_USER_AGENT_DESCRIPTION = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_CUSTOM_USER_AGENT
            + L10N_POSTFIX_DESCRIPTION;

    String L10N_STREAM_RESULTS_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_STREAM_RESULTS + L10N_POSTFIX_NAME;
    String L10N_STREAM_RESULTS_DESCRIPTION = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_STREAM_RESULTS
            + L10N_POSTFIX_DESCRIPTION;

    String L10N_AUTO_COMMIT_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_AUTO_COMMIT + L10N_POSTFIX_NAME;
    String L10N_AUTO_COMMIT_DESCRIPTION = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_AUTO_COMMIT
            + L10N_POSTFIX_DESCRIPTION;

    String L10N_IGNORE_UNSUPPORTED_OPTIONS_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_IGNORE_UNSUPPORTED_OPTIONS + L10N_POSTFIX_NAME;
    String L10N_IGNORE_UNSUPPORTED_OPTIONS_DESCRIPTION = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_IGNORE_UNSUPPORTED_OPTIONS
            + L10N_POSTFIX_DESCRIPTION;

    String L10N_JFR_MEMORY_MONITOR_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_JFR_MEMORY_MONITOR + L10N_POSTFIX_NAME;
    String L10N_JFR_MEMORY_MONITOR_DESCRIPTION = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_JFR_MEMORY_MONITOR
            + L10N_POSTFIX_DESCRIPTION;

    String L10N_SETTINGS_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_SETTINGS + L10N_POSTFIX_NAME;
    String L10N_SETTINGS_DESCRIPTION = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_SETTINGS
            + L10N_POSTFIX_DESCRIPTION;

    // Default value constants
    String DEFAULT_CUSTOM_USER_AGENT = "";
    boolean DEFAULT_STREAM_RESULTS = false;
    boolean DEFAULT_AUTO_COMMIT = true;
    boolean DEFAULT_IGNORE_UNSUPPORTED_OPTIONS = false;
    String DEFAULT_JFR_MEMORY_MONITOR = "";

    /**
     * Custom string appended to the user agent reported to DuckDB.
     */
    @AttributeDefinition(name = L10N_CUSTOM_USER_AGENT_NAME, description = L10N_CUSTOM_USER_AGENT_DESCRIPTION, required = false, defaultValue = DEFAULT_CUSTOM_USER_AGENT)
    default String customUserAgent() {
        return DEFAULT_CUSTOM_USER_AGENT;
    }

    /**
     * Stream result sets instead of materializing them. Off by default; on by
     * default for {@code ducklake:} databases.
     */
    @AttributeDefinition(name = L10N_STREAM_RESULTS_NAME, description = L10N_STREAM_RESULTS_DESCRIPTION, required = false, defaultValue = DEFAULT_STREAM_RESULTS
            + "")
    default boolean streamResults() {
        return DEFAULT_STREAM_RESULTS;
    }

    /**
     * Default auto-commit mode of the connections handed out. On by default.
     */
    @AttributeDefinition(name = L10N_AUTO_COMMIT_NAME, description = L10N_AUTO_COMMIT_DESCRIPTION, required = false, defaultValue = DEFAULT_AUTO_COMMIT
            + "")
    default boolean autoCommit() {
        return DEFAULT_AUTO_COMMIT;
    }

    /**
     * Silently drop settings DuckDB does not know instead of refusing to connect.
     */
    @AttributeDefinition(name = L10N_IGNORE_UNSUPPORTED_OPTIONS_NAME, description = L10N_IGNORE_UNSUPPORTED_OPTIONS_DESCRIPTION, required = false, defaultValue = DEFAULT_IGNORE_UNSUPPORTED_OPTIONS
            + "")
    default boolean ignoreUnsupportedOptions() {
        return DEFAULT_IGNORE_UNSUPPORTED_OPTIONS;
    }

    /**
     * Name under which the database instance is tracked in the
     * {@code duckdb.MemoryUsage} JFR event; empty disables the monitoring.
     */
    @AttributeDefinition(name = L10N_JFR_MEMORY_MONITOR_NAME, description = L10N_JFR_MEMORY_MONITOR_DESCRIPTION, required = false, defaultValue = DEFAULT_JFR_MEMORY_MONITOR)
    default String jfrMemoryMonitor() {
        return DEFAULT_JFR_MEMORY_MONITOR;
    }

    /**
     * Engine settings as {@code name=value}, handed to DuckDB as connection
     * properties ({@code threads}, {@code memory_limit},
     * {@code access_mode=READ_ONLY}, ...).
     */
    @AttributeDefinition(name = L10N_SETTINGS_NAME, description = L10N_SETTINGS_DESCRIPTION, required = false)
    default String[] settings() {
        return new String[0];
    }
}
