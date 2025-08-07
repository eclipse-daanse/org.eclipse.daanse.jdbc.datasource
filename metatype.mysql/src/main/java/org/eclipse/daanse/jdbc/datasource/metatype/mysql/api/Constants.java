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
package org.eclipse.daanse.jdbc.datasource.metatype.mysql.api;

import javax.sql.ConnectionPoolDataSource;
import javax.sql.DataSource;
import javax.sql.XADataSource;

import org.eclipse.daanse.jdbc.datasource.metatype.common.annotation.prototype.DataSourceMetaData;
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
    public static final String SUBPROTOCOL = "mysql";

    /**
     * Constant for the {@link org.osgi.framework.Constants#SERVICE_PID} of a {@link DataSource}
     */
    public static final String PID_DATASOURCE = "org.eclipse.daanse.jdbc.datasource.metatype.mysql.DataSource";

    /**
     * Constant for the {@link org.osgi.framework.Constants#SERVICE_PID} of a
     * {@link ConnectionPoolDataSource} - Service.
     */
    public static final String PID_DATASOURCE_CP = "org.eclipse.daanse.jdbc.datasource.metatype.mysql.ConnectionPoolDataSource";

    /**
     * Constant for the {@link org.osgi.framework.Constants#SERVICE_PID} of a {@link XADataSource} -
     * Service.
     */
    public static final String PID_DATASOURCE_XA = "org.eclipse.daanse.jdbc.datasource.metatype.mysql.XADataSource";

    /**
     * Constant for Properties of the Service that could be configured.
     *
     * {@link org.eclipse.daanse.jdbc.datasource.metatype.mysql.api.ocd.BaseConfig#_password()}
     */
    public static final String DATASOURCE_PROPERTY_PASSWORD = ".password";

    /**
     * Constant for Properties of the Service that could be configured.
     *
     * {@link org.eclipse.daanse.jdbc.datasource.metatype.mysql.api.ocd.BaseConfig#user()}
     */
    public static final String DATASOURCE_PROPERTY_USER = "user";

    /**
     * Constant for Properties of the Service that could be configured.
     *
     * {@link org.eclipse.daanse.jdbc.datasource.metatype.mysql.api.ocd.BaseConfig#host()}
     */
    public static final String DATASOURCE_PROPERTY_HOST = "host";

    /**
     * Constant for Properties of the Service that could be configured.
     *
     * {@link org.eclipse.daanse.jdbc.datasource.metatype.mysql.api.ocd.BaseConfig#databaseName()}
     */
    public static final String DATASOURCE_PROPERTY_DATABASENAME = "databaseName";

    /**
     * Constant for Properties of the Service that could be configured.
     *
     * {@link org.eclipse.daanse.jdbc.datasource.metatype.mysql.api.ocd.BaseConfig#port()}
     */
    public static final String DATASOURCE_PROPERTY_PORT = "port";

    // Additional MySQL driver properties from PropertyDefinitions

    /**
     * MySQL auto-reconnect setting
     */
    public static final String DATASOURCE_PROPERTY_AUTO_RECONNECT = "autoReconnect";

    /**
     * MySQL auto-reconnect for pools setting
     */
    public static final String DATASOURCE_PROPERTY_AUTO_RECONNECT_FOR_POOLS = "autoReconnectForPools";

    /**
     * MySQL initial timeout for connection
     */
    public static final String DATASOURCE_PROPERTY_INITIAL_TIMEOUT = "initialTimeout";

    /**
     * MySQL max reconnects
     */
    public static final String DATASOURCE_PROPERTY_MAX_RECONNECTS = "maxReconnects";

    /**
     * MySQL connection timeout
     */
    public static final String DATASOURCE_PROPERTY_CONNECTION_TIMEOUT = "connectTimeout";

    /**
     * MySQL socket timeout
     */
    public static final String DATASOURCE_PROPERTY_SOCKET_TIMEOUT = "socketTimeout";

    /**
     * MySQL use SSL
     */
    public static final String DATASOURCE_PROPERTY_USE_SSL = "useSSL";

    /**
     * MySQL require SSL
     */
    public static final String DATASOURCE_PROPERTY_REQUIRE_SSL = "requireSSL";

    /**
     * MySQL verify server certificate
     */
    public static final String DATASOURCE_PROPERTY_VERIFY_SERVER_CERTIFICATE = "verifyServerCertificate";

    /**
     * MySQL SSL certificate
     */
    public static final String DATASOURCE_PROPERTY_CLIENT_CERTIFICATE_KEYSTORE_URL = "clientCertificateKeyStoreUrl";

    /**
     * MySQL SSL certificate password
     */
    public static final String DATASOURCE_PROPERTY_CLIENT_CERTIFICATE_KEYSTORE_PASSWORD = ".clientCertificateKeyStorePassword";

    /**
     * MySQL trust certificate keystore URL
     */
    public static final String DATASOURCE_PROPERTY_TRUST_CERTIFICATE_KEYSTORE_URL = "trustCertificateKeyStoreUrl";

    /**
     * MySQL trust certificate keystore password
     */
    public static final String DATASOURCE_PROPERTY_TRUST_CERTIFICATE_KEYSTORE_PASSWORD = ".trustCertificateKeyStorePassword";

    /**
     * MySQL character encoding
     */
    public static final String DATASOURCE_PROPERTY_CHARACTER_ENCODING = "characterEncoding";

    /**
     * MySQL use server prepared statements
     */
    public static final String DATASOURCE_PROPERTY_USE_SERVER_PREPARED_STMTS = "useServerPrepStmts";

    /**
     * MySQL cache prepared statements
     */
    public static final String DATASOURCE_PROPERTY_CACHE_PREPARED_STMTS = "cachePrepStmts";

    /**
     * MySQL prepared statement cache size
     */
    public static final String DATASOURCE_PROPERTY_PREPARED_STATEMENT_CACHE_SIZE = "prepStmtCacheSize";

    /**
     * MySQL prepared statement cache SQL limit
     */
    public static final String DATASOURCE_PROPERTY_PREPARED_STATEMENT_CACHE_SQL_LIMIT = "prepStmtCacheSqlLimit";

    /**
     * MySQL use cursor fetch
     */
    public static final String DATASOURCE_PROPERTY_USE_CURSOR_FETCH = "useCursorFetch";

    /**
     * MySQL default fetch size
     */
    public static final String DATASOURCE_PROPERTY_DEFAULT_FETCH_SIZE = "defaultFetchSize";

    /**
     * MySQL use compression
     */
    public static final String DATASOURCE_PROPERTY_USE_COMPRESSION = "useCompression";

    /**
     * MySQL zero datetime behavior
     */
    public static final String DATASOURCE_PROPERTY_ZERO_DATETIME_BEHAVIOR = "zeroDateTimeBehavior";

    /**
     * MySQL transform zero timestamp
     */
    public static final String DATASOURCE_PROPERTY_TRANSFORM_ZERO_TIMESTAMP = "transformZeroTimestamp";

    /**
     * MySQL session variables
     */
    public static final String DATASOURCE_PROPERTY_SESSION_VARIABLES = "sessionVariables";

    /**
     * MySQL use affected rows
     */
    public static final String DATASOURCE_PROPERTY_USE_AFFECTED_ROWS = "useAffectedRows";

    /**
     * MySQL use local session state
     */
    public static final String DATASOURCE_PROPERTY_USE_LOCAL_SESSION_STATE = "useLocalSessionState";

    /**
     * MySQL use read ahead input
     */
    public static final String DATASOURCE_PROPERTY_USE_READ_AHEAD_INPUT = "useReadAheadInput";

    /**
     * MySQL use fast int parsing
     */
    public static final String DATASOURCE_PROPERTY_USE_FAST_INT_PARSING = "useFastIntParsing";

    /**
     * MySQL use fast date parsing
     */
    public static final String DATASOURCE_PROPERTY_USE_FAST_DATE_PARSING = "useFastDateParsing";

    /**
     * MySQL enable packet debug
     */
    public static final String DATASOURCE_PROPERTY_ENABLE_PACKET_DEBUG = "enablePacketDebug";

    /**
     * MySQL dump queries on exception
     */
    public static final String DATASOURCE_PROPERTY_DUMP_QUERIES_ON_EXCEPTION = "dumpQueriesOnException";

    /**
     * MySQL explain slow queries
     */
    public static final String DATASOURCE_PROPERTY_EXPLAIN_SLOW_QUERIES = "explainSlowQueries";

    /**
     * MySQL slow query threshold millis
     */
    public static final String DATASOURCE_PROPERTY_SLOW_QUERY_THRESHOLD_MILLIS = "slowQueryThresholdMillis";

    /**
     * MySQL log slow queries
     */
    public static final String DATASOURCE_PROPERTY_LOG_SLOW_QUERIES = "logSlowQueries";

    /**
     * MySQL profile SQL
     */
    public static final String DATASOURCE_PROPERTY_PROFILE_SQL = "profileSQL";

    /**
     * MySQL trace protocol
     */
    public static final String DATASOURCE_PROPERTY_TRACE_PROTOCOL = "traceProtocol";

    /**
     * MySQL use only server error messages
     */
    public static final String DATASOURCE_PROPERTY_USE_ONLY_SERVER_ERROR_MESSAGES = "useOnlyServerErrorMessages";

    /**
     * MySQL use SQL state for SQL exceptions
     */
    public static final String DATASOURCE_PROPERTY_USE_SQL_STATE_FOR_SQL_EXCEPTIONS = "useSqlStateCodes";

    /**
     * MySQL relaxAutoCommit
     */
    public static final String DATASOURCE_PROPERTY_RELAX_AUTO_COMMIT = "relaxAutoCommit";

    /**
     * MySQL rollbackOnPooledClose
     */
    public static final String DATASOURCE_PROPERTY_ROLLBACK_ON_POOLED_CLOSE = "rollbackOnPooledClose";

    /**
     * MySQL enable query timeouts
     */
    public static final String DATASOURCE_PROPERTY_ENABLE_QUERY_TIMEOUTS = "enableQueryTimeouts";

    /**
     * MySQL query timeout kill connection
     */
    public static final String DATASOURCE_PROPERTY_QUERY_TIMEOUT_KILL_CONNECTION = "queryTimeoutKillsConnection";

    /**
     * MySQL max allowed packet
     */
    public static final String DATASOURCE_PROPERTY_MAX_ALLOWED_PACKET = "maxAllowedPacket";

    /**
     * MySQL max query size to log
     */
    public static final String DATASOURCE_PROPERTY_MAX_QUERY_SIZE_TO_LOG = "maxQuerySizeToLog";

    /**
     * MySQL maintain time stats
     */
    public static final String DATASOURCE_PROPERTY_MAINTAIN_TIME_STATS = "maintainTimeStats";

    /**
     * MySQL use information schema
     */
    public static final String DATASOURCE_PROPERTY_USE_INFORMATION_SCHEMA = "useInformationSchema";

    /**
     * MySQL null catalog means current
     */
    public static final String DATASOURCE_PROPERTY_NULL_CATALOG_MEANS_CURRENT = "nullCatalogMeansCurrent";

    /**
     * MySQL null name pattern matches all
     */
    public static final String DATASOURCE_PROPERTY_NULL_NAME_PATTERN_MATCHES_ALL = "nullNamePatternMatchesAll";

    /**
     * MySQL use old alias metadata behavior
     */
    public static final String DATASOURCE_PROPERTY_USE_OLD_ALIAS_METADATA_BEHAVIOR = "useOldAliasMetadataBehavior";

    /**
     * MySQL capitalize type names
     */
    public static final String DATASOURCE_PROPERTY_CAPITALIZE_TYPE_NAMES = "capitalizeTypeNames";

    /**
     * MySQL treat year as date
     */
    public static final String DATASOURCE_PROPERTY_TREAT_YEAR_AS_DATE = "treatYearAsDate";

    /**
     * MySQL year is date type
     */
    public static final String DATASOURCE_PROPERTY_YEAR_IS_DATE_TYPE = "yearIsDateType";

    /**
     * MySQL tinyInt1isBit
     */
    public static final String DATASOURCE_PROPERTY_TINY_INT1_IS_BIT = "tinyInt1isBit";

    /**
     * MySQL transform into bit
     */
    public static final String DATASOURCE_PROPERTY_TRANSFORM_INTO_BIT = "transformedBitIsBoolean";

    /**
     * MySQL use legacy datetime code
     */
    public static final String DATASOURCE_PROPERTY_USE_LEGACY_DATETIME_CODE = "useLegacyDatetimeCode";

    /**
     * MySQL send fractional seconds
     */
    public static final String DATASOURCE_PROPERTY_SEND_FRACTIONAL_SECONDS = "sendFractionalSeconds";

    /**
     * MySQL fractional seconds on timestamps
     */
    public static final String DATASOURCE_PROPERTY_FRACTIONAL_SECONDS_ON_TIMESTAMPS = "sendFractionalSecondsForTime";

    /**
     * MySQL use timezone
     */
    public static final String DATASOURCE_PROPERTY_USE_TIMEZONE = "useTimezone";

    /**
     * MySQL server timezone
     */
    public static final String DATASOURCE_PROPERTY_SERVER_TIMEZONE = "serverTimezone";

    /**
     * MySQL override supports integrable types only
     */
    public static final String DATASOURCE_PROPERTY_OVERRIDE_SUPPORTS_INTEGRABLE_TYPES_ONLY = "overrideSupportsIntegrableTypesOnly";

    /**
     * MySQL no access to procedure bodies
     */
    public static final String DATASOURCE_PROPERTY_NO_ACCESS_TO_PROCEDURE_BODIES = "noAccessToProcedureBodies";

    /**
     * MySQL use host info
     */
    public static final String DATASOURCE_PROPERTY_USE_HOST_INFO = "useHostsInPrivileges";

    /**
     * MySQL logger class name
     */
    public static final String DATASOURCE_PROPERTY_LOGGER_CLASS_NAME = "logger";

    /**
     * MySQL profile sql class name
     */
    public static final String DATASOURCE_PROPERTY_PROFILE_SQL_CLASS_NAME = "profilerEventHandler";

    /**
     * MySQL result set size threshold
     */
    public static final String DATASOURCE_PROPERTY_RESULT_SET_SIZE_THRESHOLD = "resultSetSizeThreshold";

    /**
     * MySQL net buffer length
     */
    public static final String DATASOURCE_PROPERTY_NET_BUFFER_LENGTH = "netBufferLength";

    /**
     * MySQL max rows
     */
    public static final String DATASOURCE_PROPERTY_MAX_ROWS = "maxRows";

    /**
     * MySQL use blob to store UTF8 outside BMP
     */
    public static final String DATASOURCE_PROPERTY_USE_BLOB_TO_STORE_UTF8_OUT_SIDE_BMP = "useBlobToStoreUTF8OutsideBMP";

    /**
     * MySQL UTF8 outside BMP excluded column name pattern
     */
    public static final String DATASOURCE_PROPERTY_UTF8_OUT_SIDE_BMP_EXCLUDED_COLUMN_NAME_PATTERN = "utf8OutsideBmpExcludedColumnNamePattern";

    /**
     * MySQL UTF8 outside BMP included column name pattern
     */
    public static final String DATASOURCE_PROPERTY_UTF8_OUT_SIDE_BMP_INCLUDED_COLUMN_NAME_PATTERN = "utf8OutsideBmpIncludedColumnNamePattern";

    /**
     * MySQL include thread dump in deadlock exceptions
     */
    public static final String DATASOURCE_PROPERTY_INCLUDE_THREAD_DUMP_IN_DEADLOCK_EXCEPTIONS = "includeThreadDumpInDeadlockExceptions";

    /**
     * MySQL include thread name in exception message
     */
    public static final String DATASOURCE_PROPERTY_INCLUDE_THREAD_NAME_IN_EXCEPTION_MESSAGE = "includeThreadNamesAsStatementComment";

    /**
     * MySQL blobsAreStrings
     */
    public static final String DATASOURCE_PROPERTY_BLOBS_ARE_STRINGS = "blobsAreStrings";

    /**
     * MySQL functionsNeverReturnBlobs
     */
    public static final String DATASOURCE_PROPERTY_FUNCTIONS_NEVER_RETURN_BLOBS = "functionsNeverReturnBlobs";

    /**
     * MySQL auto generate test case script
     */
    public static final String DATASOURCE_PROPERTY_AUTO_GENERATE_TEST_CASE_SCRIPT = "autoGenerateTestcaseScript";

    /**
     * MySQL auto slowLog
     */
    public static final String DATASOURCE_PROPERTY_AUTO_SLOW_LOG = "autoSlowLog";

    /**
     * MySQL report metrics interval millis
     */
    public static final String DATASOURCE_PROPERTY_REPORT_METRICS_INTERVAL_MILLIS = "reportMetricsIntervalMillis";

    /**
     * MySQL pad char full length
     */
    public static final String DATASOURCE_PROPERTY_PAD_CHAR_FULL_LENGTH = "padCharsWithSpace";

    /**
     * MySQL use dynamic charset info
     */
    public static final String DATASOURCE_PROPERTY_USE_DYNAMIC_CHARSET_INFO = "useDynamicCharsetInfo";

    /**
     * MySQL client info provider
     */
    public static final String DATASOURCE_PROPERTY_CLIENT_INFO_PROVIDER = "clientInfoProvider";

    /**
     * MySQL populate insert row with default values
     */
    public static final String DATASOURCE_PROPERTY_POPULATE_INSERT_ROW_WITH_DEFAULT_VALUES = "populateInsertRowWithDefaultValues";

    /**
     * MySQL load balance strategy
     */
    public static final String DATASOURCE_PROPERTY_LOAD_BALANCE_STRATEGY = "loadBalanceStrategy";

    /**
     * MySQL server affinity order
     */
    public static final String DATASOURCE_PROPERTY_SERVER_AFFINITY_ORDER = "serverAffinityOrder";

    /**
     * MySQL allow load local infile
     */
    public static final String DATASOURCE_PROPERTY_ALLOW_LOAD_LOCAL_INFILE = "allowLoadLocalInfile";

    /**
     * MySQL allow load local infile in path
     */
    public static final String DATASOURCE_PROPERTY_ALLOW_LOAD_LOCAL_INFILE_IN_PATH = "allowLoadLocalInfileInPath";

    /**
     * MySQL allow multi queries
     */
    public static final String DATASOURCE_PROPERTY_ALLOW_MULTI_QUERIES = "allowMultiQueries";

    /**
     * MySQL allow URL in local infile
     */
    public static final String DATASOURCE_PROPERTY_ALLOW_URL_IN_LOCAL_INFILE = "allowUrlInLocalInfile";

    /**
     * MySQL always send set isolation
     */
    public static final String DATASOURCE_PROPERTY_ALWAYS_SEND_SET_ISOLATION = "alwaysSendSetIsolation";

    /**
     * MySQL auto closePStmtStreams
     */
    public static final String DATASOURCE_PROPERTY_AUTO_CLOSE_P_STMT_STREAMS = "autoClosePStmtStreams";

    /**
     * MySQL auto deserialize
     */
    public static final String DATASOURCE_PROPERTY_AUTO_DESERIALIZE = "autoDeserialize";

    /**
     * MySQL call able stmt cache size
     */
    public static final String DATASOURCE_PROPERTY_CALLABLE_STMT_CACHE_SIZE = "callableStmtCacheSize";

    /**
     * MySQL cache callable statements
     */
    public static final String DATASOURCE_PROPERTY_CACHE_CALLABLE_STMTS = "cacheCallableStmts";

    /**
     * MySQL cache result set metadata
     */
    public static final String DATASOURCE_PROPERTY_CACHE_RESULT_SET_METADATA = "cacheResultSetMetadata";

    /**
     * MySQL cache server configuration
     */
    public static final String DATASOURCE_PROPERTY_CACHE_SERVER_CONFIGURATION = "cacheServerConfiguration";

    /**
     * MySQL character set results
     */
    public static final String DATASOURCE_PROPERTY_CHARACTER_SET_RESULTS = "characterSetResults";

    /**
     * MySQL compensate on duplicate key update counts
     */
    public static final String DATASOURCE_PROPERTY_COMPENSATE_ON_DUPLICATE_KEY_UPDATE_COUNTS = "compensateOnDuplicateKeyUpdateCounts";

    /**
     * MySQL connection collation
     */
    public static final String DATASOURCE_PROPERTY_CONNECTION_COLLATION = "connectionCollation";

    /**
     * MySQL continuation batch on error
     */
    public static final String DATASOURCE_PROPERTY_CONTINUATION_BATCH_ON_ERROR = "continueBatchOnError";

    /**
     * MySQL create database if not exist
     */
    public static final String DATASOURCE_PROPERTY_CREATE_DATABASE_IF_NOT_EXIST = "createDatabaseIfNotExist";

    /**
     * MySQL detect custom collations
     */
    public static final String DATASOURCE_PROPERTY_DETECT_CUSTOM_COLLATIONS = "detectCustomCollations";

    /**
     * MySQL disconnect on expired passwords
     */
    public static final String DATASOURCE_PROPERTY_DISCONNECT_ON_EXPIRED_PASSWORDS = "disconnectOnExpiredPasswords";

    /**
     * MySQL dont track open resources
     */
    public static final String DATASOURCE_PROPERTY_DONT_TRACK_OPEN_RESOURCES = "dontTrackOpenResources";

    /**
     * MySQL elide set auto commits
     */
    public static final String DATASOURCE_PROPERTY_ELIDE_SET_AUTO_COMMITS = "elideSetAutoCommits";

    /**
     * MySQL emit update counts for info commands
     */
    public static final String DATASOURCE_PROPERTY_EMIT_UPDATE_COUNTS_FOR_INFO_COMMANDS = "emitUpdateCountsForInfoCommands";

    /**
     * MySQL enable escape processing
     */
    public static final String DATASOURCE_PROPERTY_ENABLE_ESCAPE_PROCESSING = "enableEscapeProcessing";

    /**
     * MySQL exception interceptors
     */
    public static final String DATASOURCE_PROPERTY_EXCEPTION_INTERCEPTORS = "exceptionInterceptors";

    /**
     * MySQL failover read only
     */
    public static final String DATASOURCE_PROPERTY_FAILOVER_READ_ONLY = "failOverReadOnly";

    /**
     * MySQL gather performance metrics
     */
    public static final String DATASOURCE_PROPERTY_GATHER_PERFORMANCE_METRICS = "gatherPerfMetrics";

    /**
     * MySQL generate simple parameter metadata
     */
    public static final String DATASOURCE_PROPERTY_GENERATE_SIMPLE_PARAMETER_METADATA = "generateSimpleParameterMetadata";

    /**
     * MySQL get procedure columns returns functions
     */
    public static final String DATASOURCE_PROPERTY_GET_PROCEDURE_COLUMNS_RETURNS_FUNCTIONS = "getProcedureColumnsReturnsFunctions";

    /**
     * MySQL ha load balance strategy
     */
    public static final String DATASOURCE_PROPERTY_HA_LOAD_BALANCE_STRATEGY = "haLoadBalanceStrategy";

    /**
     * MySQL hold results open over statement close
     */
    public static final String DATASOURCE_PROPERTY_HOLD_RESULTS_OPEN_OVER_STATEMENT_CLOSE = "holdResultsOpenOverStatementClose";

    /**
     * MySQL include in procedures
     */
    public static final String DATASOURCE_PROPERTY_INCLUDE_IN_PROCEDURES = "includeInnodbStatusInDeadlockExceptions";

    /**
     * MySQL interceptors
     */
    public static final String DATASOURCE_PROPERTY_INTERCEPTORS = "interceptors";

    /**
     * MySQL is interactiveClient
     */
    public static final String DATASOURCE_PROPERTY_IS_INTERACTIVE_CLIENT = "interactiveClient";

    /**
     * MySQL jdbc compliant truncation
     */
    public static final String DATASOURCE_PROPERTY_JDBC_COMPLIANT_TRUNCATION = "jdbcCompliantTruncation";

    /**
     * MySQL large row size threshold
     */
    public static final String DATASOURCE_PROPERTY_LARGE_ROW_SIZE_THRESHOLD = "largeRowSizeThreshold";

    /**
     * MySQL load balance auto commit stateless transactions
     */
    public static final String DATASOURCE_PROPERTY_LOAD_BALANCE_AUTO_COMMIT_STATELESS_TRANSACTIONS = "loadBalanceAutoCommitStatelessTransactions";

    /**
     * MySQL load balance black list timeout
     */
    public static final String DATASOURCE_PROPERTY_LOAD_BALANCE_BLACK_LIST_TIMEOUT = "loadBalanceBlacklistTimeout";

    /**
     * MySQL load balance connection group
     */
    public static final String DATASOURCE_PROPERTY_LOAD_BALANCE_CONNECTION_GROUP = "loadBalanceConnectionGroup";

    /**
     * MySQL load balance exception checker
     */
    public static final String DATASOURCE_PROPERTY_LOAD_BALANCE_EXCEPTION_CHECKER = "loadBalanceExceptionChecker";

    /**
     * MySQL load balance host removal grace period
     */
    public static final String DATASOURCE_PROPERTY_LOAD_BALANCE_HOST_REMOVAL_GRACE_PERIOD = "loadBalanceHostRemovalGracePeriod";

    /**
     * MySQL load balance ping timeout
     */
    public static final String DATASOURCE_PROPERTY_LOAD_BALANCE_PING_TIMEOUT = "loadBalancePingTimeout";

    /**
     * MySQL load balance SQL exception sub class list
     */
    public static final String DATASOURCE_PROPERTY_LOAD_BALANCE_SQL_EXCEPTION_SUB_CLASS_LIST = "loadBalanceSQLExceptionSubclassList";

    /**
     * MySQL load balance SQL state failover list
     */
    public static final String DATASOURCE_PROPERTY_LOAD_BALANCE_SQL_STATE_FAILOVER_LIST = "loadBalanceSQLStateFailoverList";

    /**
     * MySQL load balance validate connection on swap server
     */
    public static final String DATASOURCE_PROPERTY_LOAD_BALANCE_VALIDATE_CONNECTION_ON_SWAP_SERVER = "loadBalanceValidateConnectionOnSwapServer";

    /**
     * MySQL local socket address
     */
    public static final String DATASOURCE_PROPERTY_LOCAL_SOCKET_ADDRESS = "localSocketAddress";

    /**
     * MySQL log queries longer than ms
     */
    public static final String DATASOURCE_PROPERTY_LOG_QUERIES_LONGER_THAN_MS = "logQueriesLongerThanMs";

    /**
     * MySQL log XA commands
     */
    public static final String DATASOURCE_PROPERTY_LOG_XA_COMMANDS = "logXaCommands";

    /**
     * MySQL metadata cache size
     */
    public static final String DATASOURCE_PROPERTY_METADATA_CACHE_SIZE = "metadataCacheSize";

    /**
     * MySQL no date time string sync
     */
    public static final String DATASOURCE_PROPERTY_NO_DATE_TIME_STRING_SYNC = "noDatetimeStringSync";

    /**
     * MySQL no timestamps
     */
    public static final String DATASOURCE_PROPERTY_NO_TIMESTAMPS = "noTimezoneConversionForTimeType";

    /**
     * MySQL packet debug buffer size
     */
    public static final String DATASOURCE_PROPERTY_PACKET_DEBUG_BUFFER_SIZE = "packetDebugBufferSize";

    /**
     * MySQL paranoid
     */
    public static final String DATASOURCE_PROPERTY_PARANOID = "paranoid";

    /**
     * MySQL pedantic
     */
    public static final String DATASOURCE_PROPERTY_PEDANTIC = "pedantic";

    /**
     * MySQL ping interval
     */
    public static final String DATASOURCE_PROPERTY_PING_INTERVAL = "pinGlobalTxToPhysicalConnection";

    /**
     * MySQL process escape codes for prepared statements
     */
    public static final String DATASOURCE_PROPERTY_PROCESS_ESCAPE_CODES_FOR_PREPARED_STATEMENTS = "processEscapeCodesForPrepStmts";

    /**
     * MySQL queries before retrying master
     */
    public static final String DATASOURCE_PROPERTY_QUERIES_BEFORE_RETRYING_MASTER = "queriesBeforeRetryMaster";

    /**
     * MySQL read from master when no slaves
     */
    public static final String DATASOURCE_PROPERTY_READ_FROM_MASTER_WHEN_NO_SLAVES = "readFromMasterWhenNoSlaves";

    /**
     * MySQL read only propage to slaves
     */
    public static final String DATASOURCE_PROPERTY_READ_ONLY_PROPAGE_TO_SLAVES = "readOnlyPropagatesToServer";

    /**
     * MySQL reconnect at transaction boundary
     */
    public static final String DATASOURCE_PROPERTY_RECONNECT_AT_TRANSACTION_BOUNDARY = "reconnectAtTxnBoundary";

    /**
     * MySQL replication connection group
     */
    public static final String DATASOURCE_PROPERTY_REPLICATION_CONNECTION_GROUP = "replicationConnectionGroup";

    /**
     * MySQL report metrics interval
     */
    public static final String DATASOURCE_PROPERTY_REPORT_METRICS_INTERVAL = "reportMetricsIntervalMillis";

    /**
     * MySQL retrieval policy for generated keys
     */
    public static final String DATASOURCE_PROPERTY_RETRIEVAL_POLICY_FOR_GENERATED_KEYS = "retrieveGeneratedKeys";

    /**
     * MySQL rewrite batched statements
     */
    public static final String DATASOURCE_PROPERTY_REWRITE_BATCHED_STATEMENTS = "rewriteBatchedStatements";

    /**
     * MySQL round robin load balance
     */
    public static final String DATASOURCE_PROPERTY_ROUND_ROBIN_LOAD_BALANCE = "roundRobinLoadBalance";

    /**
     * MySQL seconds before retry master
     */
    public static final String DATASOURCE_PROPERTY_SECONDS_BEFORE_RETRY_MASTER = "secondsBeforeRetryMaster";

    /**
     * MySQL self destruct on ping max operations
     */
    public static final String DATASOURCE_PROPERTY_SELF_DESTRUCT_ON_PING_MAX_OPERATIONS = "selfDestructOnPingMaxOperations";

    /**
     * MySQL self destruct on ping seconds
     */
    public static final String DATASOURCE_PROPERTY_SELF_DESTRUCT_ON_PING_SECONDS = "selfDestructOnPingSecondsLifetime";

    /**
     * MySQL server prepared statement disclosure required
     */
    public static final String DATASOURCE_PROPERTY_SERVER_PREPARED_STATEMENT_DISCLOSURE_REQUIRED = "allowNanAndInf";

    /**
     * MySQL slave connection group
     */
    public static final String DATASOURCE_PROPERTY_SLAVE_CONNECTION_GROUP = "slaveConnectionGroup";

    /**
     * MySQL socket factory
     */
    public static final String DATASOURCE_PROPERTY_SOCKET_FACTORY = "socketFactory";

    /**
     * MySQL socket factory class name
     */
    public static final String DATASOURCE_PROPERTY_SOCKET_FACTORY_CLASS_NAME = "socketFactory";

    /**
     * MySQL SSL mode
     */
    public static final String DATASOURCE_PROPERTY_SSL_MODE = "sslMode";

    /**
     * MySQL statements resultSet type
     */
    public static final String DATASOURCE_PROPERTY_STATEMENTS_RESULT_SET_TYPE = "statementInterceptors";

    /**
     * MySQL strict floating point
     */
    public static final String DATASOURCE_PROPERTY_STRICT_FLOATING_POINT = "strictFloatingPoint";

    /**
     * MySQL strict updates
     */
    public static final String DATASOURCE_PROPERTY_STRICT_UPDATES = "strictUpdates";

    /**
     * MySQL tcp keep alive
     */
    public static final String DATASOURCE_PROPERTY_TCP_KEEP_ALIVE = "tcpKeepAlive";

    /**
     * MySQL tcp no delay
     */
    public static final String DATASOURCE_PROPERTY_TCP_NO_DELAY = "tcpNoDelay";

    /**
     * MySQL tcp rcv buf
     */
    public static final String DATASOURCE_PROPERTY_TCP_RCV_BUF = "tcpRcvBuf";

    /**
     * MySQL tcp send buf size
     */
    public static final String DATASOURCE_PROPERTY_TCP_SND_BUF = "tcpSndBuf";

    /**
     * MySQL tcp traffic class
     */
    public static final String DATASOURCE_PROPERTY_TCP_TRAFFIC_CLASS = "tcpTrafficClass";

    /**
     * MySQL treat zero as null
     */
    public static final String DATASOURCE_PROPERTY_TREAT_ZERO_AS_NULL = "treatUtilDateAsTimestamp";

    /**
     * MySQL use config character encoding
     */
    public static final String DATASOURCE_PROPERTY_USE_CONFIG_CHARACTER_ENCODING = "useConfigs";

    /**
     * MySQL use direct row unpacking
     */
    public static final String DATASOURCE_PROPERTY_USE_DIRECT_ROW_UNPACKING = "useDirectRowUnpack";

    /**
     * MySQL use get bytes for blobs
     */
    public static final String DATASOURCE_PROPERTY_USE_GET_BYTES_FOR_BLOBS = "useGetBytesForBlobs";

    /**
     * MySQL use jdbc compliant timezone shift
     */
    public static final String DATASOURCE_PROPERTY_USE_JDBC_COMPLIANT_TIMEZONE_SHIFT = "useJDBCCompliantTimezoneShift";

    /**
     * MySQL use old INIT client
     */
    public static final String DATASOURCE_PROPERTY_USE_OLD_INIT_CLIENT = "useOldUTF8Behavior";

    /**
     * MySQL use result set metadata cache
     */
    public static final String DATASOURCE_PROPERTY_USE_RESULT_SET_METADATA_CACHE = "useResultSetMetadataCache";

    /**
     * MySQL use server prepared statement metadata cache
     */
    public static final String DATASOURCE_PROPERTY_USE_SERVER_PREPARED_STATEMENT_METADATA_CACHE = "useServerPrepStmtMetadataCache";

    /**
     * MySQL use streaming result
     */
    public static final String DATASOURCE_PROPERTY_USE_STREAMING_RESULT = "useStreamingResult";

    /**
     * MySQL use unixdomainsockets
     */
    public static final String DATASOURCE_PROPERTY_USE_UNIX_DOMAIN_SOCKETS = "useUnixDomainSockets";

    /**
     * MySQL using JVMNano Time For Elapsed Time
     */
    public static final String DATASOURCE_PROPERTY_USE_NANO_FOR_ELAPSED_TIME = "useNanosForElapsedTime";

    /**
     * MySQL xa rollback on timeout
     */
    public static final String DATASOURCE_PROPERTY_XA_ROLLBACK_ON_TIMEOUT = "pinGlobalTxToPhysicalConnection";

}
