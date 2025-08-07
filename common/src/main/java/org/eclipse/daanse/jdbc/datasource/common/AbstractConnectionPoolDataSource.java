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
package org.eclipse.daanse.jdbc.datasource.common;

import java.sql.SQLException;

import javax.sql.ConnectionPoolDataSource;
import javax.sql.PooledConnection;

public abstract class AbstractConnectionPoolDataSource extends AbstractCommonDataSource<ConnectionPoolDataSource>
        implements ConnectionPoolDataSource {

    protected AbstractConnectionPoolDataSource() {
        super();
    }

    @Override
    public PooledConnection getPooledConnection() throws SQLException {
        return ds().getPooledConnection();
    }

    @Override
    public PooledConnection getPooledConnection(String user, String password) throws SQLException {
        return ds().getPooledConnection(user, password);
    }

}
