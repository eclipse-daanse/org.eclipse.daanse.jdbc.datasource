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
package org.eclipse.daanse.jdbc.datasource.postgresql.api.ocd;

import org.eclipse.daanse.jdbc.datasource.postgresql.api.Constants;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.Option;

public interface BaseConfig {

    public static final String OCD_LOCALIZATION = "OSGI-INF/l10n/org.eclipse.daanse.jdbc.datasource.postgresql.ocd";
    public static final String L10N_PREFIX = "%";

    public static final String L10N_POSTFIX_DESCRIPTION = ".description";
    public static final String L10N_POSTFIX_NAME = ".name";
    public static final String L10N_POSTFIX_OPTION = ".option";
    public static final String L10N_POSTFIX_LABEL = ".label";

    public static final String L10N_HOST_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_PG_HOST + L10N_POSTFIX_NAME;
    public static final String L10N_HOST_DESCRIPTION = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_PG_HOST
            + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_PORTS_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_PG_PORT
            + L10N_POSTFIX_NAME;
    public static final String L10N_PORTS_DESCRIPTION = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_PG_PORT
            + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_DBNAME_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_PG_DBNAME
            + L10N_POSTFIX_NAME;
    public static final String L10N_DBANE_DESCRIPTION = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_PG_DBNAME
            + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_PASSWORD_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_PASSWORD
            + L10N_POSTFIX_NAME;
    public static final String L10N_PASSWORD_DESCRIPTION = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_PASSWORD
            + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_USER_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_USER + L10N_POSTFIX_NAME;
    public static final String L10N_USER_DESCRIPTION = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_USER
            + L10N_POSTFIX_DESCRIPTION;

