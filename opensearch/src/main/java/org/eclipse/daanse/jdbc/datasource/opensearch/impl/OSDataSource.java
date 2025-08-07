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
package org.eclipse.daanse.jdbc.datasource.opensearch.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.eclipse.daanse.jdbc.datasource.common.AbstractCommonDataSource;
import org.eclipse.daanse.jdbc.datasource.common.DataSourceCommonUtils;
import org.eclipse.daanse.jdbc.datasource.common.annotation.prototype.DataSourceMetaData;
import org.eclipse.daanse.jdbc.datasource.opensearch.api.Constants;
import org.eclipse.daanse.jdbc.datasource.opensearch.api.ocd.DsConfig;
import org.opensearch.jdbc.OpenSearchDataSource;
import org.opensearch.jdbc.config.AuthConnectionProperty;
import org.opensearch.jdbc.config.HostConnectionProperty;
import org.opensearch.jdbc.config.PasswordConnectionProperty;
import org.opensearch.jdbc.config.PathConnectionProperty;
import org.opensearch.jdbc.config.PortConnectionProperty;
import org.opensearch.jdbc.config.TrustSelfSignedConnectionProperty;
import org.opensearch.jdbc.config.UseSSLConnectionProperty;
import org.opensearch.jdbc.config.UserConnectionProperty;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.ServiceScope;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.LoggerFactory;

@Designate(ocd = DsConfig.class, factory = true)
@Component(service = {
        DataSource.class }, scope = ServiceScope.SINGLETON, configurationPid = Constants.PID_DATASOURCE, immediate = true)
@DataSourceMetaData(subprotocol = Constants.SUBPROTOCOL)
public class OSDataSource extends AbstractCommonDataSource<OpenSearchDataSource> implements DataSource {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(OSDataSource.class);

    private final OpenSearchDataSource ds = new OpenSearchDataSource();

    @Activate
    public OSDataSource(Map<String, Object> map) throws SQLException {

        try {
            Properties properties = new Properties();

            if (map.containsKey(Constants.DATASOURCE_PROPERTY_HOST)) {
                properties.put(HostConnectionProperty.KEY, (String) map.get(Constants.DATASOURCE_PROPERTY_HOST));
            }

            if (map.containsKey(Constants.DATASOURCE_PROPERTY_PORT)) {
                Integer port = (Integer) map.get(Constants.DATASOURCE_PROPERTY_PORT);
                if (port != null) {
                    properties.put(PortConnectionProperty.KEY, port + "");
                }
            }

            if (map.containsKey(Constants.DATASOURCE_PROPERTY_PATH)) {
                properties.put(PathConnectionProperty.KEY, (String) map.get(Constants.DATASOURCE_PROPERTY_PATH));
            }

            if (map.containsKey(Constants.DATASOURCE_PROPERTY_USESSL)) {
                Boolean usessl = (Boolean) map.get(Constants.DATASOURCE_PROPERTY_USESSL);
                if (usessl != null) {
                    properties.put(UseSSLConnectionProperty.KEY, usessl + "");
                }
            }

            if (map.containsKey(Constants.DATASOURCE_PROPERTY_PASSWORD)) {
                properties.put(PasswordConnectionProperty.KEY,
                        (String) map.get(Constants.DATASOURCE_PROPERTY_PASSWORD));
            }
            if (map.containsKey(Constants.DATASOURCE_PROPERTY_USERNAME)) {
                properties.put(UserConnectionProperty.KEY, (String) map.get(Constants.DATASOURCE_PROPERTY_USERNAME));
            }

            if (map.containsKey(Constants.DATASOURCE_PROPERTY_TRUSTSELFSIGNED)) {
                Boolean trustSelfSigned = (Boolean) map.get(Constants.DATASOURCE_PROPERTY_TRUSTSELFSIGNED);
                if (trustSelfSigned != null) {
                    properties.put(TrustSelfSignedConnectionProperty.KEY, trustSelfSigned + "");
                }
            }

            if (map.containsKey(Constants.DATASOURCE_PROPERTY_AUTH)) {
                String auth = (String) map.get(Constants.DATASOURCE_PROPERTY_AUTH);
                if (auth != null) {
                    properties.put(AuthConnectionProperty.KEY, auth);
                }
            }

//        properties.put(AuthConnectionProperty.KEY, "");
//        properties.put(AwsCredentialsProviderProperty.KEY, "");
//        properties.put(FetchSizeProperty.KEY, "");
//        properties.put(HostnameVerificationConnectionProperty.KEY, "");
//        properties.put(KeyStoreLocationConnectionProperty.KEY, "");
//        properties.put(KeyStorePasswordConnectionProperty.KEY, "");
//        properties.put(KeyStoreTypeConnectionProperty.KEY, "");
//        properties.put(LoginTimeoutConnectionProperty.KEY, "");
//        properties.put(LogLevelConnectionProperty.KEY, "");
//        properties.put(LogOutputConnectionProperty.KEY, "");
//        properties.put(RegionConnectionProperty.KEY, "");
//        properties.put(RequestCompressionConnectionProperty.KEY, "");
//        properties.put(TrustStoreLocationConnectionProperty.KEY, "");
//        properties.put(TrustStorePasswordConnectionProperty.KEY, "");
//        properties.put(TrustStoreTypeConnectionProperty.KEY, "");
//        properties.put(TunnelHostConnectionProperty.KEY, "");
//

            ds.setProperties(properties);
        } catch (SQLException e) {
            Map<String, Object> safeConfigMap = DataSourceCommonUtils.createSafeConfigMapForLogging(map);
            LOGGER.error("Failed to configure OpenSearch DataSource with config: {}", safeConfigMap, e);
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
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return ds.isWrapperFor(iface);
    }

    @Override
    protected OpenSearchDataSource ds() {
        return ds;
    }

}
