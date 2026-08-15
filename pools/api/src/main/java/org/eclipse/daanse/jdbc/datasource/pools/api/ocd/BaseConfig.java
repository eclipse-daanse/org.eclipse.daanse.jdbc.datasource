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
package org.eclipse.daanse.jdbc.datasource.pools.api.ocd;

import org.eclipse.daanse.jdbc.datasource.pools.api.Constants;
import org.osgi.service.metatype.annotations.AttributeDefinition;

/**
 * The attributes every pool implementation shares. Implementation bundles extend
 * this in their own {@code @ObjectClassDefinition} and add whatever their library
 * offers on top.
 */
public interface BaseConfig {

    String L10N_PREFIX = "%";
    String L10N_POSTFIX_DESCRIPTION = ".description";
    String L10N_POSTFIX_NAME = ".name";

    String L10N_OCD_NAME = L10N_PREFIX + "ocd" + L10N_POSTFIX_NAME;
    String L10N_OCD_DESCRIPTION = L10N_PREFIX + "ocd" + L10N_POSTFIX_DESCRIPTION;

    String L10N_POOL_NAME_NAME = L10N_PREFIX + Constants.POOL_PROPERTY_POOL_NAME + L10N_POSTFIX_NAME;
    String L10N_POOL_NAME_DESCRIPTION = L10N_PREFIX + Constants.POOL_PROPERTY_POOL_NAME + L10N_POSTFIX_DESCRIPTION;
    String L10N_MAX_SIZE_NAME = L10N_PREFIX + Constants.POOL_PROPERTY_MAX_SIZE + L10N_POSTFIX_NAME;
    String L10N_MAX_SIZE_DESCRIPTION = L10N_PREFIX + Constants.POOL_PROPERTY_MAX_SIZE + L10N_POSTFIX_DESCRIPTION;
    String L10N_MIN_IDLE_NAME = L10N_PREFIX + Constants.POOL_PROPERTY_MIN_IDLE + L10N_POSTFIX_NAME;
    String L10N_MIN_IDLE_DESCRIPTION = L10N_PREFIX + Constants.POOL_PROPERTY_MIN_IDLE + L10N_POSTFIX_DESCRIPTION;
    String L10N_ACQUIRE_TIMEOUT_NAME = L10N_PREFIX + Constants.POOL_PROPERTY_ACQUIRE_TIMEOUT + L10N_POSTFIX_NAME;
    String L10N_ACQUIRE_TIMEOUT_DESCRIPTION = L10N_PREFIX + Constants.POOL_PROPERTY_ACQUIRE_TIMEOUT
            + L10N_POSTFIX_DESCRIPTION;
    String L10N_IDLE_TIMEOUT_NAME = L10N_PREFIX + Constants.POOL_PROPERTY_IDLE_TIMEOUT + L10N_POSTFIX_NAME;
    String L10N_IDLE_TIMEOUT_DESCRIPTION = L10N_PREFIX + Constants.POOL_PROPERTY_IDLE_TIMEOUT
            + L10N_POSTFIX_DESCRIPTION;
    String L10N_MAX_LIFETIME_NAME = L10N_PREFIX + Constants.POOL_PROPERTY_MAX_LIFETIME + L10N_POSTFIX_NAME;
    String L10N_MAX_LIFETIME_DESCRIPTION = L10N_PREFIX + Constants.POOL_PROPERTY_MAX_LIFETIME
            + L10N_POSTFIX_DESCRIPTION;
    String L10N_LEAK_THRESHOLD_NAME = L10N_PREFIX + Constants.POOL_PROPERTY_LEAK_THRESHOLD + L10N_POSTFIX_NAME;
    String L10N_LEAK_THRESHOLD_DESCRIPTION = L10N_PREFIX + Constants.POOL_PROPERTY_LEAK_THRESHOLD
            + L10N_POSTFIX_DESCRIPTION;
    String L10N_READ_ONLY_NAME = L10N_PREFIX + Constants.POOL_PROPERTY_READ_ONLY + L10N_POSTFIX_NAME;
    String L10N_READ_ONLY_DESCRIPTION = L10N_PREFIX + Constants.POOL_PROPERTY_READ_ONLY + L10N_POSTFIX_DESCRIPTION;

    @AttributeDefinition(name = L10N_POOL_NAME_NAME, description = L10N_POOL_NAME_DESCRIPTION, required = false)
    default String poolName() {
        return "";
    }

    @AttributeDefinition(name = L10N_MAX_SIZE_NAME, description = L10N_MAX_SIZE_DESCRIPTION, required = false)
    default int maximumPoolSize() {
        return Constants.DEFAULT_MAX_SIZE;
    }

    @AttributeDefinition(name = L10N_MIN_IDLE_NAME, description = L10N_MIN_IDLE_DESCRIPTION, required = false)
    default int minimumIdle() {
        return Constants.DEFAULT_MIN_IDLE;
    }

    @AttributeDefinition(name = L10N_ACQUIRE_TIMEOUT_NAME, description = L10N_ACQUIRE_TIMEOUT_DESCRIPTION, required = false)
    default long connectionTimeout() {
        return Constants.DEFAULT_ACQUIRE_TIMEOUT;
    }

    @AttributeDefinition(name = L10N_IDLE_TIMEOUT_NAME, description = L10N_IDLE_TIMEOUT_DESCRIPTION, required = false)
    default long idleTimeout() {
        return Constants.DEFAULT_IDLE_TIMEOUT;
    }

    @AttributeDefinition(name = L10N_MAX_LIFETIME_NAME, description = L10N_MAX_LIFETIME_DESCRIPTION, required = false)
    default long maxLifetime() {
        return Constants.DEFAULT_MAX_LIFETIME;
    }

    @AttributeDefinition(name = L10N_LEAK_THRESHOLD_NAME, description = L10N_LEAK_THRESHOLD_DESCRIPTION, required = false)
    default long leakThreshold() {
        return Constants.DEFAULT_LEAK_THRESHOLD;
    }

    @AttributeDefinition(name = L10N_READ_ONLY_NAME, description = L10N_READ_ONLY_DESCRIPTION, required = false)
    default boolean readOnly() {
        return Constants.DEFAULT_READ_ONLY;
    }
}
