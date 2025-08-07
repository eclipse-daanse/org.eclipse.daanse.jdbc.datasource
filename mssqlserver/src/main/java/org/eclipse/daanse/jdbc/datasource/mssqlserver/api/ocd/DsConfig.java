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
package org.eclipse.daanse.jdbc.datasource.mssqlserver.api.ocd;

import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name = DsConfig.L10N_OCD_DS_NAME, description = DsConfig.L10N_OCD_DS_DESCRIPTION, localization = BaseConfig.OCD_LOCALIZATION)
public interface DsConfig extends BaseConfig {

    String L10N_OCD_DS_NAME = L10N_PREFIX + "ocd" + ".ds" + L10N_POSTFIX_NAME;
    String L10N_OCD_DS_DESCRIPTION = L10N_PREFIX + "ocd" + ".ds" + L10N_POSTFIX_DESCRIPTION;
}
