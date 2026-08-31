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
package org.eclipse.daanse.jdbc.datasource.hive.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import java.util.Properties;

import org.eclipse.daanse.jdbc.datasource.hive.api.Constants;
import org.junit.jupiter.api.Test;

class UtilTest {

    @Test
    void defaults() {
        assertThat(Util.buildUrl(Map.of())).isEqualTo("jdbc:hive2://localhost:10000/default");
        assertThat(Util.buildProperties(Map.of())).isEmpty();
    }

    @Test
    void hostPortDatabase() {
        Map<String, Object> config = Map.of(Constants.DATASOURCE_PROPERTY_SERVERNAME, "hive.example.org",
                Constants.DATASOURCE_PROPERTY_PORTNUMBER, 10500, Constants.DATASOURCE_PROPERTY_DATABASENAME, "sales");
        assertThat(Util.buildUrl(config)).isEqualTo("jdbc:hive2://hive.example.org:10500/sales");
    }

    @Test
    void portAsString() {
        assertThat(Util.buildUrl(Map.of(Constants.DATASOURCE_PROPERTY_PORTNUMBER, "10500")))
                .isEqualTo("jdbc:hive2://localhost:10500/default");
    }

    @Test
    void ssl() {
        assertThat(Util.buildUrl(Map.of(Constants.DATASOURCE_PROPERTY_SSL, true)))
                .isEqualTo("jdbc:hive2://localhost:10000/default;ssl=true");
        assertThat(Util.buildUrl(Map.of(Constants.DATASOURCE_PROPERTY_SSL, "true")))
                .endsWith(";ssl=true");
    }

    @Test
    void sessionConfAppendsToSessionVarList() {
        assertThat(Util.buildUrl(Map.of(Constants.DATASOURCE_PROPERTY_SESSION_CONF, "hive.execution.engine=tez")))
                .isEqualTo("jdbc:hive2://localhost:10000/default;hive.execution.engine=tez");
    }

    /** The Impala shape: HS2 protocol on 21050, no SASL. */
    @Test
    void impalaViaHiveDriver() {
        Map<String, Object> config = Map.of(Constants.DATASOURCE_PROPERTY_SERVERNAME, "impala.example.org",
                Constants.DATASOURCE_PROPERTY_PORTNUMBER, 21050, Constants.DATASOURCE_PROPERTY_SESSION_CONF,
                "auth=noSasl");
        assertThat(Util.buildUrl(config)).isEqualTo("jdbc:hive2://impala.example.org:21050/default;auth=noSasl");
    }

    @Test
    void credentials() {
        Properties p = Util.buildProperties(Map.of(Constants.DATASOURCE_PROPERTY_USER, "daanse",
                Constants.DATASOURCE_PROPERTY_PASSWORD, "secret"));
        assertThat(p.getProperty("user")).isEqualTo("daanse");
        assertThat(p.getProperty("password")).isEqualTo("secret");
    }

    @Test
    void emptyCredentialsOmitted() {
        Properties p = Util.buildProperties(Map.of(Constants.DATASOURCE_PROPERTY_USER, "",
                Constants.DATASOURCE_PROPERTY_PASSWORD, ""));
        assertThat(p).isEmpty();
    }
}
