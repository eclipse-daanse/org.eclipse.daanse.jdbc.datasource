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
*   Stefan Bischof (bipolis.org) - initial
*/
package org.eclipse.daanse.jdbc.datasource.pools.hikari.api.ocd;

import org.eclipse.daanse.jdbc.datasource.pools.api.ocd.BaseConfig;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name = BaseConfig.L10N_OCD_NAME, description = BaseConfig.L10N_OCD_DESCRIPTION, localization = DsConfig.OCD_LOCALIZATION)
public interface DsConfig extends BaseConfig {

    String OCD_LOCALIZATION = "OSGI-INF/l10n/org.eclipse.daanse.jdbc.datasource.pools.hikari.ocd";

    String L10N_DATASOURCE_TARGET_NAME = L10N_PREFIX + "dataSource.target" + L10N_POSTFIX_NAME;
    String L10N_DATASOURCE_TARGET_DESCRIPTION = L10N_PREFIX + "dataSource.target" + L10N_POSTFIX_DESCRIPTION;

    /**
     * Which DataSource service to pool. Required: without it the component would
     * bind an arbitrary one.
     */
    @AttributeDefinition(name = L10N_DATASOURCE_TARGET_NAME, description = L10N_DATASOURCE_TARGET_DESCRIPTION)
    String dataSource_target();
}
