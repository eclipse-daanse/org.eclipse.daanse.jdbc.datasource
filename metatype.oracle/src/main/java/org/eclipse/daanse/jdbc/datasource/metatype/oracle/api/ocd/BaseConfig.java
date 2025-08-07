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
package org.eclipse.daanse.jdbc.datasource.metatype.oracle.api.ocd;

import org.eclipse.daanse.jdbc.datasource.metatype.oracle.api.Constants;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;

public interface BaseConfig {

    String OCD_LOCALIZATION = "OSGI-INF/l10n/org.eclipse.daanse.jdbc.datasource.metatype.oracle.ocd";
    String L10N_PREFIX = "%";

    String L10N_POSTFIX_DESCRIPTION = ".description";
    String L10N_POSTFIX_NAME = ".name";

    String L10N_PASSWORD_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_PASSWORD + L10N_POSTFIX_NAME;
    String L10N_PASSWORD_DESCRIPTION = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_PASSWORD + L10N_POSTFIX_DESCRIPTION;

    String L10N_PORTNUMBER_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_PORTNUMBER + L10N_POSTFIX_NAME;
    String L10N_PORT_DESCRIPTION = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_PORTNUMBER + L10N_POSTFIX_DESCRIPTION;

    String L10N_SERVERNAME_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_SERVERNAME + L10N_POSTFIX_NAME;
    String L10N_SERVERNAME_DESCRIPTION = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_SERVERNAME
            + L10N_POSTFIX_DESCRIPTION;

    String L10N_DATABASENAME_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_DATABASENAME + L10N_POSTFIX_NAME;
    String L10N_DATABASENAME_DESCRIPTION = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_DATABASENAME
            + L10N_POSTFIX_DESCRIPTION;

    String L10N_SERVICENAME_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_SERVICENAME + L10N_POSTFIX_NAME;
    String L10N_SERVICENAME_DESCRIPTION = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_SERVICENAME
            + L10N_POSTFIX_DESCRIPTION;

    String L10N_USER_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_USER + L10N_POSTFIX_NAME;
    String L10N_USER_DESCRIPTION = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_USER + L10N_POSTFIX_DESCRIPTION;

    // Default value constants
    String DEFAULT_PASSWORD = "";
    String DEFAULT_USER = "";
    String DEFAULT_SERVICE_NAME = "";
    int DEFAULT_PORT_NUMBER = 1521;
    String DEFAULT_DATABASE_NAME = "";
    String DEFAULT_SERVER_NAME = "localhost";

    @AttributeDefinition(name = L10N_PASSWORD_NAME, description = L10N_PASSWORD_DESCRIPTION, type = AttributeType.PASSWORD, defaultValue = DEFAULT_PASSWORD)
    default String _password() {
        return DEFAULT_PASSWORD;
    }

    @AttributeDefinition(name = L10N_USER_NAME, description = L10N_USER_DESCRIPTION, defaultValue = DEFAULT_USER)
    default String user() {
        return DEFAULT_USER;
    }

    @AttributeDefinition(name = L10N_SERVICENAME_NAME, description = L10N_SERVICENAME_DESCRIPTION, defaultValue = DEFAULT_SERVICE_NAME)
    default String serviceName() {
        return DEFAULT_SERVICE_NAME;
    }

    @AttributeDefinition(name = L10N_PORTNUMBER_NAME, description = L10N_PORT_DESCRIPTION, defaultValue = DEFAULT_PORT_NUMBER
            + "")
    default int portNumber() {
        return DEFAULT_PORT_NUMBER;
    }

    @AttributeDefinition(name = L10N_DATABASENAME_NAME, description = L10N_DATABASENAME_DESCRIPTION, defaultValue = DEFAULT_DATABASE_NAME)
    default String databaseName() {
        return DEFAULT_DATABASE_NAME;
    }

    @AttributeDefinition(name = L10N_SERVERNAME_NAME, description = L10N_SERVERNAME_DESCRIPTION, defaultValue = DEFAULT_SERVER_NAME)
    default String serverName() {
        return DEFAULT_SERVER_NAME;
    }
}
