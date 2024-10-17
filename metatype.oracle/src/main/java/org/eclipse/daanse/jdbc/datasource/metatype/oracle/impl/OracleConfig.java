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
package org.eclipse.daanse.jdbc.datasource.metatype.oracle.impl;

import org.eclipse.daanse.jdbc.datasource.metatype.oracle.api.Constants;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name = OracleConfig.L10N_OCD_NAME, description = OracleConfig.L10N_POSTFIX_DESCRIPTION)
@interface OracleConfig {

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

    public static final String L10N_DATABASENAME_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_DATABASENAME
            + L10N_POSTFIX_NAME;
    public static final String L10N_DATABASENAME_DESCRIPTION = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_DATABASENAME
            + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_SERVICENAME_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_SERVICENAME
            + L10N_POSTFIX_NAME;
    public static final String L10N_SERVICENAME_DESCRIPTION = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_SERVICENAME
            + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_USER_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_USER + L10N_POSTFIX_NAME;
    public static final String L10N_USER_DESCRIPTION = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_USER
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
     * {@link Constants#DATASOURCE_PROPERTY_USER}.
     *
     */
    @AttributeDefinition(name = L10N_USER_NAME, description = L10N_USER_DESCRIPTION)
    String user() default "";

    @AttributeDefinition(name = L10N_SERVICENAME_NAME, description = L10N_SERVICENAME_DESCRIPTION)
    String serviceName();

    @AttributeDefinition(name = L10N_PORTNUMBER_NAME, description = L10N_PORT_DESCRIPTION)
    int portNumber();

    @AttributeDefinition(name = L10N_DATABASENAME_NAME, description = L10N_DATABASENAME_DESCRIPTION)
    String databaseName();

    @AttributeDefinition(name = L10N_SERVERNAME_NAME, description = L10N_SERVERNAME_DESCRIPTION)
    String serverName();

}
