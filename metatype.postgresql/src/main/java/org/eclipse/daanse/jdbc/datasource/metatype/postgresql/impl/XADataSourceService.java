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

import java.sql.SQLException;
import java.util.Map;

import javax.sql.CommonDataSource;
import javax.sql.XAConnection;
import javax.sql.XADataSource;

import org.eclipse.daanse.jdbc.datasource.metatype.postgresql.api.Constants;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.ServiceScope;
import org.osgi.service.metatype.annotations.Designate;
import org.postgresql.xa.PGXADataSource;
import org.slf4j.LoggerFactory;

@Designate(ocd = PostgresConfig.class, factory = true)
@Component(service = XADataSource.class, scope = ServiceScope.SINGLETON, name = Constants.PID_DATASOURCE_XA)
public class XADataSourceService extends AbstractDatasource implements XADataSource {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(XADataSourceService.class);

    private PGXADataSource ds;

    @Activate
    public XADataSourceService(PostgresConfig config, Map<String, Object> configMap) {
        this.ds = new PGXADataSource();
        Util.doConfig(ds, config, configMap);
    }

    // no @Modified to force consumed Services get new configured connections.
    @Deactivate
    public void deactivate() {
        LOGGER.debug("deactivated");
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
    CommonDataSource ds() {
        return ds;
    }

}
