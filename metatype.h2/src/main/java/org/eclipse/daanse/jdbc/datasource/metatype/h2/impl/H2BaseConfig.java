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
package org.eclipse.daanse.jdbc.datasource.metatype.h2.impl;

import static org.eclipse.daanse.jdbc.datasource.metatype.h2.api.Constants.DATASOURCE_PROPERTY_DATABASE_TO_UPPER;
import static org.eclipse.daanse.jdbc.datasource.metatype.h2.api.Constants.DATASOURCE_PROPERTY_DEBUG;
import static org.eclipse.daanse.jdbc.datasource.metatype.h2.api.Constants.DATASOURCE_PROPERTY_DESCRIPTION;
import static org.eclipse.daanse.jdbc.datasource.metatype.h2.api.Constants.DATASOURCE_PROPERTY_IDENTIFIER;
import static org.eclipse.daanse.jdbc.datasource.metatype.h2.api.Constants.DATASOURCE_PROPERTY_PASSWORD;
import static org.eclipse.daanse.jdbc.datasource.metatype.h2.api.Constants.DATASOURCE_PROPERTY_PLUGABLE_FILESYSTEM;
import static org.eclipse.daanse.jdbc.datasource.metatype.h2.api.Constants.DATASOURCE_PROPERTY_USERNAME;

import org.eclipse.daanse.jdbc.datasource.metatype.h2.api.Constants;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.osgi.service.metatype.annotations.Option;

@ObjectClassDefinition(name = H2BaseConfig.L10N_OCD_NAME, description = H2BaseConfig.L10N_POSTFIX_DESCRIPTION)
@interface H2BaseConfig {

    public static final String L10N_PREFIX = "%";

    public static final String L10N_POSTFIX_DESCRIPTION = ".description";
    public static final String L10N_POSTFIX_NAME = ".name";

    public static final String L10N_OCD_NAME = L10N_PREFIX + "ocd" + L10N_POSTFIX_NAME;
    public static final String L10N_OCD_DESCRIPTION = L10N_PREFIX + "ocd" + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_DEBUG_NAME = L10N_PREFIX + DATASOURCE_PROPERTY_DEBUG + L10N_POSTFIX_NAME;
    public static final String L10N_DEBUG_DESCRIPTION = L10N_PREFIX + DATASOURCE_PROPERTY_DEBUG
            + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_DESCRIPTION_NAME = L10N_PREFIX + DATASOURCE_PROPERTY_DESCRIPTION
            + L10N_POSTFIX_NAME;
    public static final String L10N_DESCRIPTION_DESCRIPTION = L10N_PREFIX + DATASOURCE_PROPERTY_DESCRIPTION
            + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_PLUGABLE_FILESYSTEM_NAME = L10N_PREFIX + DATASOURCE_PROPERTY_PLUGABLE_FILESYSTEM
            + L10N_POSTFIX_NAME;
    public static final String L10N_PLUGABLE_FILESYSTEM_DESCRIPTION = L10N_PREFIX
            + DATASOURCE_PROPERTY_PLUGABLE_FILESYSTEM + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_IDENTIFIER_NAME = L10N_PREFIX + DATASOURCE_PROPERTY_IDENTIFIER + L10N_POSTFIX_NAME;
    public static final String L10N_IDENTIFIER_DESCRIPTION = L10N_PREFIX + DATASOURCE_PROPERTY_IDENTIFIER
            + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_PASSWORD_NAME = L10N_PREFIX + DATASOURCE_PROPERTY_PASSWORD + L10N_POSTFIX_NAME;
    public static final String L10N_PASSWORD_DESCRIPTION = L10N_PREFIX + DATASOURCE_PROPERTY_PASSWORD
            + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_USERNAME_NAME = L10N_PREFIX + DATASOURCE_PROPERTY_USERNAME + L10N_POSTFIX_NAME;
    public static final String L10N_USERNAME_DESCRIPTION = L10N_PREFIX + DATASOURCE_PROPERTY_USERNAME
            + L10N_POSTFIX_DESCRIPTION;

    public static final String L10N_DATABASE_TO_UPPER_NAME = L10N_PREFIX + DATASOURCE_PROPERTY_DATABASE_TO_UPPER
            + L10N_POSTFIX_NAME;

    public static final String L10N_DATABASE_TO_UPPER_DESCRIPTION = L10N_PREFIX + DATASOURCE_PROPERTY_DATABASE_TO_UPPER
            + L10N_POSTFIX_DESCRIPTION;

    /**
     * {@link Constants#DATASOURCE_PROPERTY_DEBUG}.
     *
     */
    @AttributeDefinition(name = L10N_DEBUG_NAME, description = L10N_DEBUG_DESCRIPTION)
    boolean debug() default false;

    /**
     * {@link Constants#DATASOURCE_PROPERTY_DESCRIPTION}.
     *
     */
    @AttributeDefinition(name = L10N_DESCRIPTION_NAME, description = L10N_DESCRIPTION_DESCRIPTION)
    String description();

    /**
     * {@link Constants#DATASOURCE_PROPERTY_PLUGABLE_FILESYSTEM}.
     *
     */
    @AttributeDefinition(name = L10N_PLUGABLE_FILESYSTEM_NAME, description = L10N_PLUGABLE_FILESYSTEM_DESCRIPTION, options = {
            @Option(value = Constants.OPTION_PLUGABLE_FILESYSTEM_ASYNC),
            @Option(value = Constants.OPTION_PLUGABLE_FILESYSTEM_FILE),
            @Option(value = Constants.OPTION_PLUGABLE_FILESYSTEM_MEM_FS),
            @Option(value = Constants.OPTION_PLUGABLE_FILESYSTEM_MEM_LZF),
            @Option(value = Constants.OPTION_PLUGABLE_FILESYSTEM_NIO_MAPPED),
            @Option(value = Constants.OPTION_PLUGABLE_FILESYSTEM_NIO_MEM_FS),
            @Option(value = Constants.OPTION_PLUGABLE_FILESYSTEM_NIO_MEM_LZF),
            @Option(value = Constants.OPTION_PLUGABLE_FILESYSTEM_ZIP) })
    String plugableFilesystem();

    /**
     * {@link Constants#DATASOURCE_PROPERTY_IDENTIFIER}.
     *
     */
    @AttributeDefinition(name = L10N_IDENTIFIER_NAME, description = L10N_IDENTIFIER_DESCRIPTION, required = true)
    String identifier();

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
    @AttributeDefinition(name = L10N_USERNAME_NAME, description = L10N_USERNAME_DESCRIPTION)
    String username() default "";

    /**
     * {@link Constants#DATASOURCE_PROPERTY_DATABASE_TO_UPPER}.
     *
     */
    @AttributeDefinition(name = H2BaseConfig.L10N_DATABASE_TO_UPPER_NAME, description = L10N_DATABASE_TO_UPPER_DESCRIPTION)
    boolean databaseToUpper() default true;

}
