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
package org.eclipse.daanse.jdbc.datasource.metatype.postgresql.impl;

import org.eclipse.daanse.jdbc.datasource.metatype.postgresql.api.Constants;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name = PostgresConfig.L10N_OCD_NAME, description = PostgresConfig.L10N_POSTFIX_DESCRIPTION)
@interface PostgresConfig {

    public static final String L10N_PREFIX = "%";

    public static final String L10N_POSTFIX_DESCRIPTION = ".description";
    public static final String L10N_POSTFIX_NAME = ".name";

    public static final String L10N_OCD_NAME = L10N_PREFIX + "ocd" + L10N_POSTFIX_NAME;
    public static final String L10N_OCD_DESCRIPTION = L10N_PREFIX + "ocd" + L10N_POSTFIX_DESCRIPTION;

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
    @AttributeDefinition(name = L10N_PASSWORD_NAME, description = L10N_PASSWORD_DESCRIPTION, type = AttributeType.PASSWORD)
    String _password() default "";

    /**
     * {@link Constants#DATASOURCE_PROPERTY_USERNAME}.
     *
     */
    @AttributeDefinition(name = L10N_USER_NAME, description = L10N_USER_DESCRIPTION)
    String user() default "";

    @AttributeDefinition(name = L10N_DBNAME_NAME, description = L10N_DBANE_DESCRIPTION)
    String dbname() default "";

    @AttributeDefinition(name = L10N_PORTS_NAME, description = L10N_PORTS_DESCRIPTION, required = true)
    int[] ports() default { 5432 };

    @AttributeDefinition(name = L10N_HOST_NAME, description = L10N_HOST_DESCRIPTION, required = true)
    String host() default "localhost";

    // Additional PostgreSQL properties
    @AttributeDefinition(name = L10N_APPLICATION_NAME_NAME, description = L10N_APPLICATION_NAME_DESCRIPTION)
    String applicationName() default "";

    @AttributeDefinition(name = L10N_CONNECT_TIMEOUT_NAME, description = L10N_CONNECT_TIMEOUT_DESCRIPTION)
    int connectTimeout() default 0;

    @AttributeDefinition(name = L10N_CURRENT_SCHEMA_NAME, description = L10N_CURRENT_SCHEMA_DESCRIPTION)
    String currentSchema() default "";

    @AttributeDefinition(name = L10N_LOGIN_TIMEOUT_NAME, description = L10N_LOGIN_TIMEOUT_DESCRIPTION)
    int loginTimeout() default 0;

    @AttributeDefinition(name = L10N_SOCKET_TIMEOUT_NAME, description = L10N_SOCKET_TIMEOUT_DESCRIPTION)
    int socketTimeout() default 0;

    @AttributeDefinition(name = L10N_SSL_NAME, description = L10N_SSL_DESCRIPTION)
    boolean ssl() default false;

    @AttributeDefinition(name = L10N_SSL_MODE_NAME, description = L10N_SSL_MODE_DESCRIPTION)
    String sslMode() default "";

    @AttributeDefinition(name = L10N_SSL_CERT_NAME, description = L10N_SSL_CERT_DESCRIPTION)
    String sslCert() default "";

    @AttributeDefinition(name = L10N_SSL_KEY_NAME, description = L10N_SSL_KEY_DESCRIPTION)
    String sslKey() default "";

    @AttributeDefinition(name = L10N_SSL_ROOT_CERT_NAME, description = L10N_SSL_ROOT_CERT_DESCRIPTION)
    String sslRootCert() default "";

    @AttributeDefinition(name = L10N_DEFAULT_ROW_FETCH_SIZE_NAME, description = L10N_DEFAULT_ROW_FETCH_SIZE_DESCRIPTION)
    int defaultRowFetchSize() default 0;

    @AttributeDefinition(name = L10N_PREPARE_THRESHOLD_NAME, description = L10N_PREPARE_THRESHOLD_DESCRIPTION)
    int prepareThreshold() default 5;

    @AttributeDefinition(name = L10N_PREPARED_STATEMENT_CACHE_QUERIES_NAME, description = L10N_PREPARED_STATEMENT_CACHE_QUERIES_DESCRIPTION)
    int preparedStatementCacheQueries() default 256;

    @AttributeDefinition(name = L10N_LOAD_BALANCE_HOSTS_NAME, description = L10N_LOAD_BALANCE_HOSTS_DESCRIPTION)
    boolean loadBalanceHosts() default false;

    @AttributeDefinition(name = L10N_TARGET_SERVER_TYPE_NAME, description = L10N_TARGET_SERVER_TYPE_DESCRIPTION)
    String targetServerType() default "";

    @AttributeDefinition(name = L10N_TCP_KEEP_ALIVE_NAME, description = L10N_TCP_KEEP_ALIVE_DESCRIPTION)
    boolean tcpKeepAlive() default false;

    @AttributeDefinition(name = L10N_READ_ONLY_NAME, description = L10N_READ_ONLY_DESCRIPTION)
    boolean readOnly() default false;

}