    // Additional L10N constants for new properties
    public static final String L10N_APPLICATION_NAME_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_APPLICATION_NAME
            + L10N_POSTFIX_NAME;
    public static final String L10N_APPLICATION_NAME_DESCRIPTION = L10N_PREFIX
            + Constants.DATASOURCE_PROPERTY_APPLICATION_NAME + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_CONNECT_TIMEOUT_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_CONNECT_TIMEOUT
            + L10N_POSTFIX_NAME;
    public static final String L10N_CONNECT_TIMEOUT_DESCRIPTION = L10N_PREFIX
            + Constants.DATASOURCE_PROPERTY_CONNECT_TIMEOUT + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_CURRENT_SCHEMA_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_CURRENT_SCHEMA
            + L10N_POSTFIX_NAME;
    public static final String L10N_CURRENT_SCHEMA_DESCRIPTION = L10N_PREFIX
            + Constants.DATASOURCE_PROPERTY_CURRENT_SCHEMA + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_LOGIN_TIMEOUT_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_LOGIN_TIMEOUT
            + L10N_POSTFIX_NAME;
    public static final String L10N_LOGIN_TIMEOUT_DESCRIPTION = L10N_PREFIX
            + Constants.DATASOURCE_PROPERTY_LOGIN_TIMEOUT + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_SOCKET_TIMEOUT_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_SOCKET_TIMEOUT
            + L10N_POSTFIX_NAME;
    public static final String L10N_SOCKET_TIMEOUT_DESCRIPTION = L10N_PREFIX
            + Constants.DATASOURCE_PROPERTY_SOCKET_TIMEOUT + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_SSL_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_SSL + L10N_POSTFIX_NAME;
    public static final String L10N_SSL_DESCRIPTION = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_SSL
            + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_SSL_MODE_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_SSL_MODE
            + L10N_POSTFIX_NAME;
    public static final String L10N_SSL_MODE_DESCRIPTION = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_SSL_MODE
            + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_SSL_CERT_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_SSL_CERT
            + L10N_POSTFIX_NAME;
    public static final String L10N_SSL_CERT_DESCRIPTION = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_SSL_CERT
            + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_SSL_KEY_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_SSL_KEY
            + L10N_POSTFIX_NAME;
    public static final String L10N_SSL_KEY_DESCRIPTION = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_SSL_KEY
            + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_SSL_ROOT_CERT_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_SSL_ROOT_CERT
            + L10N_POSTFIX_NAME;
    public static final String L10N_SSL_ROOT_CERT_DESCRIPTION = L10N_PREFIX
            + Constants.DATASOURCE_PROPERTY_SSL_ROOT_CERT + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_DEFAULT_ROW_FETCH_SIZE_NAME = L10N_PREFIX
            + Constants.DATASOURCE_PROPERTY_DEFAULT_ROW_FETCH_SIZE + L10N_POSTFIX_NAME;
    public static final String L10N_DEFAULT_ROW_FETCH_SIZE_DESCRIPTION = L10N_PREFIX
            + Constants.DATASOURCE_PROPERTY_DEFAULT_ROW_FETCH_SIZE + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_PREPARE_THRESHOLD_NAME = L10N_PREFIX
            + Constants.DATASOURCE_PROPERTY_PREPARE_THRESHOLD + L10N_POSTFIX_NAME;
    public static final String L10N_PREPARE_THRESHOLD_DESCRIPTION = L10N_PREFIX
            + Constants.DATASOURCE_PROPERTY_PREPARE_THRESHOLD + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_PREPARED_STATEMENT_CACHE_QUERIES_NAME = L10N_PREFIX
            + Constants.DATASOURCE_PROPERTY_PREPARED_STATEMENT_CACHE_QUERIES + L10N_POSTFIX_NAME;
    public static final String L10N_PREPARED_STATEMENT_CACHE_QUERIES_DESCRIPTION = L10N_PREFIX
            + Constants.DATASOURCE_PROPERTY_PREPARED_STATEMENT_CACHE_QUERIES + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_LOAD_BALANCE_HOSTS_NAME = L10N_PREFIX
            + Constants.DATASOURCE_PROPERTY_LOAD_BALANCE_HOSTS + L10N_POSTFIX_NAME;
    public static final String L10N_LOAD_BALANCE_HOSTS_DESCRIPTION = L10N_PREFIX
            + Constants.DATASOURCE_PROPERTY_LOAD_BALANCE_HOSTS + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_TARGET_SERVER_TYPE_NAME = L10N_PREFIX
            + Constants.DATASOURCE_PROPERTY_TARGET_SERVER_TYPE + L10N_POSTFIX_NAME;
    public static final String L10N_TARGET_SERVER_TYPE_DESCRIPTION = L10N_PREFIX
            + Constants.DATASOURCE_PROPERTY_TARGET_SERVER_TYPE + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_TCP_KEEP_ALIVE_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_TCP_KEEP_ALIVE
            + L10N_POSTFIX_NAME;
    public static final String L10N_TCP_KEEP_ALIVE_DESCRIPTION = L10N_PREFIX
            + Constants.DATASOURCE_PROPERTY_TCP_KEEP_ALIVE + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_READ_ONLY_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_READ_ONLY
            + L10N_POSTFIX_NAME;
    public static final String L10N_READ_ONLY_DESCRIPTION = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_READ_ONLY
            + L10N_POSTFIX_DESCRIPTION;

    // Option label constants for SSL Mode
    public static final String L10N_SSL_MODE_OPTION_DISABLE_LABEL = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_SSL_MODE + L10N_POSTFIX_OPTION + ".disable" + L10N_POSTFIX_LABEL;
    public static final String L10N_SSL_MODE_OPTION_ALLOW_LABEL = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_SSL_MODE + L10N_POSTFIX_OPTION + ".allow" + L10N_POSTFIX_LABEL;
    public static final String L10N_SSL_MODE_OPTION_PREFER_LABEL = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_SSL_MODE + L10N_POSTFIX_OPTION + ".prefer" + L10N_POSTFIX_LABEL;
    public static final String L10N_SSL_MODE_OPTION_REQUIRE_LABEL = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_SSL_MODE + L10N_POSTFIX_OPTION + ".require" + L10N_POSTFIX_LABEL;
    public static final String L10N_SSL_MODE_OPTION_VERIFY_CA_LABEL = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_SSL_MODE + L10N_POSTFIX_OPTION + ".verify-ca" + L10N_POSTFIX_LABEL;
    public static final String L10N_SSL_MODE_OPTION_VERIFY_FULL_LABEL = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_SSL_MODE + L10N_POSTFIX_OPTION + ".verify-full" + L10N_POSTFIX_LABEL;

