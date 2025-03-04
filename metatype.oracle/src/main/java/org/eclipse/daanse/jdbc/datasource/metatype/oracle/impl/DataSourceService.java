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
package org.eclipse.daanse.jdbc.datasource.metatype.oracle.impl;

import java.sql.SQLException;
import java.util.Map;

import javax.sql.DataSource;

import org.eclipse.daanse.jdbc.datasource.metatype.common.AbstractDataSource;
import org.eclipse.daanse.jdbc.datasource.metatype.common.annotation.prototype.DataSourceMetaData;
import org.eclipse.daanse.jdbc.datasource.metatype.oracle.api.Constants;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.ServiceScope;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.LoggerFactory;


@Designate(ocd = OracleConfig.class, factory = true)
@Component(service = DataSource.class, scope = ServiceScope.SINGLETON, name = Constants.PID_DATASOURCE,immediate = true)
@DataSourceMetaData(subprotocol = Constants.SUBPROTOCOL)
public class DataSourceService extends AbstractDataSource {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(DataSourceService.class);

    private oracle.jdbc.datasource.impl.OracleDataSource ds;

    @Activate
    public DataSourceService(OracleConfig config, Map<String, Object> configMap) throws SQLException {
        super();
        this.ds = new oracle.jdbc.datasource.impl.OracleDataSource();
        Util.doConfig(ds, config, configMap);
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
