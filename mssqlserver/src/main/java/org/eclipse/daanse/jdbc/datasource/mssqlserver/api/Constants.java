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
package org.eclipse.daanse.jdbc.datasource.mssqlserver.api;

import javax.sql.ConnectionPoolDataSource;
import javax.sql.DataSource;
import javax.sql.XADataSource;

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
    public static final String SUBPROTOCOL = "mssqlserver";

    /**
     * Constant for the {@link org.osgi.framework.Constants#SERVICE_PID} of a {@link DataSource} and
     * {@link XADataSource} and {@link ConnectionPoolDataSource} - Service.
     */
    public static final String PID_DATASOURCE = "daanse.jdbc.datasource.mssqlserver.DataSource";

    /**
     * Constant for the {@link org.osgi.framework.Constants#SERVICE_PID} of a
     * {@link ConnectionPoolDataSource} - Service.
     */
    public static final String PID_DATASOURCE_CP = "daanse.jdbc.datasource.mssqlserver.ConnectionPoolDataSource";

    /**
     * Constant for the {@link org.osgi.framework.Constants#SERVICE_PID} of a {@link XADataSource} -
     * Service.
     */
    public static final String PID_DATASOURCE_XA = "daanse.jdbc.datasource.mssqlserver.XADataSource";

    /**
     * Constant for Properties of the Service that could be configured using the
     * {@link Constants#PID_DATASOURCE}.
     *
     * {@link org.eclipse.daanse.jdbc.datasource.mssqlserver.api.ocd.BaseConfig#_password()}
     */
    public static final String DATASOURCE_PROPERTY_PASSWORD = ".password";

    /**
     * Constant for Properties of the Service that could be configured using the
     * {@link Constants#PID_DATASOURCE}.
     *
     * {@link org.eclipse.daanse.jdbc.datasource.mssqlserver.api.ocd.BaseConfig#user()}
     */
    public static final String DATASOURCE_PROPERTY_USER = "user";

    /**
     * Constant for Properties of the Service that could be configured using the
     * {@link Constants#PID_DATASOURCE}.
     *
     * {@link org.eclipse.daanse.jdbc.datasource.mssqlserver.api.ocd.BaseConfig#serverName()}
     */
    public static final String DATASOURCE_PROPERTY_SERVERNAME = "serverName";

    /**
     * Constant for Properties of the Service that could be configured using the
     * {@link Constants#PID_DATASOURCE}.
     *
     * {@link org.eclipse.daanse.jdbc.datasource.mssqlserver.api.ocd.BaseConfig#instanceName()}
     */
    public static final String DATASOURCE_PROPERTY_INSTANCENAME = "instanceName";

    /**
     * Constant for Properties of the Service that could be configured using the
     * {@link Constants#PID_DATASOURCE}.
     *
     * {@link org.eclipse.daanse.jdbc.datasource.mssqlserver.api.ocd.BaseConfig#applicationName()}
     */
    public static final String DATASOURCE_PROPERTY_APPLICATIONNAME = "applicationName";

    /**
     * Constant for Properties of the Service that could be configured using the
     * {@link Constants#PID_DATASOURCE}.
     *
     * {@link org.eclipse.daanse.jdbc.datasource.mssqlserver.api.ocd.BaseConfig#portNumber()}
     */
    public static final String DATASOURCE_PROPERTY_PORTNUMBER = "portNumber";

    // Additional MS SQL Server driver properties based on driver enums

    /**
     * SQLServerDriverStringProperty.DATABASE_NAME
     */
    public static final String DATASOURCE_PROPERTY_DATABASENAME = "databaseName";

    /**
     * SQLServerDriverStringProperty.FAILOVER_PARTNER
     */
    public static final String DATASOURCE_PROPERTY_FAILOVER_PARTNER = "failoverPartner";

    /**
     * SQLServerDriverStringProperty.HOSTNAME_IN_CERTIFICATE
     */
    public static final String DATASOURCE_PROPERTY_HOSTNAME_IN_CERTIFICATE = "hostNameInCertificate";

    /**
     * SQLServerDriverStringProperty.JAAS_CONFIG_NAME
     */
    public static final String DATASOURCE_PROPERTY_JAAS_CONFIG_NAME = "jaasConfigurationName";

    /**
     * SQLServerDriverStringProperty.RESPONSE_BUFFERING
     */
    public static final String DATASOURCE_PROPERTY_RESPONSE_BUFFERING = "responseBuffering";

    /**
     * SQLServerDriverStringProperty.SELECT_METHOD
     */
    public static final String DATASOURCE_PROPERTY_SELECT_METHOD = "selectMethod";

    /**
     * SQLServerDriverStringProperty.DOMAIN
     */
    public static final String DATASOURCE_PROPERTY_DOMAIN = "domain";

    /**
     * SQLServerDriverStringProperty.IPADDRESS_PREFERENCE
     */
    public static final String DATASOURCE_PROPERTY_IPADDRESS_PREFERENCE = "iPAddressPreference";

    /**
     * SQLServerDriverStringProperty.SERVER_SPN
     */
    public static final String DATASOURCE_PROPERTY_SERVER_SPN = "serverSpn";

    /**
     * SQLServerDriverStringProperty.REALM
     */
    public static final String DATASOURCE_PROPERTY_REALM = "realm";

    /**
     * SQLServerDriverStringProperty.SOCKET_FACTORY_CLASS
     */
    public static final String DATASOURCE_PROPERTY_SOCKET_FACTORY_CLASS = "socketFactoryClass";

    /**
     * SQLServerDriverStringProperty.SOCKET_FACTORY_CONSTRUCTOR_ARG
     */
    public static final String DATASOURCE_PROPERTY_SOCKET_FACTORY_CONSTRUCTOR_ARG = "socketFactoryConstructorArg";

    /**
     * SQLServerDriverStringProperty.TRUST_STORE_TYPE
     */
    public static final String DATASOURCE_PROPERTY_TRUST_STORE_TYPE = "trustStoreType";

    /**
     * SQLServerDriverStringProperty.TRUST_STORE
     */
    public static final String DATASOURCE_PROPERTY_TRUST_STORE = "trustStore";

    /**
     * SQLServerDriverStringProperty.TRUST_STORE_PASSWORD
     */
    public static final String DATASOURCE_PROPERTY_TRUST_STORE_PASSWORD = ".trustStorePassword";

    /**
     * SQLServerDriverStringProperty.TRUST_MANAGER_CLASS
     */
    public static final String DATASOURCE_PROPERTY_TRUST_MANAGER_CLASS = "trustManagerClass";

    /**
     * SQLServerDriverStringProperty.TRUST_MANAGER_CONSTRUCTOR_ARG
     */
    public static final String DATASOURCE_PROPERTY_TRUST_MANAGER_CONSTRUCTOR_ARG = "trustManagerConstructorArg";

    /**
     * SQLServerDriverStringProperty.WORKSTATION_ID
     */
    public static final String DATASOURCE_PROPERTY_WORKSTATION_ID = "workstationID";

    /**
     * SQLServerDriverStringProperty.AUTHENTICATION_SCHEME
     */
    public static final String DATASOURCE_PROPERTY_AUTHENTICATION_SCHEME = "authenticationScheme";

    /**
     * SQLServerDriverStringProperty.AUTHENTICATION
     */
    public static final String DATASOURCE_PROPERTY_AUTHENTICATION = "authentication";

    /**
     * SQLServerDriverStringProperty.ACCESS_TOKEN
     */
    public static final String DATASOURCE_PROPERTY_ACCESS_TOKEN = "accessToken";

    /**
     * SQLServerDriverStringProperty.COLUMN_ENCRYPTION
     */
    public static final String DATASOURCE_PROPERTY_COLUMN_ENCRYPTION = "columnEncryptionSetting";

    /**
     * SQLServerDriverStringProperty.ENCLAVE_ATTESTATION_URL
     */
    public static final String DATASOURCE_PROPERTY_ENCLAVE_ATTESTATION_URL = "enclaveAttestationUrl";

    /**
     * SQLServerDriverStringProperty.ENCLAVE_ATTESTATION_PROTOCOL
     */
    public static final String DATASOURCE_PROPERTY_ENCLAVE_ATTESTATION_PROTOCOL = "enclaveAttestationProtocol";

    /**
     * SQLServerDriverStringProperty.KEY_STORE_AUTHENTICATION
     */
    public static final String DATASOURCE_PROPERTY_KEY_STORE_AUTHENTICATION = "keyStoreAuthentication";

    /**
     * SQLServerDriverStringProperty.KEY_STORE_SECRET
     */
    public static final String DATASOURCE_PROPERTY_KEY_STORE_SECRET = "keyStoreSecret";

    /**
     * SQLServerDriverStringProperty.KEY_STORE_LOCATION
     */
    public static final String DATASOURCE_PROPERTY_KEY_STORE_LOCATION = "keyStoreLocation";

    /**
     * SQLServerDriverStringProperty.SSL_PROTOCOL
     */
    public static final String DATASOURCE_PROPERTY_SSL_PROTOCOL = "sslProtocol";

    /**
     * SQLServerDriverStringProperty.MSI_CLIENT_ID
     */
    public static final String DATASOURCE_PROPERTY_MSI_CLIENT_ID = "msiClientId";

    /**
     * SQLServerDriverStringProperty.KEY_VAULT_PROVIDER_CLIENT_ID
     */
    public static final String DATASOURCE_PROPERTY_KEY_VAULT_PROVIDER_CLIENT_ID = "keyVaultProviderClientId";

    /**
     * SQLServerDriverStringProperty.KEY_VAULT_PROVIDER_CLIENT_KEY
     */
    public static final String DATASOURCE_PROPERTY_KEY_VAULT_PROVIDER_CLIENT_KEY = "keyVaultProviderClientKey";

    /**
     * SQLServerDriverStringProperty.KEY_STORE_PRINCIPAL_ID
     */
    public static final String DATASOURCE_PROPERTY_KEY_STORE_PRINCIPAL_ID = "keyStorePrincipalId";

    /**
     * SQLServerDriverStringProperty.CLIENT_CERTIFICATE
     */
    public static final String DATASOURCE_PROPERTY_CLIENT_CERTIFICATE = "clientCertificate";

    /**
     * SQLServerDriverStringProperty.CLIENT_KEY
     */
    public static final String DATASOURCE_PROPERTY_CLIENT_KEY = "clientKey";

    /**
     * SQLServerDriverStringProperty.CLIENT_KEY_PASSWORD
     */
    public static final String DATASOURCE_PROPERTY_CLIENT_KEY_PASSWORD = ".clientKeyPassword";

    /**
     * SQLServerDriverStringProperty.AAD_SECURE_PRINCIPAL_ID
     */
    public static final String DATASOURCE_PROPERTY_AAD_SECURE_PRINCIPAL_ID = "AADSecurePrincipalId";

    /**
     * SQLServerDriverStringProperty.AAD_SECURE_PRINCIPAL_SECRET
     */
    public static final String DATASOURCE_PROPERTY_AAD_SECURE_PRINCIPAL_SECRET = "AADSecurePrincipalSecret";

    /**
     * SQLServerDriverStringProperty.MAX_RESULT_BUFFER
     */
    public static final String DATASOURCE_PROPERTY_MAX_RESULT_BUFFER = "maxResultBuffer";

    /**
     * SQLServerDriverStringProperty.ENCRYPT
     */
    public static final String DATASOURCE_PROPERTY_ENCRYPT = "encrypt";

    /**
     * SQLServerDriverStringProperty.SERVER_CERTIFICATE
     */
    public static final String DATASOURCE_PROPERTY_SERVER_CERTIFICATE = "serverCertificate";

    /**
     * SQLServerDriverStringProperty.DATETIME_DATATYPE
     */
    public static final String DATASOURCE_PROPERTY_DATETIME_DATATYPE = "datetimeParameterType";

    /**
     * SQLServerDriverStringProperty.ACCESS_TOKEN_CALLBACK_CLASS
     */
    public static final String DATASOURCE_PROPERTY_ACCESS_TOKEN_CALLBACK_CLASS = "accessTokenCallbackClass";

    /**
     * SQLServerDriverStringProperty.PREPARE_METHOD
     */
    public static final String DATASOURCE_PROPERTY_PREPARE_METHOD = "prepareMethod";

    /**
     * SQLServerDriverStringProperty.APPLICATION_INTENT
     */
    public static final String DATASOURCE_PROPERTY_APPLICATION_INTENT = "applicationIntent";

    // Integer properties from SQLServerDriverIntProperty

    /**
     * SQLServerDriverIntProperty.PACKET_SIZE
     */
    public static final String DATASOURCE_PROPERTY_PACKET_SIZE = "packetSize";

    /**
     * SQLServerDriverIntProperty.LOCK_TIMEOUT
     */
    public static final String DATASOURCE_PROPERTY_LOCK_TIMEOUT = "lockTimeout";

    /**
     * SQLServerDriverIntProperty.LOGIN_TIMEOUT
     */
    public static final String DATASOURCE_PROPERTY_LOGIN_TIMEOUT = "loginTimeout";

    /**
     * SQLServerDriverIntProperty.QUERY_TIMEOUT
     */
    public static final String DATASOURCE_PROPERTY_QUERY_TIMEOUT = "queryTimeout";

    /**
     * SQLServerDriverIntProperty.SOCKET_TIMEOUT
     */
    public static final String DATASOURCE_PROPERTY_SOCKET_TIMEOUT = "socketTimeout";

    /**
     * SQLServerDriverIntProperty.SERVER_PREPARED_STATEMENT_DISCARD_THRESHOLD
     */
    public static final String DATASOURCE_PROPERTY_SERVER_PREPARED_STATEMENT_DISCARD_THRESHOLD = "serverPreparedStatementDiscardThreshold";

    /**
     * SQLServerDriverIntProperty.STATEMENT_POOLING_CACHE_SIZE
     */
    public static final String DATASOURCE_PROPERTY_STATEMENT_POOLING_CACHE_SIZE = "statementPoolingCacheSize";

    /**
     * SQLServerDriverIntProperty.CANCEL_QUERY_TIMEOUT
     */
    public static final String DATASOURCE_PROPERTY_CANCEL_QUERY_TIMEOUT = "cancelQueryTimeout";

    /**
     * SQLServerDriverIntProperty.CONNECT_RETRY_COUNT
     */
    public static final String DATASOURCE_PROPERTY_CONNECT_RETRY_COUNT = "connectRetryCount";

    /**
     * SQLServerDriverIntProperty.CONNECT_RETRY_INTERVAL
     */
    public static final String DATASOURCE_PROPERTY_CONNECT_RETRY_INTERVAL = "connectRetryInterval";

    // Boolean properties from SQLServerDriverBooleanProperty

    /**
     * SQLServerDriverBooleanProperty.DISABLE_STATEMENT_POOLING
     */
    public static final String DATASOURCE_PROPERTY_DISABLE_STATEMENT_POOLING = "disableStatementPooling";

    /**
     * SQLServerDriverBooleanProperty.INTEGRATED_SECURITY
     */
    public static final String DATASOURCE_PROPERTY_INTEGRATED_SECURITY = "integratedSecurity";

    /**
     * SQLServerDriverBooleanProperty.LAST_UPDATE_COUNT
     */
    public static final String DATASOURCE_PROPERTY_LAST_UPDATE_COUNT = "lastUpdateCount";

    /**
     * SQLServerDriverBooleanProperty.MULTI_SUBNET_FAILOVER
     */
    public static final String DATASOURCE_PROPERTY_MULTI_SUBNET_FAILOVER = "multiSubnetFailover";

    /**
     * SQLServerDriverBooleanProperty.REPLICATION
     */
    public static final String DATASOURCE_PROPERTY_REPLICATION = "replication";

    /**
     * SQLServerDriverBooleanProperty.SERVER_NAME_AS_ACE
     */
    public static final String DATASOURCE_PROPERTY_SERVER_NAME_AS_ACE = "serverNameAsACE";

    /**
     * SQLServerDriverBooleanProperty.SEND_STRING_PARAMETERS_AS_UNICODE
     */
    public static final String DATASOURCE_PROPERTY_SEND_STRING_PARAMETERS_AS_UNICODE = "sendStringParametersAsUnicode";

    /**
     * SQLServerDriverBooleanProperty.SEND_TIME_AS_DATETIME
     */
    public static final String DATASOURCE_PROPERTY_SEND_TIME_AS_DATETIME = "sendTimeAsDatetime";

    /**
     * SQLServerDriverBooleanProperty.TRANSPARENT_NETWORK_IP_RESOLUTION
     */
    public static final String DATASOURCE_PROPERTY_TRANSPARENT_NETWORK_IP_RESOLUTION = "TransparentNetworkIPResolution";

    /**
     * SQLServerDriverBooleanProperty.TRUST_SERVER_CERTIFICATE
     */
    public static final String DATASOURCE_PROPERTY_TRUST_SERVER_CERTIFICATE = "trustServerCertificate";

    /**
     * SQLServerDriverBooleanProperty.XOPEN_STATES
     */
    public static final String DATASOURCE_PROPERTY_XOPEN_STATES = "xopenStates";

    /**
     * SQLServerDriverBooleanProperty.FIPS
     */
    public static final String DATASOURCE_PROPERTY_FIPS = "fips";

    /**
     * SQLServerDriverBooleanProperty.ENABLE_PREPARE_ON_FIRST_PREPARED_STATEMENT
     */
    public static final String DATASOURCE_PROPERTY_ENABLE_PREPARE_ON_FIRST_PREPARED_STATEMENT = "enablePrepareOnFirstPreparedStatementCall";

    /**
     * SQLServerDriverBooleanProperty.ENABLE_BULK_COPY_CACHE
     */
    public static final String DATASOURCE_PROPERTY_ENABLE_BULK_COPY_CACHE = "cacheBulkCopyMetadata";

    /**
     * SQLServerDriverBooleanProperty.USE_BULK_COPY_FOR_BATCH_INSERT
     */
    public static final String DATASOURCE_PROPERTY_USE_BULK_COPY_FOR_BATCH_INSERT = "useBulkCopyForBatchInsert";

    /**
     * SQLServerDriverBooleanProperty.USE_FMT_ONLY
     */
    public static final String DATASOURCE_PROPERTY_USE_FMT_ONLY = "useFmtOnly";

    /**
     * SQLServerDriverBooleanProperty.SEND_TEMPORAL_DATATYPES_AS_STRING_FOR_BULK_COPY
     */
    public static final String DATASOURCE_PROPERTY_SEND_TEMPORAL_DATATYPES_AS_STRING_FOR_BULK_COPY = "sendTemporalDataTypesAsStringForBulkCopy";

    /**
     * SQLServerDriverBooleanProperty.DELAY_LOADING_LOBS
     */
    public static final String DATASOURCE_PROPERTY_DELAY_LOADING_LOBS = "delayLoadingLobs";

    /**
     * SQLServerDriverBooleanProperty.IGNORE_OFFSET_ON_DATE_TIME_OFFSET_CONVERSION
     */
    public static final String DATASOURCE_PROPERTY_IGNORE_OFFSET_ON_DATE_TIME_OFFSET_CONVERSION = "ignoreOffsetOnDateTimeOffsetConversion";

    /**
     * SQLServerDriverBooleanProperty.USE_DEFAULT_JAAS_CONFIG
     */
    public static final String DATASOURCE_PROPERTY_USE_DEFAULT_JAAS_CONFIG = "useDefaultJaasConfig";

    /**
     * SQLServerDriverBooleanProperty.USE_DEFAULT_GSS_CREDENTIAL
     */
    public static final String DATASOURCE_PROPERTY_USE_DEFAULT_GSS_CREDENTIAL = "useDefaultGSSCredential";

    /**
     * SQLServerDriverBooleanProperty.CALC_BIG_DECIMAL_PRECISION
     */
    public static final String DATASOURCE_PROPERTY_CALC_BIG_DECIMAL_PRECISION = "calcBigDecimalPrecision";

}
