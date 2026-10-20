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
package org.eclipse.daanse.jdbc.datasource.testkit.oracle;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.sql.DataSource;

import org.eclipse.daanse.jdbc.datasource.testkit.api.ActiveDatabase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;

/**
 * The test instance takes a burst of short connections without the listener refusing them (ORA-12516). With the
 * image's {@code processes=200} a burst of 320 connections, eight at a time, lost about a third to the refusal
 * (measured 17.10.2026, testwelten {@code befunde/oracle-ora-12516.md}); the provider sets {@code processes=400}.
 * Gated by {@code -Dintegration.docker=true}: it starts an Oracle container.
 */
@EnabledIfSystemProperty(named = "integration.docker", matches = "true")
class ManyShortConnectionsAreNotRefusedTest {

    @Test
    void aBurstOfShortConnectionsIsServed() throws InterruptedException {
        ActiveDatabase database = new OracleDatabaseProvider().activate("BURST");
        DataSource source = database.dataSource();
        ConcurrentLinkedQueue<SQLException> refused = new ConcurrentLinkedQueue<>();
        ExecutorService eight = Executors.newFixedThreadPool(8);
        for (int i = 0; i < 320; i++) {
            eight.execute(() -> {
                try (Connection connection = source.getConnection()) {
                    connection.isValid(5);
                } catch (SQLException e) {
                    refused.add(e);
                }
            });
        }
        eight.shutdown();
        assertThat(eight.awaitTermination(5, TimeUnit.MINUTES)).isTrue();

        List<String> messages = refused.stream().map(SQLException::getMessage).distinct().toList();
        assertThat(refused).as("refused: %s", messages).isEmpty();
    }

    @Test
    void aTestConnectionGivesUpOnADeadServerAfterTheReadTimeout() {
        org.assertj.core.api.Assertions.assertThat(OracleDatabaseProvider.readTimeout().getProperty("oracle.jdbc.ReadTimeout"))
                .isEqualTo(Long.toString(OracleDatabaseProvider.DEFAULT_READ_TIMEOUT_MILLIS));
    }

    @Test
    void zeroLeavesTheImagesSetting() {
        // nothing to restart, nothing to wait for: the call returns at once without a container
        OracleDatabaseProvider.configureProcesses(null, 0);
    }
}
