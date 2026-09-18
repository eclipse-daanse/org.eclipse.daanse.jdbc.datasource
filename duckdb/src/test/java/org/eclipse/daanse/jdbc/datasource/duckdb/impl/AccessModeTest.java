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
package org.eclipse.daanse.jdbc.datasource.duckdb.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import org.eclipse.daanse.jdbc.datasource.duckdb.api.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class AccessModeTest {

    private static final String ACCESS_MODE_READ_ONLY = "access_mode=READ_ONLY";

    @TempDir
    Path dir;

    private String file;

    @BeforeEach
    void createDatabase() throws SQLException {
        file = dir.resolve("test.duckdb").toString();
        DuckDbDataSource ds = Util.createDataSource(Map.of(Constants.DATASOURCE_PROPERTY_DATABASENAME, file));
        try (Connection connection = ds.getConnection(); Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE t (i INTEGER)");
        } finally {
            ds.close();
        }
    }

    @Test
    void accessModeSettingOpensReadOnly() throws SQLException {
        assertReadOnly(Map.of(Constants.DATASOURCE_PROPERTY_DATABASENAME, file, Constants.DATASOURCE_PROPERTY_SETTINGS,
                new String[] { ACCESS_MODE_READ_ONLY }));
    }

    @Test
    void accessModeAttributeOpensReadOnly() throws SQLException {
        assertReadOnly(Map.of(Constants.DATASOURCE_PROPERTY_DATABASENAME, file,
                Constants.DATASOURCE_PROPERTY_ACCESS_MODE, Constants.ACCESS_MODE_READ_ONLY));
    }

    @Test
    void accessModeAttributeWinsOverSetting() throws SQLException {
        assertReadOnly(Map.of(Constants.DATASOURCE_PROPERTY_DATABASENAME, file,
                Constants.DATASOURCE_PROPERTY_ACCESS_MODE, Constants.ACCESS_MODE_READ_ONLY,
                Constants.DATASOURCE_PROPERTY_SETTINGS, new String[] { "access_mode=READ_WRITE" }));
    }

    @Test
    void automaticAccessModeAgreesWithReadOnlyFlag() throws SQLException {
        assertReadOnly(Map.of(Constants.DATASOURCE_PROPERTY_DATABASENAME, file,
                Constants.DATASOURCE_PROPERTY_READ_ONLY, true, Constants.DATASOURCE_PROPERTY_ACCESS_MODE,
                Constants.ACCESS_MODE_AUTOMATIC));
    }

    @Test
    void accessModeSettingAgreesWithReadOnlyFlag() throws SQLException {
        assertReadOnly(Map.of(Constants.DATASOURCE_PROPERTY_DATABASENAME, file, Constants.DATASOURCE_PROPERTY_READ_ONLY,
                true, Constants.DATASOURCE_PROPERTY_SETTINGS, new String[] { ACCESS_MODE_READ_ONLY }));
    }

    @Test
    void contradictingAccessModeIsRefused() {
        DuckDbDataSource ds = Util.createDataSource(
                Map.of(Constants.DATASOURCE_PROPERTY_DATABASENAME, file, Constants.DATASOURCE_PROPERTY_READ_ONLY, true,
                        Constants.DATASOURCE_PROPERTY_SETTINGS, new String[] { "access_mode=READ_WRITE" }));
        assertThatThrownBy(ds::getConnection).isInstanceOf(SQLException.class).hasMessageContaining("access_mode");
    }

    private static void assertReadOnly(Map<String, Object> config) throws SQLException {
        DuckDbDataSource ds = Util.createDataSource(config);
        try (Connection connection = ds.getConnection(); Statement statement = connection.createStatement()) {
            assertThat(connection.isReadOnly()).isTrue();
            assertThat(statement.executeQuery("SELECT count(*) FROM t").next()).isTrue();
            assertThatThrownBy(() -> statement.execute("INSERT INTO t VALUES (1)")).isInstanceOf(SQLException.class);
        } finally {
            ds.close();
        }
    }
}
