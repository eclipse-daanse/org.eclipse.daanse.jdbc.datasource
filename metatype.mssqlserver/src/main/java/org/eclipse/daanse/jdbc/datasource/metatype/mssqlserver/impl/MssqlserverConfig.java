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
package org.eclipse.daanse.jdbc.datasource.metatype.mssqlserver.impl;

import org.eclipse.daanse.jdbc.datasource.metatype.mssqlserver.api.Constants;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name = MssqlserverConfig.L10N_OCD_NAME, description = MssqlserverConfig.L10N_POSTFIX_DESCRIPTION)
@interface MssqlserverConfig {

    public static final String L10N_PREFIX = "%";

    public static final String L10N_POSTFIX_DESCRIPTION = ".description";
    public static final String L10N_POSTFIX_NAME = ".name";

    public static final String L10N_OCD_NAME = L10N_PREFIX + "ocd" + L10N_POSTFIX_NAME;
    public static final String L10N_OCD_DESCRIPTION = L10N_PREFIX + "ocd" + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_PASSWORD_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_PASSWORD
            + L10N_POSTFIX_NAME;
    public static final String L10N_PASSWORD_DESCRIPTION = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_PASSWORD
            + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_PORTNUMBER_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_PORTNUMBER
            + L10N_POSTFIX_NAME;
    public static final String L10N_PORT_DESCRIPTION = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_PORTNUMBER
            + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_SERVERNAME_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_SERVERNAME
            + L10N_POSTFIX_NAME;
    public static final String L10N_SERVERNAME_DESCRIPTION = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_SERVERNAME
            + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_INSTANCENAME_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_INSTANCENAME
            + L10N_POSTFIX_NAME;
    public static final String L10N_INSTANCENAME_DESCRIPTION = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_INSTANCENAME
            + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_APPLICATIONNAME_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_APPLICATIONNAME
            + L10N_POSTFIX_NAME;
    public static final String L10N_APPLICATIONNAME_DESCRIPTION = L10N_PREFIX
            + Constants.DATASOURCE_PROPERTY_APPLICATIONNAME + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_USER_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_USER + L10N_POSTFIX_NAME;
    public static final String L10N_USER_DESCRIPTION = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_USER
            + L10N_POSTFIX_DESCRIPTION;

