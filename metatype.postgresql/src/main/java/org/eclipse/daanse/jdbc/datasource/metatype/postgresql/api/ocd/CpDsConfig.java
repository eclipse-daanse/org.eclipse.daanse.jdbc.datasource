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
package org.eclipse.daanse.jdbc.datasource.metatype.postgresql.api.ocd;

import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name = CpDsConfig.L10N_OCD_CP_NAME, description = CpDsConfig.L10N_OCD_CP_DESCRIPTION, localization = CpDsConfig.OCD_LOCALIZATION)

public interface CpDsConfig extends BaseConfig {

    public static final String L10N_OCD_CP_NAME = L10N_PREFIX + "ocd" + ".cpds" + L10N_POSTFIX_NAME;
    public static final String L10N_OCD_CP_DESCRIPTION = L10N_PREFIX + "ocd" + ".cpds" + L10N_POSTFIX_DESCRIPTION;

}
