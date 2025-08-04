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
package org.eclipse.daanse.jdbc.datasource.metatype.mysql.impl;

import org.eclipse.daanse.jdbc.datasource.metatype.mysql.api.Constants;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name = MySqlConfig.L10N_OCD_NAME, description = MySqlConfig.L10N_POSTFIX_DESCRIPTION)
@interface MySqlConfig {

    public static final String L10N_PREFIX = "%";

    public static final String L10N_POSTFIX_DESCRIPTION = ".description";
    public static final String L10N_POSTFIX_NAME = ".name";

    public static final String L10N_OCD_NAME = L10N_PREFIX + "ocd" + L10N_POSTFIX_NAME;
    public static final String L10N_OCD_DESCRIPTION = L10N_PREFIX + "ocd" + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_HOST_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_HOST + L10N_POSTFIX_NAME;
    public static final String L10N_HOST_DESCRIPTION = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_HOST
            + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_PORT_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_PORT + L10N_POSTFIX_NAME;
    public static final String L10N_PORT_DESCRIPTION = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_PORT
            + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_DATABASENAME_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_DATABASENAME
            + L10N_POSTFIX_NAME;
    public static final String L10N_DATABASENAME_DESCRIPTION = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_DATABASENAME
            + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_PASSWORD_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_PASSWORD
            + L10N_POSTFIX_NAME;
    public static final String L10N_PASSWORD_DESCRIPTION = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_PASSWORD
            + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_USER_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_USER + L10N_POSTFIX_NAME;
    public static final String L10N_USER_DESCRIPTION = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_USER
            + L10N_POSTFIX_DESCRIPTION;

