/*
* Copyright (c) 2026 Contributors to the Eclipse Foundation.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
*
* Contributors:
*   SmartCity Jena - initial
*/
package org.eclipse.daanse.jdbc.datasource.duckdb.api.ocd;

import org.eclipse.daanse.jdbc.datasource.duckdb.api.Constants;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

/**
 * Remote DuckDB server over the Quack client/server protocol: the DataSource is a
 * client and every connection works on the remote.
 * {@link Constants#PID_REMOTE_DATASOURCE}.
 */
@ObjectClassDefinition(name = RemoteDsConfig.L10N_OCD_REMOTE_NAME, description = RemoteDsConfig.L10N_OCD_REMOTE_DESCRIPTION, localization = BaseConfig.OCD_LOCALIZATION)
public interface RemoteDsConfig extends BaseConfig {

    String L10N_OCD_REMOTE_NAME = L10N_PREFIX + "ocd" + ".remote" + L10N_POSTFIX_NAME;
    String L10N_OCD_REMOTE_DESCRIPTION = L10N_PREFIX + "ocd" + ".remote" + L10N_POSTFIX_DESCRIPTION;

    String L10N_REMOTE_URI_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_REMOTE_URI + L10N_POSTFIX_NAME;
    String L10N_REMOTE_URI_DESCRIPTION = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_REMOTE_URI
            + L10N_POSTFIX_DESCRIPTION;

    String L10N_REMOTE_TOKEN_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_REMOTE_TOKEN + L10N_POSTFIX_NAME;
    String L10N_REMOTE_TOKEN_DESCRIPTION = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_REMOTE_TOKEN
            + L10N_POSTFIX_DESCRIPTION;

    String L10N_REMOTE_ALIAS_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_REMOTE_ALIAS + L10N_POSTFIX_NAME;
    String L10N_REMOTE_ALIAS_DESCRIPTION = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_REMOTE_ALIAS
            + L10N_POSTFIX_DESCRIPTION;

    String L10N_REMOTE_DISABLE_SSL_NAME = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_REMOTE_DISABLE_SSL
            + L10N_POSTFIX_NAME;
    String L10N_REMOTE_DISABLE_SSL_DESCRIPTION = L10N_PREFIX + Constants.DATASOURCE_PROPERTY_REMOTE_DISABLE_SSL
            + L10N_POSTFIX_DESCRIPTION;

    // Default value constants
    String DEFAULT_REMOTE_TOKEN = "";
    String DEFAULT_REMOTE_ALIAS = "remote";
    boolean DEFAULT_REMOTE_DISABLE_SSL = false;

    /**
     * Remote DuckDB server, {@code quack:<host>[:<port>]}. See
     * {@link Constants#DATASOURCE_PROPERTY_REMOTE_URI}.
     */
    @AttributeDefinition(name = L10N_REMOTE_URI_NAME, description = L10N_REMOTE_URI_DESCRIPTION, required = true)
    String remoteUri();

    /**
     * Authentication token of the remote server. A leading single low line becomes
     * a full stop ({@value Constants#DATASOURCE_PROPERTY_REMOTE_TOKEN}), which keeps
     * the token out of the service properties.
     */
    @AttributeDefinition(name = L10N_REMOTE_TOKEN_NAME, description = L10N_REMOTE_TOKEN_DESCRIPTION, type = AttributeType.PASSWORD, required = false, defaultValue = DEFAULT_REMOTE_TOKEN)
    default String _remoteToken() {
        return DEFAULT_REMOTE_TOKEN;
    }

    /**
     * Catalog name the remote server is attached under.
     */
    @AttributeDefinition(name = L10N_REMOTE_ALIAS_NAME, description = L10N_REMOTE_ALIAS_DESCRIPTION, required = false, defaultValue = DEFAULT_REMOTE_ALIAS)
    default String remoteAlias() {
        return DEFAULT_REMOTE_ALIAS;
    }

    /**
     * Talk plain HTTP to a remote server that is not local.
     */
    @AttributeDefinition(name = L10N_REMOTE_DISABLE_SSL_NAME, description = L10N_REMOTE_DISABLE_SSL_DESCRIPTION, required = false, defaultValue = DEFAULT_REMOTE_DISABLE_SSL
            + "")
    default boolean remoteDisableSsl() {
        return DEFAULT_REMOTE_DISABLE_SSL;
    }
}
