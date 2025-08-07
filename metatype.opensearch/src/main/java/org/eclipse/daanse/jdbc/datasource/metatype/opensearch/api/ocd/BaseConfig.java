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
package org.eclipse.daanse.jdbc.datasource.metatype.opensearch.api.ocd;

import org.eclipse.daanse.jdbc.datasource.metatype.opensearch.api.Constants;
import org.opensearch.jdbc.auth.AuthenticationType;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.Option;

public interface BaseConfig {

    String OCD_LOCALIZATION = "OSGI-INF/l10n/org.eclipse.daanse.jdbc.datasource.metatype.opensearch.ocd";
    String L10N_PREFIX = "%";
    String L10N_POSTFIX_DESCRIPTION = ".description";
    String L10N_POSTFIX_NAME = ".name";
    String L10N_POSTFIX_OPTION = ".option";
    String L10N_POSTFIX_LABEL = ".label";

    String L10N_HOST_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_HOST + L10N_POSTFIX_NAME;
    String L10N_HOST_DESCRIPTION = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_HOST + L10N_POSTFIX_DESCRIPTION;

    String L10N_PASSWORD_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_PASSWORD + L10N_POSTFIX_NAME;
    String L10N_PASSWORD_DESCRIPTION = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_PASSWORD + L10N_POSTFIX_DESCRIPTION;

    String L10N_USERNAME_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_USERNAME + L10N_POSTFIX_NAME;
    String L10N_USERNAME_DESCRIPTION = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_USERNAME + L10N_POSTFIX_DESCRIPTION;

    String L10N_PORT_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_PORT + L10N_POSTFIX_NAME;
    String L10N_PORT_DESCRIPTION = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_PORT + L10N_POSTFIX_DESCRIPTION;

    String L10N_PATH_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_PATH + L10N_POSTFIX_NAME;
    String L10N_PATH_DESCRIPTION = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_PATH + L10N_POSTFIX_DESCRIPTION;

    String L10N_USESSL_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_USESSL + L10N_POSTFIX_NAME;
    String L10N_USESSL_DESCRIPTION = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_USESSL + L10N_POSTFIX_DESCRIPTION;

    String L10N_TRUSTSELFSIGNED_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_TRUSTSELFSIGNED + L10N_POSTFIX_NAME;
    String L10N_TRUSTSELFSIGNED_DESCRIPTION = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_TRUSTSELFSIGNED
            + L10N_POSTFIX_DESCRIPTION;

    String L10N_AUTH_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_AUTH + L10N_POSTFIX_NAME;
    String L10N_AUTH_DESCRIPTION = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_AUTH + L10N_POSTFIX_DESCRIPTION;

    // Option label constants for Auth
    String L10N_AUTH_OPTION_NONE_LABEL = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_AUTH + L10N_POSTFIX_OPTION + ".NONE" + L10N_POSTFIX_LABEL;
    String L10N_AUTH_OPTION_BASIC_LABEL = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_AUTH + L10N_POSTFIX_OPTION + ".BASIC" + L10N_POSTFIX_LABEL;
    String L10N_AUTH_OPTION_AWS_SIGV4_LABEL = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_AUTH + L10N_POSTFIX_OPTION + ".AWS_SIGV4" + L10N_POSTFIX_LABEL;

    // Default value constants
    String DEFAULT_HOST = "";
    String DEFAULT_PASSWORD = "";
    String DEFAULT_USERNAME = "";
    int DEFAULT_PORT = 443;
    String DEFAULT_PATH = "";
    boolean DEFAULT_USESSL = true;
    boolean DEFAULT_TRUSTSELFSIGNED = false;
    AuthenticationType DEFAULT_AUTH = AuthenticationType.NONE;

    @AttributeDefinition(name = L10N_HOST_NAME, description = L10N_HOST_DESCRIPTION, required = true, defaultValue = DEFAULT_HOST)
    default String host() {
        return DEFAULT_HOST;
    }

    @AttributeDefinition(name = L10N_PASSWORD_NAME, description = L10N_PASSWORD_DESCRIPTION, type = AttributeType.PASSWORD, defaultValue = DEFAULT_PASSWORD)
    default String _password() {
        return DEFAULT_PASSWORD;
    }

    @AttributeDefinition(name = L10N_USERNAME_NAME, description = L10N_USERNAME_DESCRIPTION, defaultValue = DEFAULT_USERNAME)
    default String username() {
        return DEFAULT_USERNAME;
    }

    @AttributeDefinition(name = L10N_PORT_NAME, description = L10N_PORT_DESCRIPTION, defaultValue = DEFAULT_PORT + "")
    default int port() {
        return DEFAULT_PORT;
    }

    @AttributeDefinition(name = L10N_PATH_NAME, description = L10N_PATH_DESCRIPTION, defaultValue = DEFAULT_PATH)
    default String path() {
        return DEFAULT_PATH;
    }

    @AttributeDefinition(name = L10N_USESSL_NAME, description = L10N_USESSL_DESCRIPTION, defaultValue = DEFAULT_USESSL
            + "")
    default boolean usessl() {
        return DEFAULT_USESSL;
    }

    @AttributeDefinition(name = L10N_TRUSTSELFSIGNED_NAME, description = L10N_TRUSTSELFSIGNED_DESCRIPTION, defaultValue = DEFAULT_TRUSTSELFSIGNED
            + "")
    default boolean trustSelfSigned() {
        return DEFAULT_TRUSTSELFSIGNED;
    }

    @AttributeDefinition(name = L10N_AUTH_NAME, description = L10N_AUTH_DESCRIPTION, defaultValue = "NONE", options = {
            @Option(label = L10N_AUTH_OPTION_NONE_LABEL, value = "NONE"),
            @Option(label = L10N_AUTH_OPTION_BASIC_LABEL, value = "BASIC"),
            @Option(label = L10N_AUTH_OPTION_AWS_SIGV4_LABEL, value = "AWS_SIGV4")
    })
    default AuthenticationType auth() {
        return DEFAULT_AUTH;
    }
}