    // Additional L10N constants for commonly used MySQL properties
    public static final String L10N_AUTO_RECONNECT_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_AUTO_RECONNECT
            + L10N_POSTFIX_NAME;
    public static final String L10N_AUTO_RECONNECT_DESCRIPTION = L10N_PREFIX
            + Constants.DATASOURCE_PROPERTY_AUTO_RECONNECT + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_CONNECTION_TIMEOUT_NAME = L10N_PREFIX
            + Constants.DATASOURCE_PROPERTY_CONNECTION_TIMEOUT + L10N_POSTFIX_NAME;
    public static final String L10N_CONNECTION_TIMEOUT_DESCRIPTION = L10N_PREFIX
            + Constants.DATASOURCE_PROPERTY_CONNECTION_TIMEOUT + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_SOCKET_TIMEOUT_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_SOCKET_TIMEOUT
            + L10N_POSTFIX_NAME;
    public static final String L10N_SOCKET_TIMEOUT_DESCRIPTION = L10N_PREFIX
            + Constants.DATASOURCE_PROPERTY_SOCKET_TIMEOUT + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_USE_SSL_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_USE_SSL
            + L10N_POSTFIX_NAME;
    public static final String L10N_USE_SSL_DESCRIPTION = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_USE_SSL
            + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_CHARACTER_ENCODING_NAME = L10N_PREFIX
            + Constants.DATASOURCE_PROPERTY_CHARACTER_ENCODING + L10N_POSTFIX_NAME;
    public static final String L10N_CHARACTER_ENCODING_DESCRIPTION = L10N_PREFIX
            + Constants.DATASOURCE_PROPERTY_CHARACTER_ENCODING + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_USE_SERVER_PREPARED_STMTS_NAME = L10N_PREFIX
            + Constants.DATASOURCE_PROPERTY_USE_SERVER_PREPARED_STMTS + L10N_POSTFIX_NAME;
    public static final String L10N_USE_SERVER_PREPARED_STMTS_DESCRIPTION = L10N_PREFIX
            + Constants.DATASOURCE_PROPERTY_USE_SERVER_PREPARED_STMTS + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_CACHE_PREPARED_STMTS_NAME = L10N_PREFIX
            + Constants.DATASOURCE_PROPERTY_CACHE_PREPARED_STMTS + L10N_POSTFIX_NAME;
    public static final String L10N_CACHE_PREPARED_STMTS_DESCRIPTION = L10N_PREFIX
            + Constants.DATASOURCE_PROPERTY_CACHE_PREPARED_STMTS + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_PREPARED_STATEMENT_CACHE_SIZE_NAME = L10N_PREFIX
            + Constants.DATASOURCE_PROPERTY_PREPARED_STATEMENT_CACHE_SIZE + L10N_POSTFIX_NAME;
    public static final String L10N_PREPARED_STATEMENT_CACHE_SIZE_DESCRIPTION = L10N_PREFIX
            + Constants.DATASOURCE_PROPERTY_PREPARED_STATEMENT_CACHE_SIZE + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_USE_COMPRESSION_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_USE_COMPRESSION
            + L10N_POSTFIX_NAME;
    public static final String L10N_USE_COMPRESSION_DESCRIPTION = L10N_PREFIX
            + Constants.DATASOURCE_PROPERTY_USE_COMPRESSION + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_ZERO_DATETIME_BEHAVIOR_NAME = L10N_PREFIX
            + Constants.DATASOURCE_PROPERTY_ZERO_DATETIME_BEHAVIOR + L10N_POSTFIX_NAME;
    public static final String L10N_ZERO_DATETIME_BEHAVIOR_DESCRIPTION = L10N_PREFIX
            + Constants.DATASOURCE_PROPERTY_ZERO_DATETIME_BEHAVIOR + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_SERVER_TIMEZONE_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_SERVER_TIMEZONE
            + L10N_POSTFIX_NAME;
    public static final String L10N_SERVER_TIMEZONE_DESCRIPTION = L10N_PREFIX
            + Constants.DATASOURCE_PROPERTY_SERVER_TIMEZONE + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_USE_AFFECTED_ROWS_NAME = L10N_PREFIX
            + Constants.DATASOURCE_PROPERTY_USE_AFFECTED_ROWS + L10N_POSTFIX_NAME;
    public static final String L10N_USE_AFFECTED_ROWS_DESCRIPTION = L10N_PREFIX
            + Constants.DATASOURCE_PROPERTY_USE_AFFECTED_ROWS + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_ALLOW_MULTI_QUERIES_NAME = L10N_PREFIX
            + Constants.DATASOURCE_PROPERTY_ALLOW_MULTI_QUERIES + L10N_POSTFIX_NAME;
    public static final String L10N_ALLOW_MULTI_QUERIES_DESCRIPTION = L10N_PREFIX
            + Constants.DATASOURCE_PROPERTY_ALLOW_MULTI_QUERIES + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_REWRITE_BATCHED_STATEMENTS_NAME = L10N_PREFIX
            + Constants.DATASOURCE_PROPERTY_REWRITE_BATCHED_STATEMENTS + L10N_POSTFIX_NAME;
    public static final String L10N_REWRITE_BATCHED_STATEMENTS_DESCRIPTION = L10N_PREFIX
            + Constants.DATASOURCE_PROPERTY_REWRITE_BATCHED_STATEMENTS + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_USE_INFORMATION_SCHEMA_NAME = L10N_PREFIX
            + Constants.DATASOURCE_PROPERTY_USE_INFORMATION_SCHEMA + L10N_POSTFIX_NAME;
    public static final String L10N_USE_INFORMATION_SCHEMA_DESCRIPTION = L10N_PREFIX
            + Constants.DATASOURCE_PROPERTY_USE_INFORMATION_SCHEMA + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_LOGGER_CLASS_NAME_NAME = L10N_PREFIX
            + Constants.DATASOURCE_PROPERTY_LOGGER_CLASS_NAME + L10N_POSTFIX_NAME;
    public static final String L10N_LOGGER_CLASS_NAME_DESCRIPTION = L10N_PREFIX
            + Constants.DATASOURCE_PROPERTY_LOGGER_CLASS_NAME + L10N_POSTFIX_DESCRIPTION;

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

