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
package org.eclipse.daanse.jdbc.datasource.mariadb.api.ocd;

import org.eclipse.daanse.jdbc.datasource.mariadb.api.Constants;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.Option;

public interface BaseConfig {

    String OCD_LOCALIZATION = "OSGI-INF/l10n/org.eclipse.daanse.jdbc.datasource.mariadb.ocd";
    String L10N_PREFIX = "%";

    String L10N_POSTFIX_DESCRIPTION = ".description";
    String L10N_POSTFIX_NAME = ".name";
    String L10N_POSTFIX_OPTION = ".option";
    String L10N_POSTFIX_LABEL = ".label";

    String L10N_PASSWORD_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_PASSWORD + L10N_POSTFIX_NAME;
    String L10N_PASSWORD_DESCRIPTION = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_PASSWORD + L10N_POSTFIX_DESCRIPTION;

    String L10N_USER_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_USER + L10N_POSTFIX_NAME;
    String L10N_USER_DESCRIPTION = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_USER + L10N_POSTFIX_DESCRIPTION;

    String L10N_HOST_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_HOST + L10N_POSTFIX_NAME;
    String L10N_HOST_DESCRIPTION = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_HOST + L10N_POSTFIX_DESCRIPTION;

    String L10N_PORT_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_PORT + L10N_POSTFIX_NAME;
    String L10N_PORT_DESCRIPTION = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_PORT + L10N_POSTFIX_DESCRIPTION;

    String L10N_DATABASENAME_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_DATABASENAME + L10N_POSTFIX_NAME;
    String L10N_DATABASENAME_DESCRIPTION = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_DATABASENAME
            + L10N_POSTFIX_DESCRIPTION;

    String L10N_CONNECT_TIMEOUT_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_CONNECT_TIMEOUT + L10N_POSTFIX_NAME;
    String L10N_CONNECT_TIMEOUT_DESCRIPTION = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_CONNECT_TIMEOUT
            + L10N_POSTFIX_DESCRIPTION;

    String L10N_USE_SERVER_PREPARED_STMTS_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_USE_SERVER_PREPARED_STMTS
            + L10N_POSTFIX_NAME;
    String L10N_USE_SERVER_PREPARED_STMTS_DESCRIPTION = L10N_PREFIX
            + Constants.DATASOURCE_PROPERTY_USE_SERVER_PREPARED_STMTS + L10N_POSTFIX_DESCRIPTION;

    String L10N_ALLOW_LOCAL_INFILE_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_ALLOW_LOCAL_INFILE
            + L10N_POSTFIX_NAME;
    String L10N_ALLOW_LOCAL_INFILE_DESCRIPTION = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_ALLOW_LOCAL_INFILE
            + L10N_POSTFIX_DESCRIPTION;

    String L10N_SSL_MODE_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_SSL_MODE + L10N_POSTFIX_NAME;
    String L10N_SSL_MODE_DESCRIPTION = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_SSL_MODE + L10N_POSTFIX_DESCRIPTION;

    String L10N_USE_COMPRESSION_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_USE_COMPRESSION + L10N_POSTFIX_NAME;
    String L10N_USE_COMPRESSION_DESCRIPTION = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_USE_COMPRESSION
            + L10N_POSTFIX_DESCRIPTION;

    String L10N_SOCKET_TIMEOUT_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_SOCKET_TIMEOUT + L10N_POSTFIX_NAME;
    String L10N_SOCKET_TIMEOUT_DESCRIPTION = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_SOCKET_TIMEOUT
            + L10N_POSTFIX_DESCRIPTION;

    String L10N_ALLOW_MULTI_QUERIES_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_ALLOW_MULTI_QUERIES
            + L10N_POSTFIX_NAME;
    String L10N_ALLOW_MULTI_QUERIES_DESCRIPTION = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_ALLOW_MULTI_QUERIES
            + L10N_POSTFIX_DESCRIPTION;

    String L10N_SESSION_VARIABLES_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_SESSION_VARIABLES
            + L10N_POSTFIX_NAME;
    String L10N_SESSION_VARIABLES_DESCRIPTION = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_SESSION_VARIABLES
            + L10N_POSTFIX_DESCRIPTION;

    // Option label constants for sslMode
    String L10N_SSL_MODE_OPTION_DISABLE_LABEL = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_SSL_MODE
            + L10N_POSTFIX_OPTION + ".disable" + L10N_POSTFIX_LABEL;
    String L10N_SSL_MODE_OPTION_TRUST_LABEL = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_SSL_MODE + L10N_POSTFIX_OPTION
            + ".trust" + L10N_POSTFIX_LABEL;
    String L10N_SSL_MODE_OPTION_VERIFY_CA_LABEL = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_SSL_MODE
            + L10N_POSTFIX_OPTION + ".verify-ca" + L10N_POSTFIX_LABEL;
    String L10N_SSL_MODE_OPTION_VERIFY_FULL_LABEL = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_SSL_MODE
            + L10N_POSTFIX_OPTION + ".verify-full" + L10N_POSTFIX_LABEL;