    // Option label constants for Target Server Type
    public static final String L10N_TARGET_SERVER_TYPE_OPTION_ANY_LABEL = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_TARGET_SERVER_TYPE + L10N_POSTFIX_OPTION + ".any" + L10N_POSTFIX_LABEL;
    public static final String L10N_TARGET_SERVER_TYPE_OPTION_MASTER_LABEL = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_TARGET_SERVER_TYPE + L10N_POSTFIX_OPTION + ".master" + L10N_POSTFIX_LABEL;
    public static final String L10N_TARGET_SERVER_TYPE_OPTION_SLAVE_LABEL = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_TARGET_SERVER_TYPE + L10N_POSTFIX_OPTION + ".slave" + L10N_POSTFIX_LABEL;
    public static final String L10N_TARGET_SERVER_TYPE_OPTION_SECONDARY_LABEL = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_TARGET_SERVER_TYPE + L10N_POSTFIX_OPTION + ".secondary" + L10N_POSTFIX_LABEL;
    public static final String L10N_TARGET_SERVER_TYPE_OPTION_PREFERSLAVE_LABEL = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_TARGET_SERVER_TYPE + L10N_POSTFIX_OPTION + ".preferSlave" + L10N_POSTFIX_LABEL;
    public static final String L10N_TARGET_SERVER_TYPE_OPTION_PREFERSECONDARY_LABEL = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_TARGET_SERVER_TYPE + L10N_POSTFIX_OPTION + ".preferSecondary" + L10N_POSTFIX_LABEL;

    // Default value constants
    public static final String DEFAULT_PASSWORD = "";
    public static final String DEFAULT_USER = "";
    public static final String DEFAULT_DBNAME = "";
    public static final int[] DEFAULT_PORTS = { 5432 };
    public static final String DEFAULT_HOST = "localhost";
    public static final String DEFAULT_APPLICATION_NAME = "";
    public static final int DEFAULT_CONNECT_TIMEOUT = 0;
    public static final String DEFAULT_CURRENT_SCHEMA = "";
    public static final int DEFAULT_LOGIN_TIMEOUT = 0;
    public static final int DEFAULT_SOCKET_TIMEOUT = 0;
    public static final boolean DEFAULT_SSL = false;
    public static final String DEFAULT_SSL_MODE = "";
    public static final String DEFAULT_SSL_CERT = "";
    public static final String DEFAULT_SSL_KEY = "";
    public static final String DEFAULT_SSL_ROOT_CERT = "";
    public static final int DEFAULT_DEFAULT_ROW_FETCH_SIZE = 0;
    public static final int DEFAULT_PREPARE_THRESHOLD = 5;
    public static final int DEFAULT_PREPARED_STATEMENT_CACHE_QUERIES = 256;
    public static final boolean DEFAULT_LOAD_BALANCE_HOSTS = false;
    public static final String DEFAULT_TARGET_SERVER_TYPE = "";
    public static final boolean DEFAULT_TCP_KEEP_ALIVE = false;
    public static final boolean DEFAULT_READ_ONLY = false;

    // https://docs.oracle.com/cd/E13222_01/wls/docs81/jdbc_drivers/oracle.html
    /**
     * {@link Constants#DATASOURCE_PROPERTY_PASSWORD}. OSGi Service Component Spec.:
     *
     * Component properties whose names start with full stop are available to the component instance but
     * are not available as service properties of the registered service.
     *
     * A single low line ('_' \u005F) is converted into a full stop ('.' \u002E)
     *
     * @return password
     */
    @AttributeDefinition(name = L10N_PASSWORD_NAME, description = L10N_PASSWORD_DESCRIPTION, type = AttributeType.PASSWORD, defaultValue = DEFAULT_PASSWORD)
    default String _password() {
        return DEFAULT_PASSWORD;
    }

