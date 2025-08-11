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
package org.eclipse.daanse.jdbc.datasource.mariadb;

import static org.assertj.core.api.Assertions.assertThat;

import javax.sql.ConnectionPoolDataSource;
import javax.sql.DataSource;
import javax.sql.XADataSource;

import org.eclipse.daanse.jdbc.datasource.mariadb.api.Constants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.osgi.test.common.annotation.InjectService;
import org.osgi.test.common.annotation.config.WithFactoryConfiguration;
import org.osgi.test.common.service.ServiceAware;
import org.osgi.test.junit5.cm.ConfigurationExtension;
import org.osgi.test.junit5.context.BundleContextExtension;
import org.osgi.test.junit5.service.ServiceExtension;

@ExtendWith(BundleContextExtension.class)
@ExtendWith(ServiceExtension.class)
@ExtendWith(ConfigurationExtension.class)
class MariaDbDataSourceTest {

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
    @WithFactoryConfiguration(factoryPid = Constants.PID_DATASOURCE, name = "1", location = "?")
    @WithFactoryConfiguration(factoryPid = Constants.PID_CONNECTION_POOL_DATASOURCE, name = "1", location = "?")
    @WithFactoryConfiguration(factoryPid = Constants.PID_XA_DATASOURCE, name = "1", location = "?")

    void serviceWithConfigurationTest(@InjectService(timeout = 500) ServiceAware<DataSource> serviceAwareDataSource, //
            @InjectService(timeout = 500) ServiceAware<XADataSource> serviceAwareXaDataSource, //
            @InjectService(timeout = 500) ServiceAware<ConnectionPoolDataSource> serviceAwareCpDataSource)
            throws Exception {

        assertThat(serviceAwareDataSource.getServices()).hasSize(1);
        assertThat(serviceAwareXaDataSource.getServices()).hasSize(1);
        assertThat(serviceAwareCpDataSource.getServices()).hasSize(1);

    }

}