    // Default value constants
    String DEFAULT_PASSWORD = "";
    String DEFAULT_USER = "";
    String DEFAULT_DATABASENAME = "";
    String DEFAULT_HOST = "localhost";
    int DEFAULT_PORT = 3306;
    int DEFAULT_CONNECT_TIMEOUT = 30000;
    boolean DEFAULT_USE_SERVER_PREPARED_STMTS = false;
    boolean DEFAULT_ALLOW_LOCAL_INFILE = true;
    String DEFAULT_SSL_MODE = "disable";
    boolean DEFAULT_USE_COMPRESSION = false;
    int DEFAULT_SOCKET_TIMEOUT = 0;
    boolean DEFAULT_ALLOW_MULTI_QUERIES = false;
    String DEFAULT_SESSION_VARIABLES = "";

    @AttributeDefinition(name = L10N_PASSWORD_NAME, description = L10N_PASSWORD_DESCRIPTION, type = AttributeType.PASSWORD, defaultValue = DEFAULT_PASSWORD)
    default String _password() {
        return DEFAULT_PASSWORD;
    }

    @AttributeDefinition(name = L10N_USER_NAME, description = L10N_USER_DESCRIPTION, defaultValue = DEFAULT_USER)
    default String user() {
        return DEFAULT_USER;
    }

    @AttributeDefinition(name = L10N_HOST_NAME, description = L10N_HOST_DESCRIPTION, required = true, defaultValue = DEFAULT_HOST)
    default String host() {
        return DEFAULT_HOST;
    }

    @AttributeDefinition(name = L10N_PORT_NAME, description = L10N_PORT_DESCRIPTION, required = true, defaultValue = DEFAULT_PORT
            + "")
    default int port() {
        return DEFAULT_PORT;
    }

    @AttributeDefinition(name = L10N_DATABASENAME_NAME, description = L10N_DATABASENAME_DESCRIPTION, defaultValue = DEFAULT_DATABASENAME)
    default String databaseName() {
        return DEFAULT_DATABASENAME;
    }

    @AttributeDefinition(name = L10N_CONNECT_TIMEOUT_NAME, description = L10N_CONNECT_TIMEOUT_DESCRIPTION, defaultValue = DEFAULT_CONNECT_TIMEOUT
            + "")
    default int connectTimeout() {
        return DEFAULT_CONNECT_TIMEOUT;
    }

    @AttributeDefinition(name = L10N_USE_SERVER_PREPARED_STMTS_NAME, description = L10N_USE_SERVER_PREPARED_STMTS_DESCRIPTION, defaultValue = DEFAULT_USE_SERVER_PREPARED_STMTS
            + "")
    default boolean useServerPrepStmts() {
        return DEFAULT_USE_SERVER_PREPARED_STMTS;
    }

    @AttributeDefinition(name = L10N_ALLOW_LOCAL_INFILE_NAME, description = L10N_ALLOW_LOCAL_INFILE_DESCRIPTION, defaultValue = DEFAULT_ALLOW_LOCAL_INFILE
            + "")
    default boolean allowLocalInfile() {
        return DEFAULT_ALLOW_LOCAL_INFILE;
    }

    @AttributeDefinition(name = L10N_SSL_MODE_NAME, description = L10N_SSL_MODE_DESCRIPTION, defaultValue = DEFAULT_SSL_MODE, options = {
            @Option(label = L10N_SSL_MODE_OPTION_DISABLE_LABEL, value = "disable"),
            @Option(label = L10N_SSL_MODE_OPTION_TRUST_LABEL, value = "trust"),
            @Option(label = L10N_SSL_MODE_OPTION_VERIFY_CA_LABEL, value = "verify-ca"),
            @Option(label = L10N_SSL_MODE_OPTION_VERIFY_FULL_LABEL, value = "verify-full") })
    default String sslMode() {
        return DEFAULT_SSL_MODE;
    }

    @AttributeDefinition(name = L10N_USE_COMPRESSION_NAME, description = L10N_USE_COMPRESSION_DESCRIPTION, defaultValue = DEFAULT_USE_COMPRESSION
            + "")
    default boolean useCompression() {
        return DEFAULT_USE_COMPRESSION;
    }

    @AttributeDefinition(name = L10N_SOCKET_TIMEOUT_NAME, description = L10N_SOCKET_TIMEOUT_DESCRIPTION, defaultValue = DEFAULT_SOCKET_TIMEOUT
            + "")
    default int socketTimeout() {
        return DEFAULT_SOCKET_TIMEOUT;
    }

    @AttributeDefinition(name = L10N_ALLOW_MULTI_QUERIES_NAME, description = L10N_ALLOW_MULTI_QUERIES_DESCRIPTION, defaultValue = DEFAULT_ALLOW_MULTI_QUERIES
            + "")
    default boolean allowMultiQueries() {
        return DEFAULT_ALLOW_MULTI_QUERIES;
    }

    @AttributeDefinition(name = L10N_SESSION_VARIABLES_NAME, description = L10N_SESSION_VARIABLES_DESCRIPTION, defaultValue = DEFAULT_SESSION_VARIABLES)
    default String sessionVariables() {
        return DEFAULT_SESSION_VARIABLES;
    }
}