    /**
     * {@link Constants#DATASOURCE_PROPERTY_USER}.
     *
     */
    @AttributeDefinition(name = L10N_USER_NAME, description = L10N_USER_DESCRIPTION, defaultValue = DEFAULT_USER)
    default String user() {
        return DEFAULT_USER;
    }

    @AttributeDefinition(name = L10N_DBNAME_NAME, description = L10N_DBANE_DESCRIPTION, defaultValue = DEFAULT_DBNAME)
    default String dbname() {
        return DEFAULT_DBNAME;
    }

    @AttributeDefinition(name = L10N_PORTS_NAME, description = L10N_PORTS_DESCRIPTION, required = true, defaultValue = "5432")
    default int[] ports() {
        return DEFAULT_PORTS;
    }

    @AttributeDefinition(name = L10N_HOST_NAME, description = L10N_HOST_DESCRIPTION, required = true, defaultValue = DEFAULT_HOST)
    default String host() {
        return DEFAULT_HOST;
    }

    // Additional PostgreSQL properties
    @AttributeDefinition(name = L10N_APPLICATION_NAME_NAME, description = L10N_APPLICATION_NAME_DESCRIPTION, defaultValue = DEFAULT_APPLICATION_NAME)
    default String applicationName() {
        return DEFAULT_APPLICATION_NAME;
    }

    @AttributeDefinition(name = L10N_CONNECT_TIMEOUT_NAME, description = L10N_CONNECT_TIMEOUT_DESCRIPTION, defaultValue = DEFAULT_CONNECT_TIMEOUT
            + "")
    default int connectTimeout() {
        return DEFAULT_CONNECT_TIMEOUT;
    }

    @AttributeDefinition(name = L10N_CURRENT_SCHEMA_NAME, description = L10N_CURRENT_SCHEMA_DESCRIPTION, defaultValue = DEFAULT_CURRENT_SCHEMA)
    default String currentSchema() {
        return DEFAULT_CURRENT_SCHEMA;
    }

    @AttributeDefinition(name = L10N_LOGIN_TIMEOUT_NAME, description = L10N_LOGIN_TIMEOUT_DESCRIPTION, defaultValue = DEFAULT_LOGIN_TIMEOUT
            + "")
    default int loginTimeout() {
        return DEFAULT_LOGIN_TIMEOUT;
    }

    @AttributeDefinition(name = L10N_SOCKET_TIMEOUT_NAME, description = L10N_SOCKET_TIMEOUT_DESCRIPTION, defaultValue = DEFAULT_SOCKET_TIMEOUT
            + "")
    default int socketTimeout() {
        return DEFAULT_SOCKET_TIMEOUT;
    }

    @AttributeDefinition(name = L10N_SSL_NAME, description = L10N_SSL_DESCRIPTION, defaultValue = DEFAULT_SSL + "")
    default boolean ssl() {
        return DEFAULT_SSL;
    }

    @AttributeDefinition(name = L10N_SSL_MODE_NAME, description = L10N_SSL_MODE_DESCRIPTION, defaultValue = DEFAULT_SSL_MODE, options = {
            @Option(label = L10N_SSL_MODE_OPTION_DISABLE_LABEL, value = "disable"),
            @Option(label = L10N_SSL_MODE_OPTION_ALLOW_LABEL, value = "allow"),
            @Option(label = L10N_SSL_MODE_OPTION_PREFER_LABEL, value = "prefer"),
            @Option(label = L10N_SSL_MODE_OPTION_REQUIRE_LABEL, value = "require"),
            @Option(label = L10N_SSL_MODE_OPTION_VERIFY_CA_LABEL, value = "verify-ca"),
            @Option(label = L10N_SSL_MODE_OPTION_VERIFY_FULL_LABEL, value = "verify-full")
    })
    default String sslMode() {
        return DEFAULT_SSL_MODE;
    }

    @AttributeDefinition(name = L10N_SSL_CERT_NAME, description = L10N_SSL_CERT_DESCRIPTION, defaultValue = DEFAULT_SSL_CERT)
    default String sslCert() {
        return DEFAULT_SSL_CERT;
    }

