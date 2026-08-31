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

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;
import java.util.Map;

import org.eclipse.daanse.jdbc.datasource.hive.api.Constants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 * Connects through {@link Util#createDataSource(Map)} / {@link HiveDataSource}
 * — the same path the OSGi component takes — against a real HiveServer2.
 * Gated by {@code -Dintegration.docker=true}; works against Docker and
 * rootless Podman (via DOCKER_HOST).
 */
@Testcontainers
@EnabledIfSystemProperty(named = "integration.docker", matches = "true")
class HiveDataSourceContainerTest {

    private static final int HS2_PORT = 10000;

    @Container
    @SuppressWarnings("resource")
    static final GenericContainer<?> CONTAINER = new GenericContainer<>("apache/hive:4.2.0")
            .withEnv("SERVICE_NAME", "hiveserver2").withExposedPorts(HS2_PORT)
            .waitingFor(Wait.forListeningPort()).withStartupTimeout(Duration.ofMinutes(5));

    @Test
    void connectsAndQueries() throws Exception {
        Map<String, Object> config = Map.of(Constants.DATASOURCE_PROPERTY_SERVERNAME, CONTAINER.getHost(),
                Constants.DATASOURCE_PROPERTY_PORTNUMBER, CONTAINER.getMappedPort(HS2_PORT),
                Constants.DATASOURCE_PROPERTY_DATABASENAME, "default");
        HiveDataSource ds = Util.createDataSource(config);

        // HS2 accepts TCP before sessions work — retry until a statement runs.
        SQLException last = null;
        for (int i = 0; i < 48; i++) {
            try (Connection c = ds.getConnection(); Statement s = c.createStatement()) {
                try (ResultSet rs = s.executeQuery("SELECT 1")) {
                    assertThat(rs.next()).isTrue();
                    assertThat(rs.getInt(1)).isEqualTo(1);
                }
                return;
            } catch (SQLException e) {
                last = e;
                Thread.sleep(5000);
            }
        }
        throw new IllegalStateException("HiveServer2 did not become ready", last);
    }
}
