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
package org.eclipse.daanse.jdbc.datasource.mysql.impl;

import java.sql.SQLException;
import java.util.Map;

import javax.sql.ConnectionPoolDataSource;

import org.eclipse.daanse.jdbc.datasource.common.AbstractConnectionPoolDataSource;
import org.eclipse.daanse.jdbc.datasource.common.DataSourceCommonUtils;
import org.eclipse.daanse.jdbc.datasource.common.annotation.prototype.DataSourceMetaData;
import org.eclipse.daanse.jdbc.datasource.mysql.api.Constants;
import org.eclipse.daanse.jdbc.datasource.mysql.api.ocd.CpDsConfig;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.ServiceScope;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.LoggerFactory;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;

@Designate(ocd = CpDsConfig.class, factory = true)
@Component(service = ConnectionPoolDataSource.class, scope = ServiceScope.SINGLETON, configurationPid = Constants.PID_DATASOURCE_CP)
@DataSourceMetaData(subprotocol = Constants.SUBPROTOCOL)
public class ConnectionPoolDataSourceService extends AbstractConnectionPoolDataSource
        implements ConnectionPoolDataSource {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ConnectionPoolDataSourceService.class);

    private MysqlConnectionPoolDataSource ds;

    @Activate
    public ConnectionPoolDataSourceService(Map<String, Object> configMap) throws SQLException {
        this.ds = new MysqlConnectionPoolDataSource();

        try {
            Util.doConfig(ds, configMap);
        } catch (SQLException e) {
            Map<String, Object> safeConfigMap = DataSourceCommonUtils.createSafeConfigMapForLogging(configMap);
            LOGGER.error("Failed to configure MySQL ConnectionPoolDataSource with config: {}", safeConfigMap, e);
            throw e;
        }

    }

    // no @Modified to force consumed Services get new configured connections.
    @Deactivate
    public void deactivate() {
        LOGGER.debug("deactivated");
    }

    @Override
    protected ConnectionPoolDataSource ds() {
        return ds;
    }

}
