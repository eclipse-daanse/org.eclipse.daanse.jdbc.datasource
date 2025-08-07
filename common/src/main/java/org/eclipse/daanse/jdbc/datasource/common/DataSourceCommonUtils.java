/*
* Copyright (c) 2025 Contributors to the Eclipse Foundation.
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
package org.eclipse.daanse.jdbc.datasource.common;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for handling configuration maps in datasource services.
 */
public class DataSourceCommonUtils {

    private DataSourceCommonUtils() {
        // Utility class
    }

    /**
     * Creates a safe copy of the configuration map for logging purposes by removing all sensitive
     * properties that start with a leading dot (.). These properties typically contain passwords and
     * other sensitive data.
     *
     * @param configMap the original configuration map
     * @return a new map with sensitive properties removed
     */
    public static Map<String, Object> createSafeConfigMapForLogging(Map<String, Object> configMap) {
        Map<String, Object> safeConfigMap = new HashMap<>(configMap);

        // Remove all properties that start with a dot (.) as they are considered sensitive
        safeConfigMap.entrySet().removeIf(entry -> entry.getKey().startsWith("."));

        return safeConfigMap;
    }
}
