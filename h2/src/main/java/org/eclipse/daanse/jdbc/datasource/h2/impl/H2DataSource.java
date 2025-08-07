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
package org.eclipse.daanse.jdbc.datasource.h2.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.sql.ConnectionPoolDataSource;
import javax.sql.DataSource;
import javax.sql.PooledConnection;
import javax.sql.XAConnection;
import javax.sql.XADataSource;

import org.eclipse.daanse.jdbc.datasource.common.AbstractCommonDataSource;
import org.eclipse.daanse.jdbc.datasource.common.DataSourceCommonUtils;
import org.eclipse.daanse.jdbc.datasource.common.annotation.prototype.DataSourceMetaData;
import org.eclipse.daanse.jdbc.datasource.h2.api.Constants;
import org.eclipse.daanse.jdbc.datasource.h2.api.ocd.DsConfig;
import org.h2.jdbcx.JdbcDataSource;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.ServiceScope;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.LoggerFactory;

@Designate(ocd = DsConfig.class, factory = true)
@Component( service = { DataSource.class, XADataSource.class,
        ConnectionPoolDataSource.class }, scope = ServiceScope.SINGLETON, configurationPid = Constants.PID_DATASOURCE)
@DataSourceMetaData(subprotocol = Constants.SUBPROTOCOL)
public class H2DataSource extends AbstractCommonDataSource<JdbcDataSource>
        implements ConnectionPoolDataSource, DataSource, XADataSource {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(H2DataSource.class);

    private final JdbcDataSource ds = new JdbcDataSource();

    @Activate
    public H2DataSource(Map<String, Object> map) {

        try {
            String url = UrlBuilder.buildUrl(map);

            LOGGER.debug("composed url: {}", url);

            ds.setURL(url);

            if (map.containsKey(Constants.DATASOURCE_PROPERTY_USERNAME)) {
                ds.setUser((String) map.get(Constants.DATASOURCE_PROPERTY_USERNAME));
            }

            if (map.containsKey(Constants.DATASOURCE_PROPERTY_PASSWORD)) {
                ds.setPassword((String) map.get(Constants.DATASOURCE_PROPERTY_PASSWORD));
            }

            if (map.containsKey(Constants.DATASOURCE_PROPERTY_DESCRIPTION)) {
                ds.setDescription((String) map.get(Constants.DATASOURCE_PROPERTY_DESCRIPTION));
            }
        } catch (Exception e) {
            Map<String, Object> safeConfigMap = DataSourceCommonUtils.createSafeConfigMapForLogging(map);
            LOGGER.error("Failed to configure H2 DataSource with config: {}", safeConfigMap, e);
            throw e;
        }

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
    public PooledConnection getPooledConnection() throws SQLException {
        return ds.getPooledConnection();
    }

    @Override
    public PooledConnection getPooledConnection(String user, String password) throws SQLException {
        return ds.getPooledConnection(user, password);
    }

    @Override
    public XAConnection getXAConnection() throws SQLException {
        return ds.getXAConnection();
    }

    @Override
    public XAConnection getXAConnection(String user, String password) throws SQLException {
        return ds.getXAConnection(user, password);
    }

    @Override
    protected JdbcDataSource ds() {
        return ds;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return ds.isWrapperFor(iface);
    }

}
