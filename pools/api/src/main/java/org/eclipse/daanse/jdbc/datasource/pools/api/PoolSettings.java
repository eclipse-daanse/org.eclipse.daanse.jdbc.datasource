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
 *   SmartCity Jena, Stefan Bischof - initial
 */
package org.eclipse.daanse.jdbc.datasource.pools.api;

import java.time.Duration;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Pool settings for a caller that constructs a pool directly. {@link #from(Map)}
 * is the boundary to Configuration Admin and the only place that has to know
 * the property names in {@link Constants}.
 *
 * @param leakThreshold age at which a still-held connection is reported;
 *                      {@link Duration#ZERO} disables it
 */
public record PoolSettings(String poolName, int maximumPoolSize, int minimumIdle, Duration connectionTimeout,
        Duration idleTimeout, Duration maxLifetime, Duration leakThreshold, boolean readOnly) {

    /** Every value from {@link Constants}. */
    public static PoolSettings defaults() {
        return new PoolSettings("", Constants.DEFAULT_MAX_SIZE, Constants.DEFAULT_MIN_IDLE,
                Duration.ofMillis(Constants.DEFAULT_ACQUIRE_TIMEOUT), Duration.ofMillis(Constants.DEFAULT_IDLE_TIMEOUT),
                Duration.ofMillis(Constants.DEFAULT_MAX_LIFETIME), Duration.ofMillis(Constants.DEFAULT_LEAK_THRESHOLD),
                Constants.DEFAULT_READ_ONLY);
    }

    public PoolSettings withPoolName(String name) {
        return new PoolSettings(name, maximumPoolSize, minimumIdle, connectionTimeout, idleTimeout, maxLifetime,
                leakThreshold, readOnly);
    }

    public PoolSettings withMaximumPoolSize(int size) {
        return new PoolSettings(poolName, size, minimumIdle, connectionTimeout, idleTimeout, maxLifetime, leakThreshold,
                readOnly);
    }

    public PoolSettings withMinimumIdle(int idle) {
        return new PoolSettings(poolName, maximumPoolSize, idle, connectionTimeout, idleTimeout, maxLifetime,
                leakThreshold, readOnly);
    }

    public PoolSettings withConnectionTimeout(Duration timeout) {
        return new PoolSettings(poolName, maximumPoolSize, minimumIdle, timeout, idleTimeout, maxLifetime,
                leakThreshold, readOnly);
    }

    /** Has to match the DataSource; see {@link Constants#POOL_PROPERTY_READ_ONLY}. */
    public PoolSettings withReadOnly(boolean readOnlyState) {
        return new PoolSettings(poolName, maximumPoolSize, minimumIdle, connectionTimeout, idleTimeout, maxLifetime,
                leakThreshold, readOnlyState);
    }

    /** As {@link #from(Map, Consumer)}, ignoring unreadable values. */
    public static PoolSettings from(Map<String, Object> config) {
        return from(config, m -> {
        });
    }

    /**
     * Reads the Configuration Admin properties named in {@link Constants},
     * falling back to {@link #defaults()} for anything absent or unreadable. An
     * unreadable value is reported rather than failing the component; this
     * module has no logger, so the caller supplies one.
     *
     * @param onProblem receives one message per unreadable value
     */
    public static PoolSettings from(Map<String, Object> config, Consumer<String> onProblem) {
        if (config == null || config.isEmpty()) {
            return defaults();
        }
        PoolSettings d = defaults();
        return new PoolSettings(string(config, Constants.POOL_PROPERTY_POOL_NAME, d.poolName()),
                (int) number(config, Constants.POOL_PROPERTY_MAX_SIZE, d.maximumPoolSize(), onProblem),
                (int) number(config, Constants.POOL_PROPERTY_MIN_IDLE, d.minimumIdle(), onProblem),
                millis(config, Constants.POOL_PROPERTY_ACQUIRE_TIMEOUT, d.connectionTimeout(), onProblem),
                millis(config, Constants.POOL_PROPERTY_IDLE_TIMEOUT, d.idleTimeout(), onProblem),
                millis(config, Constants.POOL_PROPERTY_MAX_LIFETIME, d.maxLifetime(), onProblem),
                millis(config, Constants.POOL_PROPERTY_LEAK_THRESHOLD, d.leakThreshold(), onProblem),
                bool(config, Constants.POOL_PROPERTY_READ_ONLY, d.readOnly()));
    }

    private static boolean bool(Map<String, Object> config, String key, boolean dflt) {
        Object v = config.get(key);
        if (v instanceof Boolean b) {
            return b;
        }
        return v == null ? dflt : Boolean.parseBoolean(v.toString().trim());
    }

    private static String string(Map<String, Object> config, String key, String dflt) {
        Object v = config.get(key);
        return v == null || v.toString().isBlank() ? dflt : v.toString();
    }

    private static Duration millis(Map<String, Object> config, String key, Duration dflt, Consumer<String> onProblem) {
        return Duration.ofMillis(number(config, key, dflt.toMillis(), onProblem));
    }

    private static long number(Map<String, Object> config, String key, long dflt, Consumer<String> onProblem) {
        Object v = config.get(key);
        if (v instanceof Number n) {
            return n.longValue();
        }
        if (v != null) {
            try {
                return Long.parseLong(v.toString().trim());
            } catch (NumberFormatException e) {
                onProblem.accept("pool property " + key + "=" + v + " is not a number, using " + dflt);
            }
        }
        return dflt;
    }
}
