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

    public static final String L10N_PORTS_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_PG_HOST
            + L10N_POSTFIX_NAME;
    public static final String L10N_PORTS_DESCRIPTION = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_PG_HOST
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

    // https://docs.oracle.com/cd/E13222_01/wls/docs81/jdbc_drivers/oracle.html
    /**
     * {@link Constants#DATASOURCE_PROPERTY_PASSWORD}. OSGi Service Component Spec.:
     *
     * Component properties whose names start with full stop are available to the
     * component instance but are not available as service properties of the
     * registered service.
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

}
