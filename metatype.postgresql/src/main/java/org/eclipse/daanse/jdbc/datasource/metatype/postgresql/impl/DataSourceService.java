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
package org.eclipse.daanse.jdbc.datasource.metatype.postgresql.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.sql.DataSource;

import org.eclipse.daanse.jdbc.datasource.metatype.postgresql.api.Constants;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.ServiceScope;
import org.osgi.service.metatype.annotations.Designate;
import org.postgresql.ds.PGSimpleDataSource;
import org.slf4j.LoggerFactory;

@Designate(ocd = PostgresConfig.class, factory = true)
@Component(service = DataSource.class, scope = ServiceScope.SINGLETON, name = Constants.PID_DATASOURCE)
public class DataSourceService extends AbstractDatasource implements DataSource {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(DataSourceService.class);

    private PGSimpleDataSource ds;

    @Activate
    public DataSourceService(PostgresConfig config, Map<String, Object> configMap) {
        super();
        this.ds = new PGSimpleDataSource();
        Util.doConfig(ds, config, configMap);
    }

    // no @Modified to force consumed Services get new configured connections.

    @Deactivate
    public void deactivate() {
        LOGGER.debug("deactivated");
    }

    @Override
    public Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return ds.getConnection(username, password);
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return ds.unwrap(iface);
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return ds.isWrapperFor(iface);
    }

    @Override
    PGSimpleDataSource ds() {
        return ds;
    }

}
