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
package org.eclipse.daanse.jdbc.datasource.clickhouse.integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.Duration;
import java.util.Hashtable;

import javax.sql.DataSource;

import org.eclipse.daanse.jdbc.datasource.clickhouse.api.Constants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.osgi.service.cm.Configuration;
import org.osgi.test.common.annotation.InjectService;
import org.osgi.test.common.annotation.config.InjectConfiguration;
import org.osgi.test.common.annotation.config.WithFactoryConfiguration;
import org.osgi.test.common.service.ServiceAware;
import org.osgi.test.junit5.cm.ConfigurationExtension;
import org.osgi.test.junit5.context.BundleContextExtension;
import org.osgi.test.junit5.service.ServiceExtension;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.HttpWaitStrategy;
import org.testcontainers.containers.wait.strategy.WaitStrategy;

@ExtendWith(BundleContextExtension.class)
@ExtendWith(ServiceExtension.class)
@ExtendWith(ConfigurationExtension.class)
class ClickHouseDataSourceTest {

    private static final String USER = "daanse";
    private static final String PW = "fdf2FW?WW93?!";
    private static final String LOCALHOST = "localhost";
    private static final int PORT_HTTP_8123 = 8123;
    private static final String TABLE = "engine_defaulted";

    @Test
    void noConfigurationNoServiceTest(
            @InjectService(cardinality = 0, timeout = 500) ServiceAware<DataSource> saDataSource) throws Exception {

        assertThat(saDataSource.getServices()).isEmpty();

    }

    @Test
    void serviceWithConfigurationAgainstContainerTest(
            @InjectConfiguration(withFactoryConfig = @WithFactoryConfiguration(factoryPid = Constants.PID_DATASOURCE, name = "1", location = "?")) Configuration configuration,
            @InjectService(cardinality = 0) ServiceAware<DataSource> saDataSource) //
            throws Exception {

        try (GenericContainer<?> container = new GenericContainer<>("clickhouse/clickhouse-server:latest")) {

            container.withEnv("CLICKHOUSE_USER", USER)//
                    .withEnv("CLICKHOUSE_PASSWORD", PW)//
                    .withExposedPorts(PORT_HTTP_8123);

            WaitStrategy waitStrategy = new HttpWaitStrategy().forPort(PORT_HTTP_8123).forPath("/ping")
                    .forStatusCode(200).withReadTimeout(Duration.ofSeconds(30))
                    .withStartupTimeout(Duration.ofMinutes(5));
            container.setWaitStrategy(waitStrategy);

            container.start();

            int portHttp = container.getMappedPort(PORT_HTTP_8123);
            System.out.println("8123=" + portHttp);

            Hashtable<String, Object> ht = new Hashtable<>();
            ht.put(Constants.DATASOURCE_PROPERTY_SERVERNAME, LOCALHOST);
            ht.put(Constants.DATASOURCE_PROPERTY_PORTNUMBER, portHttp);
            ht.put(Constants.DATASOURCE_PROPERTY_USER, USER);
            ht.put(Constants.DATASOURCE_PROPERTY_PASSWORD, PW);
            ht.put(Constants.DATASOURCE_PROPERTY_CUSTOM_SETTINGS, "default_table_engine=MergeTree");
            configuration.update(ht);

            DataSource dataSource = saDataSource.waitForService(15000);
            assertThat(dataSource).isNotNull();
            assertThat(saDataSource.getServices()).hasSize(1);
            System.out.println("DataSource service appeared: " + dataSource);

            try (Connection connection = dataSource.getConnection(); //
                    Statement statement = connection.createStatement()) {

                // 1) plain query through the embedded driver inside OSGi
                try (ResultSet rs = statement.executeQuery("SELECT 1")) {
                    assertThat(rs.next()).isTrue();
                    int one = rs.getInt(1);
                    System.out.println("SELECT 1 = " + one);
                    assertThat(one).isEqualTo(1);
                }

                // 2) the custom_settings pair must land as a session setting
                try (ResultSet rs = statement.executeQuery(
                        "SELECT value, changed FROM system.settings WHERE name = 'default_table_engine'")) {
                    assertThat(rs.next()).isTrue();
                    String value = rs.getString(1);
                    System.out.println("default_table_engine = " + value + " (changed=" + rs.getString(2) + ")");
                    assertThat(value).isEqualTo("MergeTree");
                }

                // 3) CREATE TABLE without ENGINE clause relies on the
                // default_table_engine handed over via custom_settings
                statement.execute("CREATE TABLE " + TABLE + " (id Int32) ORDER BY id");
                try (ResultSet rs = statement.executeQuery("SHOW CREATE TABLE " + TABLE)) {
                    assertThat(rs.next()).isTrue();
                    String ddl = rs.getString(1);
                    System.out.println("SHOW CREATE TABLE " + TABLE + " -> " + ddl);
                    assertThat(ddl).contains("ENGINE = MergeTree");
                }
            }

            container.stop();
        }
    }

}