    // Additional L10N constants for commonly used properties
    public static final String L10N_DATABASENAME_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_DATABASENAME
            + L10N_POSTFIX_NAME;
    public static final String L10N_DATABASENAME_DESCRIPTION = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_DATABASENAME
            + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_ENCRYPT_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_ENCRYPT
            + L10N_POSTFIX_NAME;
    public static final String L10N_ENCRYPT_DESCRIPTION = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_ENCRYPT
            + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_TRUST_SERVER_CERTIFICATE_NAME = L10N_PREFIX
            + Constants.DATASOURCE_PROPERTY_TRUST_SERVER_CERTIFICATE + L10N_POSTFIX_NAME;
    public static final String L10N_TRUST_SERVER_CERTIFICATE_DESCRIPTION = L10N_PREFIX
            + Constants.DATASOURCE_PROPERTY_TRUST_SERVER_CERTIFICATE + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_INTEGRATED_SECURITY_NAME = L10N_PREFIX
            + Constants.DATASOURCE_PROPERTY_INTEGRATED_SECURITY + L10N_POSTFIX_NAME;
    public static final String L10N_INTEGRATED_SECURITY_DESCRIPTION = L10N_PREFIX
            + Constants.DATASOURCE_PROPERTY_INTEGRATED_SECURITY + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_AUTHENTICATION_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_AUTHENTICATION
            + L10N_POSTFIX_NAME;
    public static final String L10N_AUTHENTICATION_DESCRIPTION = L10N_PREFIX
            + Constants.DATASOURCE_PROPERTY_AUTHENTICATION + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_LOGIN_TIMEOUT_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_LOGIN_TIMEOUT
            + L10N_POSTFIX_NAME;
    public static final String L10N_LOGIN_TIMEOUT_DESCRIPTION = L10N_PREFIX
            + Constants.DATASOURCE_PROPERTY_LOGIN_TIMEOUT + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_QUERY_TIMEOUT_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_QUERY_TIMEOUT
            + L10N_POSTFIX_NAME;
    public static final String L10N_QUERY_TIMEOUT_DESCRIPTION = L10N_PREFIX
            + Constants.DATASOURCE_PROPERTY_QUERY_TIMEOUT + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_SOCKET_TIMEOUT_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_SOCKET_TIMEOUT
            + L10N_POSTFIX_NAME;
    public static final String L10N_SOCKET_TIMEOUT_DESCRIPTION = L10N_PREFIX
            + Constants.DATASOURCE_PROPERTY_SOCKET_TIMEOUT + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_RESPONSE_BUFFERING_NAME = L10N_PREFIX
            + Constants.DATASOURCE_PROPERTY_RESPONSE_BUFFERING + L10N_POSTFIX_NAME;
    public static final String L10N_RESPONSE_BUFFERING_DESCRIPTION = L10N_PREFIX
            + Constants.DATASOURCE_PROPERTY_RESPONSE_BUFFERING + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_SELECT_METHOD_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_SELECT_METHOD
            + L10N_POSTFIX_NAME;
    public static final String L10N_SELECT_METHOD_DESCRIPTION = L10N_PREFIX
            + Constants.DATASOURCE_PROPERTY_SELECT_METHOD + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_APPLICATION_INTENT_NAME = L10N_PREFIX
            + Constants.DATASOURCE_PROPERTY_APPLICATION_INTENT + L10N_POSTFIX_NAME;
    public static final String L10N_APPLICATION_INTENT_DESCRIPTION = L10N_PREFIX
            + Constants.DATASOURCE_PROPERTY_APPLICATION_INTENT + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_MULTI_SUBNET_FAILOVER_NAME = L10N_PREFIX
            + Constants.DATASOURCE_PROPERTY_MULTI_SUBNET_FAILOVER + L10N_POSTFIX_NAME;
    public static final String L10N_MULTI_SUBNET_FAILOVER_DESCRIPTION = L10N_PREFIX
            + Constants.DATASOURCE_PROPERTY_MULTI_SUBNET_FAILOVER + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_FAILOVER_PARTNER_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_FAILOVER_PARTNER
            + L10N_POSTFIX_NAME;
    public static final String L10N_FAILOVER_PARTNER_DESCRIPTION = L10N_PREFIX
            + Constants.DATASOURCE_PROPERTY_FAILOVER_PARTNER + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_PACKET_SIZE_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_PACKET_SIZE
            + L10N_POSTFIX_NAME;
    public static final String L10N_PACKET_SIZE_DESCRIPTION = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_PACKET_SIZE
            + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_LOCK_TIMEOUT_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_LOCK_TIMEOUT
            + L10N_POSTFIX_NAME;
    public static final String L10N_LOCK_TIMEOUT_DESCRIPTION = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_LOCK_TIMEOUT
            + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_COLUMN_ENCRYPTION_NAME = L10N_PREFIX
            + Constants.DATASOURCE_PROPERTY_COLUMN_ENCRYPTION + L10N_POSTFIX_NAME;
    public static final String L10N_COLUMN_ENCRYPTION_DESCRIPTION = L10N_PREFIX
            + Constants.DATASOURCE_PROPERTY_COLUMN_ENCRYPTION + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_SEND_STRING_PARAMETERS_AS_UNICODE_NAME = L10N_PREFIX
            + Constants.DATASOURCE_PROPERTY_SEND_STRING_PARAMETERS_AS_UNICODE + L10N_POSTFIX_NAME;
    public static final String L10N_SEND_STRING_PARAMETERS_AS_UNICODE_DESCRIPTION = L10N_PREFIX
            + Constants.DATASOURCE_PROPERTY_SEND_STRING_PARAMETERS_AS_UNICODE + L10N_POSTFIX_DESCRIPTION;

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
     * {@link Constants#DATASOURCE_PROPERTY_USER}.
     *
     */
    @AttributeDefinition(name = L10N_USER_NAME, description = L10N_USER_DESCRIPTION)
    String user() default "";

    @AttributeDefinition(name = L10N_APPLICATIONNAME_NAME, description = L10N_APPLICATIONNAME_DESCRIPTION)
    String applicationName();

    @AttributeDefinition(name = L10N_PORTNUMBER_NAME, description = L10N_PORT_DESCRIPTION)
    int portNumber();

    @AttributeDefinition(name = L10N_INSTANCENAME_NAME, description = L10N_INSTANCENAME_DESCRIPTION)
    String instanceName();

    @AttributeDefinition(name = L10N_SERVERNAME_NAME, description = L10N_SERVERNAME_DESCRIPTION)
    String serverName();

    // Additional MS SQL Server properties
    @AttributeDefinition(name = L10N_DATABASENAME_NAME, description = L10N_DATABASENAME_DESCRIPTION)
    String databaseName() default "";

    @AttributeDefinition(name = L10N_ENCRYPT_NAME, description = L10N_ENCRYPT_DESCRIPTION)
    String encrypt() default "true";

    @AttributeDefinition(name = L10N_TRUST_SERVER_CERTIFICATE_NAME, description = L10N_TRUST_SERVER_CERTIFICATE_DESCRIPTION)
    boolean trustServerCertificate() default false;

    @AttributeDefinition(name = L10N_INTEGRATED_SECURITY_NAME, description = L10N_INTEGRATED_SECURITY_DESCRIPTION)
    boolean integratedSecurity() default false;

    @AttributeDefinition(name = L10N_AUTHENTICATION_NAME, description = L10N_AUTHENTICATION_DESCRIPTION)
    String authentication() default "NotSpecified";

    @AttributeDefinition(name = L10N_LOGIN_TIMEOUT_NAME, description = L10N_LOGIN_TIMEOUT_DESCRIPTION)
    int loginTimeout() default 30;

    @AttributeDefinition(name = L10N_QUERY_TIMEOUT_NAME, description = L10N_QUERY_TIMEOUT_DESCRIPTION)
    int queryTimeout() default -1;

    @AttributeDefinition(name = L10N_SOCKET_TIMEOUT_NAME, description = L10N_SOCKET_TIMEOUT_DESCRIPTION)
    int socketTimeout() default 0;

    @AttributeDefinition(name = L10N_RESPONSE_BUFFERING_NAME, description = L10N_RESPONSE_BUFFERING_DESCRIPTION)
    String responseBuffering() default "adaptive";

    @AttributeDefinition(name = L10N_SELECT_METHOD_NAME, description = L10N_SELECT_METHOD_DESCRIPTION)
    String selectMethod() default "direct";

    @AttributeDefinition(name = L10N_APPLICATION_INTENT_NAME, description = L10N_APPLICATION_INTENT_DESCRIPTION)
    String applicationIntent() default "readwrite";

    @AttributeDefinition(name = L10N_MULTI_SUBNET_FAILOVER_NAME, description = L10N_MULTI_SUBNET_FAILOVER_DESCRIPTION)
    boolean multiSubnetFailover() default false;

    @AttributeDefinition(name = L10N_FAILOVER_PARTNER_NAME, description = L10N_FAILOVER_PARTNER_DESCRIPTION)
    String failoverPartner() default "";

    @AttributeDefinition(name = L10N_PACKET_SIZE_NAME, description = L10N_PACKET_SIZE_DESCRIPTION)
    int packetSize() default 4096;

    @AttributeDefinition(name = L10N_LOCK_TIMEOUT_NAME, description = L10N_LOCK_TIMEOUT_DESCRIPTION)
    int lockTimeout() default -1;

    @AttributeDefinition(name = L10N_COLUMN_ENCRYPTION_NAME, description = L10N_COLUMN_ENCRYPTION_DESCRIPTION)
    String columnEncryptionSetting() default "Disabled";

    @AttributeDefinition(name = L10N_SEND_STRING_PARAMETERS_AS_UNICODE_NAME, description = L10N_SEND_STRING_PARAMETERS_AS_UNICODE_DESCRIPTION)
    boolean sendStringParametersAsUnicode() default true;

}