    @AttributeDefinition(name = L10N_DATABASENAME_NAME, description = L10N_DATABASENAME_DESCRIPTION)
    String databaseName() default "";

    @AttributeDefinition(name = L10N_PORT_NAME, description = L10N_PORT_DESCRIPTION, required = true)
    int port() default 3306;

    @AttributeDefinition(name = L10N_HOST_NAME, description = L10N_HOST_DESCRIPTION, required = true)
    String host() default "localhost";

    // Additional MySQL properties
    @AttributeDefinition(name = L10N_AUTO_RECONNECT_NAME, description = L10N_AUTO_RECONNECT_DESCRIPTION)
    boolean autoReconnect() default false;

    @AttributeDefinition(name = L10N_CONNECTION_TIMEOUT_NAME, description = L10N_CONNECTION_TIMEOUT_DESCRIPTION)
    int connectTimeout() default 0;

    @AttributeDefinition(name = L10N_SOCKET_TIMEOUT_NAME, description = L10N_SOCKET_TIMEOUT_DESCRIPTION)
    int socketTimeout() default 0;

    @AttributeDefinition(name = L10N_USE_SSL_NAME, description = L10N_USE_SSL_DESCRIPTION)
    boolean useSSL() default true;

    @AttributeDefinition(name = L10N_CHARACTER_ENCODING_NAME, description = L10N_CHARACTER_ENCODING_DESCRIPTION)
    String characterEncoding() default "UTF-8";

    @AttributeDefinition(name = L10N_USE_SERVER_PREPARED_STMTS_NAME, description = L10N_USE_SERVER_PREPARED_STMTS_DESCRIPTION)
    boolean useServerPrepStmts() default false;

    @AttributeDefinition(name = L10N_CACHE_PREPARED_STMTS_NAME, description = L10N_CACHE_PREPARED_STMTS_DESCRIPTION)
    boolean cachePrepStmts() default false;

    @AttributeDefinition(name = L10N_PREPARED_STATEMENT_CACHE_SIZE_NAME, description = L10N_PREPARED_STATEMENT_CACHE_SIZE_DESCRIPTION)
    int prepStmtCacheSize() default 25;

    @AttributeDefinition(name = L10N_USE_COMPRESSION_NAME, description = L10N_USE_COMPRESSION_DESCRIPTION)
    boolean useCompression() default false;

    @AttributeDefinition(name = L10N_ZERO_DATETIME_BEHAVIOR_NAME, description = L10N_ZERO_DATETIME_BEHAVIOR_DESCRIPTION)
    String zeroDateTimeBehavior() default "exception";

    @AttributeDefinition(name = L10N_SERVER_TIMEZONE_NAME, description = L10N_SERVER_TIMEZONE_DESCRIPTION)
    String serverTimezone() default "";

    @AttributeDefinition(name = L10N_USE_AFFECTED_ROWS_NAME, description = L10N_USE_AFFECTED_ROWS_DESCRIPTION)
    boolean useAffectedRows() default false;

    @AttributeDefinition(name = L10N_ALLOW_MULTI_QUERIES_NAME, description = L10N_ALLOW_MULTI_QUERIES_DESCRIPTION)
    boolean allowMultiQueries() default false;

    @AttributeDefinition(name = L10N_REWRITE_BATCHED_STATEMENTS_NAME, description = L10N_REWRITE_BATCHED_STATEMENTS_DESCRIPTION)
    boolean rewriteBatchedStatements() default false;

    @AttributeDefinition(name = L10N_USE_INFORMATION_SCHEMA_NAME, description = L10N_USE_INFORMATION_SCHEMA_DESCRIPTION)
    boolean useInformationSchema() default true;

    @AttributeDefinition(name = L10N_LOGGER_CLASS_NAME_NAME, description = L10N_LOGGER_CLASS_NAME_DESCRIPTION)
    String logger() default "";

}
