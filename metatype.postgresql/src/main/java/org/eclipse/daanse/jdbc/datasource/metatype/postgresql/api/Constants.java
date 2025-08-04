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
     * Constant for the {@link org.osgi.framework.Constants#SERVICE_PID} of a {@link DataSource}
     */
    public static final String PID_DATASOURCE = "org.eclipse.daanse.jdbc.datasource.metatype.postgresql.DataSource";

    /**
     * Constant for the {@link org.osgi.framework.Constants#SERVICE_PID} of a
     * {@link ConnectionPoolDataSource} - Service.
     */
    public static final String PID_DATASOURCE_CP = "org.eclipse.daanse.jdbc.datasource.metatype.postgresql.ConnectionPoolDataSource";

    /**
     * Constant for the {@link org.osgi.framework.Constants#SERVICE_PID} of a {@link XADataSource} -
     * Service.
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

    // Additional PostgreSQL driver properties based on PGProperty enum

    /**
     * {@link PGProperty#ADAPTIVE_FETCH}
     */
    public static final String DATASOURCE_PROPERTY_ADAPTIVE_FETCH = "adaptive.fetch";

    /**
     * {@link PGProperty#ADAPTIVE_FETCH_MAXIMUM}
     */
    public static final String DATASOURCE_PROPERTY_ADAPTIVE_FETCH_MAXIMUM = "adaptive.fetch.maximum";

    /**
     * {@link PGProperty#ADAPTIVE_FETCH_MINIMUM}
     */
    public static final String DATASOURCE_PROPERTY_ADAPTIVE_FETCH_MINIMUM = "adaptive.fetch.minimum";

    /**
     * {@link PGProperty#ALLOW_ENCODING_CHANGES}
     */
    public static final String DATASOURCE_PROPERTY_ALLOW_ENCODING_CHANGES = "allow.encoding.changes";

    /**
     * {@link PGProperty#APPLICATION_NAME}
     */
    public static final String DATASOURCE_PROPERTY_APPLICATION_NAME = "application.name";

    /**
     * {@link PGProperty#ASSUME_MIN_SERVER_VERSION}
     */
    public static final String DATASOURCE_PROPERTY_ASSUME_MIN_SERVER_VERSION = "assume.min.server.version";

    /**
     * {@link PGProperty#AUTHENTICATION_PLUGIN_CLASS_NAME}
     */
    public static final String DATASOURCE_PROPERTY_AUTHENTICATION_PLUGIN_CLASS_NAME = "authentication.plugin.class.name";

    /**
     * {@link PGProperty#AUTOSAVE}
     */
    public static final String DATASOURCE_PROPERTY_AUTOSAVE = "autosave";

    /**
     * {@link PGProperty#BINARY_TRANSFER}
     */
    public static final String DATASOURCE_PROPERTY_BINARY_TRANSFER = "binary.transfer";

    /**
     * {@link PGProperty#BINARY_TRANSFER_DISABLE}
     */
    public static final String DATASOURCE_PROPERTY_BINARY_TRANSFER_DISABLE = "binary.transfer.disable";

    /**
     * {@link PGProperty#BINARY_TRANSFER_ENABLE}
     */
    public static final String DATASOURCE_PROPERTY_BINARY_TRANSFER_ENABLE = "binary.transfer.enable";

    /**
     * {@link PGProperty#CANCEL_SIGNAL_TIMEOUT}
     */
    public static final String DATASOURCE_PROPERTY_CANCEL_SIGNAL_TIMEOUT = "cancel.signal.timeout";

    /**
     * {@link PGProperty#CLEANUP_SAVEPOINTS}
     */
    public static final String DATASOURCE_PROPERTY_CLEANUP_SAVEPOINTS = "cleanup.savepoints";

    /**
     * {@link PGProperty#CONNECT_TIMEOUT}
     */
    public static final String DATASOURCE_PROPERTY_CONNECT_TIMEOUT = "connect.timeout";

    /**
     * {@link PGProperty#CURRENT_SCHEMA}
     */
    public static final String DATASOURCE_PROPERTY_CURRENT_SCHEMA = "current.schema";

    /**
     * {@link PGProperty#DATABASE_METADATA_CACHE_FIELDS}
     */
    public static final String DATASOURCE_PROPERTY_DATABASE_METADATA_CACHE_FIELDS = "database.metadata.cache.fields";

    /**
     * {@link PGProperty#DATABASE_METADATA_CACHE_FIELDS_MIB}
     */
    public static final String DATASOURCE_PROPERTY_DATABASE_METADATA_CACHE_FIELDS_MIB = "database.metadata.cache.fields.mib";

    /**
     * {@link PGProperty#DEFAULT_ROW_FETCH_SIZE}
     */
    public static final String DATASOURCE_PROPERTY_DEFAULT_ROW_FETCH_SIZE = "default.row.fetch.size";

    /**
     * {@link PGProperty#DISABLE_COLUMN_SANITISER}
     */
    public static final String DATASOURCE_PROPERTY_DISABLE_COLUMN_SANITISER = "disable.column.sanitiser";

    /**
     * {@link PGProperty#ESCAPE_SYNTAX_CALL_MODE}
     */
    public static final String DATASOURCE_PROPERTY_ESCAPE_SYNTAX_CALL_MODE = "escape.syntax.call.mode";

    /**
     * {@link PGProperty#GROUP_STARTUP_PARAMETERS}
     */
    public static final String DATASOURCE_PROPERTY_GROUP_STARTUP_PARAMETERS = "group.startup.parameters";

    /**
     * {@link PGProperty#GSS_ENC_MODE}
     */
    public static final String DATASOURCE_PROPERTY_GSS_ENC_MODE = "gss.enc.mode";

    /**
     * {@link PGProperty#GSS_LIB}
     */
    public static final String DATASOURCE_PROPERTY_GSS_LIB = "gss.lib";

    /**
     * {@link PGProperty#GSS_RESPONSE_TIMEOUT}
     */
    public static final String DATASOURCE_PROPERTY_GSS_RESPONSE_TIMEOUT = "gss.response.timeout";

    /**
     * {@link PGProperty#HIDE_UNPRIVILEGED_OBJECTS}
     */
    public static final String DATASOURCE_PROPERTY_HIDE_UNPRIVILEGED_OBJECTS = "hide.unprivileged.objects";

    /**
     * {@link PGProperty#HOST_RECHECK_SECONDS}
     */
    public static final String DATASOURCE_PROPERTY_HOST_RECHECK_SECONDS = "host.recheck.seconds";

    /**
     * {@link PGProperty#JAAS_APPLICATION_NAME}
     */
    public static final String DATASOURCE_PROPERTY_JAAS_APPLICATION_NAME = "jaas.application.name";

    /**
     * {@link PGProperty#JAAS_LOGIN}
     */
    public static final String DATASOURCE_PROPERTY_JAAS_LOGIN = "jaas.login";

    /**
     * {@link PGProperty#KERBEROS_SERVER_NAME}
     */
    public static final String DATASOURCE_PROPERTY_KERBEROS_SERVER_NAME = "kerberos.server.name";

    /**
     * {@link PGProperty#LOAD_BALANCE_HOSTS}
     */
    public static final String DATASOURCE_PROPERTY_LOAD_BALANCE_HOSTS = "load.balance.hosts";

    /**
     * {@link PGProperty#LOCAL_SOCKET_ADDRESS}
     */
    public static final String DATASOURCE_PROPERTY_LOCAL_SOCKET_ADDRESS = "local.socket.address";

    /**
     * {@link PGProperty#LOGGER_FILE}
     */
    public static final String DATASOURCE_PROPERTY_LOGGER_FILE = "logger.file";

    /**
     * {@link PGProperty#LOGGER_LEVEL}
     */
    public static final String DATASOURCE_PROPERTY_LOGGER_LEVEL = "logger.level";

    /**
     * {@link PGProperty#LOGIN_TIMEOUT}
     */
    public static final String DATASOURCE_PROPERTY_LOGIN_TIMEOUT = "login.timeout";

    /**
     * {@link PGProperty#LOG_SERVER_ERROR_DETAIL}
     */
    public static final String DATASOURCE_PROPERTY_LOG_SERVER_ERROR_DETAIL = "log.server.error.detail";

    /**
     * {@link PGProperty#LOG_UNCLOSED_CONNECTIONS}
     */
    public static final String DATASOURCE_PROPERTY_LOG_UNCLOSED_CONNECTIONS = "log.unclosed.connections";

    /**
     * {@link PGProperty#MAX_RESULT_BUFFER}
     */
    public static final String DATASOURCE_PROPERTY_MAX_RESULT_BUFFER = "max.result.buffer";

    /**
     * {@link PGProperty#OPTIONS}
     */
    public static final String DATASOURCE_PROPERTY_OPTIONS = "options";

    /**
     * {@link PGProperty#PREFER_QUERY_MODE}
     */
    public static final String DATASOURCE_PROPERTY_PREFER_QUERY_MODE = "prefer.query.mode";

    /**
     * {@link PGProperty#PREPARED_STATEMENT_CACHE_QUERIES}
     */
    public static final String DATASOURCE_PROPERTY_PREPARED_STATEMENT_CACHE_QUERIES = "prepared.statement.cache.queries";

    /**
     * {@link PGProperty#PREPARED_STATEMENT_CACHE_SIZE_MIB}
     */
    public static final String DATASOURCE_PROPERTY_PREPARED_STATEMENT_CACHE_SIZE_MIB = "prepared.statement.cache.size.mib";

    /**
     * {@link PGProperty#PREPARE_THRESHOLD}
     */
    public static final String DATASOURCE_PROPERTY_PREPARE_THRESHOLD = "prepare.threshold";

    /**
     * {@link PGProperty#PROTOCOL_VERSION}
     */
    public static final String DATASOURCE_PROPERTY_PROTOCOL_VERSION = "protocol.version";

    /**
     * {@link PGProperty#QUOTE_RETURNING_IDENTIFIERS}
     */
    public static final String DATASOURCE_PROPERTY_QUOTE_RETURNING_IDENTIFIERS = "quote.returning.identifiers";

    /**
     * {@link PGProperty#READ_ONLY}
     */
    public static final String DATASOURCE_PROPERTY_READ_ONLY = "read.only";

    /**
     * {@link PGProperty#READ_ONLY_MODE}
     */
    public static final String DATASOURCE_PROPERTY_READ_ONLY_MODE = "read.only.mode";

    /**
     * {@link PGProperty#RECEIVE_BUFFER_SIZE}
     */
    public static final String DATASOURCE_PROPERTY_RECEIVE_BUFFER_SIZE = "receive.buffer.size";

    /**
     * {@link PGProperty#REPLICATION}
     */
    public static final String DATASOURCE_PROPERTY_REPLICATION = "replication";

    /**
     * {@link PGProperty#REWRITE_BATCHED_INSERTS}
     */
    public static final String DATASOURCE_PROPERTY_REWRITE_BATCHED_INSERTS = "rewrite.batched.inserts";

    /**
     * {@link PGProperty#SEND_BUFFER_SIZE}
     */
    public static final String DATASOURCE_PROPERTY_SEND_BUFFER_SIZE = "send.buffer.size";

    /**
     * {@link PGProperty#SERVICE}
     */
    public static final String DATASOURCE_PROPERTY_SERVICE = "service";

    /**
     * {@link PGProperty#SOCKET_FACTORY}
     */
    public static final String DATASOURCE_PROPERTY_SOCKET_FACTORY = "socket.factory";

    /**
     * {@link PGProperty#SOCKET_FACTORY_ARG}
     */
    public static final String DATASOURCE_PROPERTY_SOCKET_FACTORY_ARG = "socket.factory.arg";

    /**
     * {@link PGProperty#SOCKET_TIMEOUT}
     */
    public static final String DATASOURCE_PROPERTY_SOCKET_TIMEOUT = "socket.timeout";

    /**
     * {@link PGProperty#SSL}
     */
    public static final String DATASOURCE_PROPERTY_SSL = "ssl";

    /**
     * {@link PGProperty#SSL_CERT}
     */
    public static final String DATASOURCE_PROPERTY_SSL_CERT = "ssl.cert";

    /**
     * {@link PGProperty#SSL_FACTORY}
     */
    public static final String DATASOURCE_PROPERTY_SSL_FACTORY = "ssl.factory";

    /**
     * {@link PGProperty#SSL_FACTORY_ARG}
     */
    public static final String DATASOURCE_PROPERTY_SSL_FACTORY_ARG = "ssl.factory.arg";

    /**
     * {@link PGProperty#SSL_HOSTNAME_VERIFIER}
     */
    public static final String DATASOURCE_PROPERTY_SSL_HOSTNAME_VERIFIER = "ssl.hostname.verifier";

    /**
     * {@link PGProperty#SSL_KEY}
     */
    public static final String DATASOURCE_PROPERTY_SSL_KEY = "ssl.key";

    /**
     * {@link PGProperty#SSL_MODE}
     */
    public static final String DATASOURCE_PROPERTY_SSL_MODE = "ssl.mode";

    /**
     * {@link PGProperty#SSL_PASSWORD}
     */
    public static final String DATASOURCE_PROPERTY_SSL_PASSWORD = ".ssl.password";

    /**
     * {@link PGProperty#SSL_PASSWORD_CALLBACK}
     */
    public static final String DATASOURCE_PROPERTY_SSL_PASSWORD_CALLBACK = "ssl.password.callback";

    /**
     * {@link PGProperty#SSL_RESPONSE_TIMEOUT}
     */
    public static final String DATASOURCE_PROPERTY_SSL_RESPONSE_TIMEOUT = "ssl.response.timeout";

    /**
     * {@link PGProperty#SSL_ROOT_CERT}
     */
    public static final String DATASOURCE_PROPERTY_SSL_ROOT_CERT = "ssl.root.cert";

    /**
     * {@link PGProperty#SSPI_SERVICE_CLASS}
     */
    public static final String DATASOURCE_PROPERTY_SSPI_SERVICE_CLASS = "sspi.service.class";

    /**
     * {@link PGProperty#STRING_TYPE}
     */
    public static final String DATASOURCE_PROPERTY_STRING_TYPE = "string.type";

    /**
     * {@link PGProperty#TARGET_SERVER_TYPE}
     */
    public static final String DATASOURCE_PROPERTY_TARGET_SERVER_TYPE = "target.server.type";

    /**
     * {@link PGProperty#TCP_KEEP_ALIVE}
     */
    public static final String DATASOURCE_PROPERTY_TCP_KEEP_ALIVE = "tcp.keep.alive";

    /**
     * {@link PGProperty#TCP_NO_DELAY}
     */
    public static final String DATASOURCE_PROPERTY_TCP_NO_DELAY = "tcp.no.delay";

    /**
     * {@link PGProperty#UNKNOWN_LENGTH}
     */
    public static final String DATASOURCE_PROPERTY_UNKNOWN_LENGTH = "unknown.length";

    /**
     * {@link PGProperty#USE_SPNEGO}
     */
    public static final String DATASOURCE_PROPERTY_USE_SPNEGO = "use.spnego";

    /**
     * {@link PGProperty#XML_FACTORY_FACTORY}
     */
    public static final String DATASOURCE_PROPERTY_XML_FACTORY_FACTORY = "xml.factory.factory";

}