    @AttributeDefinition(name = L10N_SSL_KEY_NAME, description = L10N_SSL_KEY_DESCRIPTION, defaultValue = DEFAULT_SSL_KEY)
    default String sslKey() {
        return DEFAULT_SSL_KEY;
    }

    @AttributeDefinition(name = L10N_SSL_ROOT_CERT_NAME, description = L10N_SSL_ROOT_CERT_DESCRIPTION, defaultValue = DEFAULT_SSL_ROOT_CERT)
    default String sslRootCert() {
        return DEFAULT_SSL_ROOT_CERT;
    }

    @AttributeDefinition(name = L10N_DEFAULT_ROW_FETCH_SIZE_NAME, description = L10N_DEFAULT_ROW_FETCH_SIZE_DESCRIPTION, defaultValue = DEFAULT_DEFAULT_ROW_FETCH_SIZE
            + "")
    default int defaultRowFetchSize() {
        return DEFAULT_DEFAULT_ROW_FETCH_SIZE;
    }

    @AttributeDefinition(name = L10N_PREPARE_THRESHOLD_NAME, description = L10N_PREPARE_THRESHOLD_DESCRIPTION, defaultValue = DEFAULT_PREPARE_THRESHOLD
            + "")
    default int prepareThreshold() {
        return DEFAULT_PREPARE_THRESHOLD;
    }

    @AttributeDefinition(name = L10N_PREPARED_STATEMENT_CACHE_QUERIES_NAME, description = L10N_PREPARED_STATEMENT_CACHE_QUERIES_DESCRIPTION, defaultValue = DEFAULT_PREPARED_STATEMENT_CACHE_QUERIES
            + "")
    default int preparedStatementCacheQueries() {
        return DEFAULT_PREPARED_STATEMENT_CACHE_QUERIES;
    }

    @AttributeDefinition(name = L10N_LOAD_BALANCE_HOSTS_NAME, description = L10N_LOAD_BALANCE_HOSTS_DESCRIPTION, defaultValue = DEFAULT_LOAD_BALANCE_HOSTS
            + "")
    default boolean loadBalanceHosts() {
        return DEFAULT_LOAD_BALANCE_HOSTS;
    }

    @AttributeDefinition(name = L10N_TARGET_SERVER_TYPE_NAME, description = L10N_TARGET_SERVER_TYPE_DESCRIPTION, defaultValue = DEFAULT_TARGET_SERVER_TYPE, options = {
            @Option(label = L10N_TARGET_SERVER_TYPE_OPTION_ANY_LABEL, value = "any"),
            @Option(label = L10N_TARGET_SERVER_TYPE_OPTION_MASTER_LABEL, value = "master"),
            @Option(label = L10N_TARGET_SERVER_TYPE_OPTION_SLAVE_LABEL, value = "slave"),
            @Option(label = L10N_TARGET_SERVER_TYPE_OPTION_SECONDARY_LABEL, value = "secondary"),
            @Option(label = L10N_TARGET_SERVER_TYPE_OPTION_PREFERSLAVE_LABEL, value = "preferSlave"),
            @Option(label = L10N_TARGET_SERVER_TYPE_OPTION_PREFERSECONDARY_LABEL, value = "preferSecondary")
    })
    default String targetServerType() {
        return DEFAULT_TARGET_SERVER_TYPE;
    }

    @AttributeDefinition(name = L10N_TCP_KEEP_ALIVE_NAME, description = L10N_TCP_KEEP_ALIVE_DESCRIPTION, defaultValue = DEFAULT_TCP_KEEP_ALIVE
            + "")
    default boolean tcpKeepAlive() {
        return DEFAULT_TCP_KEEP_ALIVE;
    }

    @AttributeDefinition(name = L10N_READ_ONLY_NAME, description = L10N_READ_ONLY_DESCRIPTION, defaultValue = DEFAULT_READ_ONLY
            + "")
    default boolean readOnly() {
        return DEFAULT_READ_ONLY;
    }

}
