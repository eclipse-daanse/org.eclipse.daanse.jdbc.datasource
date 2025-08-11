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
package org.eclipse.daanse.jdbc.datasource.mariadb.api;

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
     *
     */
    public static final String SUBPROTOCOL = "mariadb";

    /**
     * Constant for the {@link org.osgi.framework.Constants#SERVICE_PID} of a {@link DataSource}
     */
    public static final String PID_DATASOURCE = "daanse.jdbc.datasource.mariadb.DataSource";

    /**
     * Constant for the {@link org.osgi.framework.Constants#SERVICE_PID} of a {@link DataSource}
     */
    public static final String PID_CONNECTION_POOL_DATASOURCE = "daanse.jdbc.datasource.mariadb.ConnectionPoolDataSource";

    /**
     * Constant for the {@link org.osgi.framework.Constants#SERVICE_PID} of a {@link DataSource}
     */
    public static final String PID_XA_DATASOURCE = "daanse.jdbc.datasource.mariadb.XADataSource";

    /**
     * Constant for Properties of the Service that could be configured.
     *
     * {@link org.eclipse.daanse.jdbc.datasource.mariadb.api.ocd.BaseConfig#_password()}
     */
    public static final String DATASOURCE_PROPERTY_PASSWORD = ".password";

    /**
     * Constant for Properties of the Service that could be configured.
     *
     * {@link org.eclipse.daanse.jdbc.datasource.mariadb.api.ocd.BaseConfig#user()}
     */
    public static final String DATASOURCE_PROPERTY_USER = "user";

    /**
     * Constant for Properties of the Service that could be configured.
     *
     * {@link org.eclipse.daanse.jdbc.datasource.mariadb.api.ocd.BaseConfig#host()}
     */
    public static final String DATASOURCE_PROPERTY_HOST = "host";

    /**
     * Constant for Properties of the Service that could be configured.
     *
     * {@link org.eclipse.daanse.jdbc.datasource.mariadb.api.ocd.BaseConfig#databaseName()}
     */
    public static final String DATASOURCE_PROPERTY_DATABASENAME = "databaseName";

    /**
     * Constant for Properties of the Service that could be configured.
     *
     * {@link org.eclipse.daanse.jdbc.datasource.mariadb.api.ocd.BaseConfig#port()}
     */
    public static final String DATASOURCE_PROPERTY_PORT = "port";

    // MariaDB-specific driver properties

    /**
     * MariaDB connect timeout
     */
    public static final String DATASOURCE_PROPERTY_CONNECT_TIMEOUT = "connectTimeout";

    /**
     * MariaDB use server prepared statements
     */
    public static final String DATASOURCE_PROPERTY_USE_SERVER_PREPARED_STMTS = "useServerPrepStmts";

    /**
     * MariaDB allow local infile
     */
    public static final String DATASOURCE_PROPERTY_ALLOW_LOCAL_INFILE = "allowLocalInfile";

    /**
     * MariaDB SSL mode
     */
    public static final String DATASOURCE_PROPERTY_SSL_MODE = "sslMode";

    /**
     * MariaDB server SSL certificate
     */
    public static final String DATASOURCE_PROPERTY_SERVER_SSL_CERT = "serverSslCert";

    /**
     * MariaDB key store
     */
    public static final String DATASOURCE_PROPERTY_KEY_STORE = "keyStore";

    /**
     * MariaDB key store password
     */
    public static final String DATASOURCE_PROPERTY_KEY_STORE_PASSWORD = ".keyStorePassword";

    /**
     * MariaDB enabled SSL cipher suites
     */
    public static final String DATASOURCE_PROPERTY_ENABLED_SSL_CIPHER_SUITES = "enabledSslCipherSuites";

    /**
     * MariaDB enabled SSL protocol suites
     */
    public static final String DATASOURCE_PROPERTY_ENABLED_SSL_PROTOCOL_SUITES = "enabledSslProtocolSuites";

    /**
     * MariaDB disable SSL hostname verification
     */
    public static final String DATASOURCE_PROPERTY_DISABLE_SSL_HOSTNAME_VERIFICATION = "disableSslHostnameVerification";

    /**
     * MariaDB use SSL
     */
    public static final String DATASOURCE_PROPERTY_USE_SSL = "useSsl";

    /**
     * MariaDB trust server certificate
     */
    public static final String DATASOURCE_PROPERTY_TRUST_SERVER_CERTIFICATE = "trustServerCertificate";

    /**
     * MariaDB pool
     */
    public static final String DATASOURCE_PROPERTY_POOL = "pool";

    /**
     * MariaDB pool name
     */
    public static final String DATASOURCE_PROPERTY_POOL_NAME = "poolName";

    /**
     * MariaDB max pool size
     */
    public static final String DATASOURCE_PROPERTY_MAX_POOL_SIZE = "maxPoolSize";

    /**
     * MariaDB min pool size
     */
    public static final String DATASOURCE_PROPERTY_MIN_POOL_SIZE = "minPoolSize";

    /**
     * MariaDB pool valid min delay
     */
    public static final String DATASOURCE_PROPERTY_POOL_VALID_MIN_DELAY = "poolValidMinDelay";

    /**
     * MariaDB max idle time
     */
    public static final String DATASOURCE_PROPERTY_MAX_IDLE_TIME = "maxIdleTime";

    /**
     * MariaDB use reset connection
     */
    public static final String DATASOURCE_PROPERTY_USE_RESET_CONNECTION = "useResetConnection";

    /**
     * MariaDB register JMX pool
     */
    public static final String DATASOURCE_PROPERTY_REGISTER_JMX_POOL = "registerJmxPool";

    /**
     * MariaDB trust store
     */
    public static final String DATASOURCE_PROPERTY_TRUST_STORE = "trustStore";

    /**
     * MariaDB trust store password
     */
    public static final String DATASOURCE_PROPERTY_TRUST_STORE_PASSWORD = ".trustStorePassword";

    /**
     * MariaDB trust store type
     */
    public static final String DATASOURCE_PROPERTY_TRUST_STORE_TYPE = "trustStoreType";

    /**
     * MariaDB use MySQL metadata
     */
    public static final String DATASOURCE_PROPERTY_USE_MYSQL_METADATA = "useMysqlMetadata";

    /**
     * MariaDB restricted auth
     */
    public static final String DATASOURCE_PROPERTY_RESTRICTED_AUTH = "restrictedAuth";

    /**
     * MariaDB max query size to log
     */
    public static final String DATASOURCE_PROPERTY_MAX_QUERY_SIZE_TO_LOG = "maxQuerySizeToLog";

    /**
     * MariaDB allow multi queries
     */
    public static final String DATASOURCE_PROPERTY_ALLOW_MULTI_QUERIES = "allowMultiQueries";

    /**
     * MariaDB dump queries on exception
     */
    public static final String DATASOURCE_PROPERTY_DUMP_QUERIES_ON_EXCEPTION = "dumpQueriesOnException";

    /**
     * MariaDB use compression
     */
    public static final String DATASOURCE_PROPERTY_USE_COMPRESSION = "useCompression";

    /**
     * MariaDB socket factory
     */
    public static final String DATASOURCE_PROPERTY_SOCKET_FACTORY = "socketFactory";

    /**
     * MariaDB TCP keep alive
     */
    public static final String DATASOURCE_PROPERTY_TCP_KEEP_ALIVE = "tcpKeepAlive";

    /**
     * MariaDB TCP abortive close
     */
    public static final String DATASOURCE_PROPERTY_TCP_ABORTIVE_CLOSE = "tcpAbortiveClose";

    /**
     * MariaDB pipe
     */
    public static final String DATASOURCE_PROPERTY_PIPE = "pipe";

    /**
     * MariaDB tinyInt1isBit
     */
    public static final String DATASOURCE_PROPERTY_TINY_INT1_IS_BIT = "tinyInt1isBit";

    /**
     * MariaDB year is date type
     */
    public static final String DATASOURCE_PROPERTY_YEAR_IS_DATE_TYPE = "yearIsDateType";

    /**
     * MariaDB session variables
     */
    public static final String DATASOURCE_PROPERTY_SESSION_VARIABLES = "sessionVariables";

    /**
     * MariaDB local socket
     */
    public static final String DATASOURCE_PROPERTY_LOCAL_SOCKET = "localSocket";

    /**
     * MariaDB local socket address
     */
    public static final String DATASOURCE_PROPERTY_LOCAL_SOCKET_ADDRESS = "localSocketAddress";

    /**
     * MariaDB socket timeout
     */
    public static final String DATASOURCE_PROPERTY_SOCKET_TIMEOUT = "socketTimeout";

    /**
     * MariaDB create database if not exist
     */
    public static final String DATASOURCE_PROPERTY_CREATE_DATABASE_IF_NOT_EXIST = "createDatabaseIfNotExist";

    /**
     * MariaDB cache callable statements
     */
    public static final String DATASOURCE_PROPERTY_CACHE_CALLABLE_STMTS = "cacheCallableStmts";

    /**
     * MariaDB connection attributes
     */
    public static final String DATASOURCE_PROPERTY_CONNECTION_ATTRIBUTES = "connectionAttributes";

    /**
     * MariaDB use pipeline auth
     */
    public static final String DATASOURCE_PROPERTY_USE_PIPELINE_AUTH = "usePipelineAuth";

    /**
     * MariaDB autocommit
     */
    public static final String DATASOURCE_PROPERTY_AUTOCOMMIT = "autocommit";

    /**
     * MariaDB galera allowed state
     */
    public static final String DATASOURCE_PROPERTY_GALERA_ALLOWED_STATE = "galeraAllowedState";

    /**
     * MariaDB include innodb status in deadlock exceptions
     */
    public static final String DATASOURCE_PROPERTY_INCLUDE_INNODB_STATUS_IN_DEADLOCK_EXCEPTIONS = "includeInnodbStatusInDeadlockExceptions";

    /**
     * MariaDB include thread dump in deadlock exceptions
     */
    public static final String DATASOURCE_PROPERTY_INCLUDE_THREAD_DUMP_IN_DEADLOCK_EXCEPTIONS = "includeThreadDumpInDeadlockExceptions";

    /**
     * MariaDB use read ahead input
     */
    public static final String DATASOURCE_PROPERTY_USE_READ_AHEAD_INPUT = "useReadAheadInput";

    /**
     * MariaDB service principal name
     */
    public static final String DATASOURCE_PROPERTY_SERVICE_PRINCIPAL_NAME = "servicePrincipalName";

    /**
     * MariaDB default fetch size
     */
    public static final String DATASOURCE_PROPERTY_DEFAULT_FETCH_SIZE = "defaultFetchSize";

    /**
     * MariaDB blank table name meta
     */
    public static final String DATASOURCE_PROPERTY_BLANK_TABLE_NAME_META = "blankTableNameMeta";

    /**
     * MariaDB server RSA public key file
     */
    public static final String DATASOURCE_PROPERTY_SERVER_RSA_PUBLIC_KEY_FILE = "serverRsaPublicKeyFile";

    /**
     * MariaDB allow public key retrieval
     */
    public static final String DATASOURCE_PROPERTY_ALLOW_PUBLIC_KEY_RETRIEVAL = "allowPublicKeyRetrieval";

    /**
     * MariaDB TLS socket type
     */
    public static final String DATASOURCE_PROPERTY_TLS_SOCKET_TYPE = "tlsSocketType";

    /**
     * MariaDB credential type
     */
    public static final String DATASOURCE_PROPERTY_CREDENTIAL_TYPE = "credentialType";

    /**
     * MariaDB TCP keep count
     */
    public static final String DATASOURCE_PROPERTY_TCP_KEEP_COUNT = "tcpKeepCount";

    /**
     * MariaDB TCP keep idle
     */
    public static final String DATASOURCE_PROPERTY_TCP_KEEP_IDLE = "tcpKeepIdle";

    /**
     * MariaDB TCP keep interval
     */
    public static final String DATASOURCE_PROPERTY_TCP_KEEP_INTERVAL = "tcpKeepInterval";

    /**
     * MariaDB permit MySQL scheme
     */
    public static final String DATASOURCE_PROPERTY_PERMIT_MYSQL_SCHEME = "permitMysqlScheme";

    /**
     * MariaDB prepared statement cache size
     */
    public static final String DATASOURCE_PROPERTY_PREP_STMT_CACHE_SIZE = "prepStmtCacheSize";

    /**
     * MariaDB transaction replay
     */
    public static final String DATASOURCE_PROPERTY_TRANSACTION_REPLAY = "transactionReplay";

    /**
     * MariaDB transaction replay size
     */
    public static final String DATASOURCE_PROPERTY_TRANSACTION_REPLAY_SIZE = "transactionReplaySize";

    /**
     * MariaDB use bulk statements
     */
    public static final String DATASOURCE_PROPERTY_USE_BULK_STMTS = "useBulkStmts";

    /**
     * MariaDB use catalog term
     */
    public static final String DATASOURCE_PROPERTY_USE_CATALOG_TERM = "useCatalogTerm";

    /**
     * MariaDB return multi values generated IDs
     */
    public static final String DATASOURCE_PROPERTY_RETURN_MULTI_VALUES_GENERATED_IDS = "returnMultiValuesGeneratedIds";

    /**
     * MariaDB pin global transaction to physical connection
     */
    public static final String DATASOURCE_PROPERTY_PIN_GLOBAL_TX_TO_PHYSICAL_CONNECTION = "pinGlobalTxToPhysicalConnection";

    /**
     * MariaDB connection collation
     */
    public static final String DATASOURCE_PROPERTY_CONNECTION_COLLATION = "connectionCollation";

    /**
     * MariaDB disconnect on expired passwords
     */
    public static final String DATASOURCE_PROPERTY_DISCONNECT_ON_EXPIRED_PASSWORDS = "disconnectOnExpiredPasswords";

    /**
     * MariaDB permit no results
     */
    public static final String DATASOURCE_PROPERTY_PERMIT_NO_RESULTS = "permitNoResults";

    /**
     * MariaDB old mode no precision timestamp
     */
    public static final String DATASOURCE_PROPERTY_OLD_MODE_NO_PRECISION_TIMESTAMP = "oldModeNoPrecisionTimestamp";

    /**
     * MariaDB cached codecs
     */
    public static final String DATASOURCE_PROPERTY_CACHED_CODECS = "cachedCodecs";

    /**
     * MariaDB meta exported keys
     */
    public static final String DATASOURCE_PROPERTY_META_EXPORTED_KEYS = "metaExportedKeys";

}
