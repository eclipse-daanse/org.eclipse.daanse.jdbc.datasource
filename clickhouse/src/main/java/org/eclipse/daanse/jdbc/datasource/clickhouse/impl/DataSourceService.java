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
package org.eclipse.daanse.jdbc.datasource.clickhouse.impl;

import java.sql.SQLException;
import java.util.Map;

import javax.sql.DataSource;

import org.eclipse.daanse.jdbc.datasource.clickhouse.api.Constants;
import org.eclipse.daanse.jdbc.datasource.clickhouse.api.ocd.DsConfig;
import org.eclipse.daanse.jdbc.datasource.common.AbstractDataSource;
import org.eclipse.daanse.jdbc.datasource.common.DataSourceCommonUtils;
import org.eclipse.daanse.jdbc.datasource.common.annotation.prototype.DataSourceMetaData;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.ServiceScope;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.LoggerFactory;

import com.clickhouse.jdbc.DataSourceImpl;

@Designate(ocd = DsConfig.class, factory = true)
@Component(service = DataSource.class, scope = ServiceScope.SINGLETON, configurationPid = Constants.PID_DATASOURCE, immediate = true)
@DataSourceMetaData(subprotocol = Constants.SUBPROTOCOL)
public class DataSourceService extends AbstractDataSource {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(DataSourceService.class);

    private DataSourceImpl ds;

    @Activate
    public DataSourceService(Map<String, Object> configMap) throws SQLException {
        super();
        try {
            this.ds = Util.createDataSource(configMap);
        } catch (Exception e) {
            Map<String, Object> safeConfigMap = DataSourceCommonUtils.createSafeConfigMapForLogging(configMap);
            LOGGER.error("Failed to configure ClickHouse DataSource with config: {}", safeConfigMap, e);
            throw new SQLException(e);
        }
    }

    // no @Modified to force consumed Services get new configured connections.

    @Deactivate
    public void deactivate() {
        LOGGER.debug("deactivated");
    }

    @Override
    protected DataSource ds() {
        return ds;
    }

}
