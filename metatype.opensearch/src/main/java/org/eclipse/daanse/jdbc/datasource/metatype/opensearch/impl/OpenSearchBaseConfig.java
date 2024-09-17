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
package org.eclipse.daanse.jdbc.datasource.metatype.opensearch.impl;

import static org.eclipse.daanse.jdbc.datasource.metatype.opensearch.api.Constants.DATASOURCE_PROPERTY_AUTH;
import static org.eclipse.daanse.jdbc.datasource.metatype.opensearch.api.Constants.DATASOURCE_PROPERTY_HOST;
import static org.eclipse.daanse.jdbc.datasource.metatype.opensearch.api.Constants.DATASOURCE_PROPERTY_PASSWORD;
import static org.eclipse.daanse.jdbc.datasource.metatype.opensearch.api.Constants.DATASOURCE_PROPERTY_PATH;
import static org.eclipse.daanse.jdbc.datasource.metatype.opensearch.api.Constants.DATASOURCE_PROPERTY_PORT;
import static org.eclipse.daanse.jdbc.datasource.metatype.opensearch.api.Constants.DATASOURCE_PROPERTY_TRUSTSELFSIGNED;
import static org.eclipse.daanse.jdbc.datasource.metatype.opensearch.api.Constants.DATASOURCE_PROPERTY_USERNAME;
import static org.eclipse.daanse.jdbc.datasource.metatype.opensearch.api.Constants.DATASOURCE_PROPERTY_USESSL;

import org.eclipse.daanse.jdbc.datasource.metatype.opensearch.api.Constants;
import org.opensearch.jdbc.auth.AuthenticationType;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name = OpenSearchBaseConfig.L10N_OCD_NAME, description = OpenSearchBaseConfig.L10N_POSTFIX_DESCRIPTION)
@interface OpenSearchBaseConfig {

    public static final String L10N_PREFIX = "%";

    public static final String L10N_POSTFIX_DESCRIPTION = ".description";
    public static final String L10N_POSTFIX_NAME = ".name";

    public static final String L10N_OCD_NAME = L10N_PREFIX + "ocd" + L10N_POSTFIX_NAME;
    public static final String L10N_OCD_DESCRIPTION = L10N_PREFIX + "ocd" + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_HOST_NAME = L10N_PREFIX + DATASOURCE_PROPERTY_HOST + L10N_POSTFIX_NAME;
    public static final String L10N_HOST_DESCRIPTION = L10N_PREFIX + DATASOURCE_PROPERTY_HOST
            + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_PASSWORD_NAME = L10N_PREFIX + DATASOURCE_PROPERTY_PASSWORD + L10N_POSTFIX_NAME;
    public static final String L10N_PASSWORD_DESCRIPTION = L10N_PREFIX + DATASOURCE_PROPERTY_PASSWORD
            + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_USERNAME_NAME = L10N_PREFIX + DATASOURCE_PROPERTY_USERNAME + L10N_POSTFIX_NAME;
    public static final String L10N_USERNAME_DESCRIPTION = L10N_PREFIX + DATASOURCE_PROPERTY_USERNAME
            + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_PORT_NAME = L10N_PREFIX + DATASOURCE_PROPERTY_PORT + L10N_POSTFIX_NAME;
    public static final String L10N_PORT_DESCRIPTION = L10N_PREFIX + DATASOURCE_PROPERTY_PORT
            + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_PATH_NAME = L10N_PREFIX + DATASOURCE_PROPERTY_PATH + L10N_POSTFIX_NAME;
    public static final String L10N_PATH_DESCRIPTION = L10N_PREFIX + DATASOURCE_PROPERTY_PATH
            + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_USESSL_NAME = L10N_PREFIX + DATASOURCE_PROPERTY_USESSL + L10N_POSTFIX_NAME;
    public static final String L10N_USESSL_DESCRIPTION = L10N_PREFIX + DATASOURCE_PROPERTY_USESSL
            + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_TRUSTSELFSIGNED_NAME = L10N_PREFIX + DATASOURCE_PROPERTY_TRUSTSELFSIGNED
            + L10N_POSTFIX_NAME;
    public static final String L10N_TRUSTSELFSIGNED_DESCRIPTION = L10N_PREFIX + DATASOURCE_PROPERTY_TRUSTSELFSIGNED
            + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_AUTH_NAME = L10N_PREFIX + DATASOURCE_PROPERTY_AUTH + L10N_POSTFIX_NAME;
    public static final String L10N_AUTH_DESCRIPTION = L10N_PREFIX + DATASOURCE_PROPERTY_AUTH
            + L10N_POSTFIX_DESCRIPTION;

    /**
     * {@link Constants#DATASOURCE_PROPERTY_HOST}.
     *
     */
    @AttributeDefinition(name = L10N_HOST_NAME, description = L10N_HOST_DESCRIPTION, required = true)
    String host();

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
    @AttributeDefinition(name = L10N_USERNAME_NAME, description = L10N_USERNAME_DESCRIPTION)
    String username() default "";

    /**
     * {@link Constants#DATASOURCE_PROPERTY_PORT}.
     *
     */
    @AttributeDefinition(name = L10N_PORT_NAME, description = L10N_PORT_DESCRIPTION)
    int port() default 443;

    /**
     * {@link Constants#DATASOURCE_PROPERTY_PATH}.
     *
     */
    @AttributeDefinition(name = L10N_PATH_NAME, description = L10N_PATH_DESCRIPTION)
    String path();

    /**
     * {@link Constants#DATASOURCE_PROPERTY_USESSL}.
     *
     */
    @AttributeDefinition(name = L10N_USESSL_NAME, description = L10N_USESSL_DESCRIPTION)
    boolean usessl();

    /**
     * {@link Constants#DATASOURCE_PROPERTY_TRUSTSELFSIGNED}.
     *
     */
    @AttributeDefinition(name = L10N_TRUSTSELFSIGNED_NAME, description = L10N_TRUSTSELFSIGNED_DESCRIPTION)
    boolean trustSelfSigned();

    /**
     * {@link Constants#DATASOURCE_PROPERTY_AUTH}.
     *
     */
    @AttributeDefinition(name = L10N_AUTH_NAME, description = L10N_AUTH_DESCRIPTION)
    AuthenticationType auth();

}
