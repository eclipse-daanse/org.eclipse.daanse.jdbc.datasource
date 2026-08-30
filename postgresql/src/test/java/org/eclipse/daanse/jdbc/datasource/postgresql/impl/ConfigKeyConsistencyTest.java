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
*   Stefan Bischof (bipolis.org) - initial
*/
package org.eclipse.daanse.jdbc.datasource.postgresql.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.daanse.jdbc.datasource.postgresql.api.Constants;
import org.eclipse.daanse.jdbc.datasource.postgresql.api.ocd.DsConfig;
import org.junit.jupiter.api.Test;
import org.postgresql.ds.PGSimpleDataSource;

/**
 * Pins the contract between the {@link Constants#DATASOURCE_PROPERTY_CURRENT_SCHEMA
 * DATASOURCE_PROPERTY_*} keys {@link Util#doConfig} reads and the {@link DsConfig} methods that
 * produce them: the OSGi component-property mapping of each config method name ({@code $} removed,
 * {@code _} → {@code .}, {@code __} → {@code _}, camelCase kept as-is) must land exactly on the
 * constant — {@code currentSchema()} → {@code currentSchema}, {@code _password()} →
 * {@code .password}, matching the camelCase convention of the other datasource modules. A method
 * whose derived name matches no constant means {@code doConfig} never reads that key and the
 * setting is silently dropped end to end (metatype, ConfigAdmin and the {@code DAANSE_JDBC_*}
 * environment mapping all derive from the method name).
 */
class ConfigKeyConsistencyTest {

    /** The OSGi component property mapping of a config-interface method name. */
    private static String propertyName(String methodName) {
        StringBuilder sb = new StringBuilder(methodName.length());
        for (int i = 0; i < methodName.length(); i++) {
            char c = methodName.charAt(i);
            if (c == '$') {
                if (i + 1 < methodName.length() && methodName.charAt(i + 1) == '$') {
                    sb.append('$');
                    i++;
                }
            } else if (c == '_') {
                if (i + 1 < methodName.length() && methodName.charAt(i + 1) == '_') {
                    sb.append('_');
                    i++;
                } else {
                    sb.append('.');
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    private static Set<String> derivedPropertyNames() {
        Set<String> names = new java.util.TreeSet<>();
        for (Method m : DsConfig.class.getMethods()) {
            if (m.isDefault()) {
                names.add(propertyName(m.getName()));
            }
        }
        return names;
    }

    private static Set<String> constantValues() throws IllegalAccessException {
        Set<String> values = new java.util.TreeSet<>();
        for (Field f : Constants.class.getFields()) {
            if (Modifier.isStatic(f.getModifiers()) && f.getType() == String.class
                    && f.getName().startsWith("DATASOURCE_PROPERTY_")) {
                values.add((String) f.get(null));
            }
        }
        return values;
    }

    /** Every key {@code Util.doConfig} reads must be producible by a {@link DsConfig} method. */
    @Test
    void everyKeyReadByDoConfigHasAConfigMethod() {
        assertThat(derivedPropertyNames()).contains(
                Constants.DATASOURCE_PROPERTY_HOST,
                Constants.DATASOURCE_PROPERTY_DBNAME,
                Constants.DATASOURCE_PROPERTY_USER,
                Constants.DATASOURCE_PROPERTY_PASSWORD,
                Constants.DATASOURCE_PROPERTY_PORT,
                Constants.DATASOURCE_PROPERTY_APPLICATION_NAME,
                Constants.DATASOURCE_PROPERTY_CURRENT_SCHEMA,
                Constants.DATASOURCE_PROPERTY_SSL_MODE,
                Constants.DATASOURCE_PROPERTY_SSL_CERT,
                Constants.DATASOURCE_PROPERTY_SSL_KEY,
                Constants.DATASOURCE_PROPERTY_SSL_ROOT_CERT,
                Constants.DATASOURCE_PROPERTY_TARGET_SERVER_TYPE,
                Constants.DATASOURCE_PROPERTY_CONNECT_TIMEOUT,
                Constants.DATASOURCE_PROPERTY_LOGIN_TIMEOUT,
                Constants.DATASOURCE_PROPERTY_SOCKET_TIMEOUT,
                Constants.DATASOURCE_PROPERTY_DEFAULT_ROW_FETCH_SIZE,
                Constants.DATASOURCE_PROPERTY_PREPARE_THRESHOLD,
                Constants.DATASOURCE_PROPERTY_PREPARED_STATEMENT_CACHE_QUERIES,
                Constants.DATASOURCE_PROPERTY_SSL,
                Constants.DATASOURCE_PROPERTY_LOAD_BALANCE_HOSTS,
                Constants.DATASOURCE_PROPERTY_TCP_KEEP_ALIVE,
                Constants.DATASOURCE_PROPERTY_READ_ONLY);
    }

    /** No config method may map beside the constants — a camelCase name would land here. */
    @Test
    void everyConfigMethodMapsOntoAConstant() throws IllegalAccessException {
        Set<String> constants = constantValues();
        Set<String> unmapped = derivedPropertyNames().stream()
                .filter(n -> !constants.contains(n))
                .collect(Collectors.toSet());
        assertThat(unmapped).isEmpty();
    }

    /** End check: a map keyed by the constants actually reaches the PostgreSQL DataSource. */
    @Test
    void doConfigAppliesCurrentSchemaAndApplicationName() {
        PGSimpleDataSource ds = new PGSimpleDataSource();
        Util.doConfig(ds, Map.of(
                Constants.DATASOURCE_PROPERTY_CURRENT_SCHEMA, "theschema",
                Constants.DATASOURCE_PROPERTY_APPLICATION_NAME, "the-app"));
        assertThat(ds.getCurrentSchema()).isEqualTo("theschema");
        assertThat(ds.getApplicationName()).isEqualTo("the-app");
    }
}
