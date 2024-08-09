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
package org.eclipse.daanse.jdbc.datasource.metatype.common;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

public abstract class AbstractDataSource extends AbstractCommonDataSource<DataSource> implements DataSource {

    protected AbstractDataSource() {
        super();
    }

    @Override
    public Connection getConnection() throws SQLException {
        return ds().getConnection();
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return ds().getConnection(username, password);
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return ds().unwrap(iface);
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return ds().isWrapperFor(iface);
    }

}
