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
package org.eclipse.daanse.jdbc.datasource.metatype.opensearch;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Connection;
import java.sql.ResultSet;

import javax.sql.ConnectionPoolDataSource;
import javax.sql.DataSource;
import javax.sql.XADataSource;

import org.eclipse.daanse.jdbc.datasource.metatype.opensearch.api.Constants;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.osgi.test.common.annotation.InjectService;
import org.osgi.test.common.annotation.Property;
import org.osgi.test.common.annotation.Property.TemplateArgument;
import org.osgi.test.common.annotation.Property.ValueSource;
import org.osgi.test.common.annotation.config.WithFactoryConfiguration;
import org.osgi.test.common.service.ServiceAware;
import org.osgi.test.junit5.cm.ConfigurationExtension;
import org.osgi.test.junit5.context.BundleContextExtension;
import org.osgi.test.junit5.service.ServiceExtension;

@ExtendWith(BundleContextExtension.class)
@ExtendWith(ServiceExtension.class)
@ExtendWith(ConfigurationExtension.class)
class OSDataSourceTest {

    @Test
    void noConfigurationNoServiceTest(
            @InjectService(cardinality = 0, timeout = 500) ServiceAware<DataSource> saDataSource, //
            @InjectService(cardinality = 0, timeout = 500) ServiceAware<XADataSource> saXaDataSource, //
            @InjectService(cardinality = 0, timeout = 500) ServiceAware<ConnectionPoolDataSource> saCpDataSource)
            throws Exception {

        assertThat(saDataSource.getServices()).isEmpty();
        assertThat(saXaDataSource.getServices()).isEmpty();
        assertThat(saCpDataSource.getServices()).isEmpty();

    }

    @Test
    @WithFactoryConfiguration(factoryPid = org.eclipse.daanse.jdbc.datasource.metatype.opensearch.api.Constants.PID_DATASOURCE, name = "1", location = "?", properties = @Property(key = Constants.DATASOURCE_PROPERTY_HOST, value = "localhost"))
    void serviceWithConfigurationTest(@InjectService(timeout = 5000) ServiceAware<DataSource> serviceAwareDataSource) //
            throws Exception {

        assertThat(serviceAwareDataSource.getServices()).hasSize(1);

    }

    @Disabled
    @Test
    @WithFactoryConfiguration(factoryPid = org.eclipse.daanse.jdbc.datasource.metatype.opensearch.api.Constants.PID_DATASOURCE, name = "1", location = "?", properties = {
            @Property(key = Constants.DATASOURCE_PROPERTY_HOST, value = "localhost"),
            @Property(key = Constants.DATASOURCE_PROPERTY_PORT, value = "9200"),
            @Property(key = Constants.DATASOURCE_PROPERTY_USERNAME, value = "admin"),
            @Property(key = Constants.DATASOURCE_PROPERTY_PASSWORD, value = "%s", templateArguments = @TemplateArgument(source = ValueSource.SystemProperty, value = "OPENSEARCH_INITIAL_ADMIN_PASSWORD")), // set
                                                                                                                                                                                                            // in
                                                                                                                                                                                                            // bndrun
            @Property(key = Constants.DATASOURCE_PROPERTY_AUTH, value = "BASIC"),
            @Property(key = Constants.DATASOURCE_PROPERTY_TRUSTSELFSIGNED, value = "true"),
            @Property(key = Constants.DATASOURCE_PROPERTY_USESSL, value = "true") })
    void test(@InjectService(timeout = 500000) DataSource dataSource) //
            throws Exception {

        Connection connection = dataSource.getConnection();
        java.sql.Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM  opensearch_dashboards_sample_data_ecommerce");

        boolean b = false;
        while (rs.next()) {
            String s = rs.getString(1);
            System.out.println(s);
            b = true;
        }

        assertThat(b).isTrue();

    }
}
