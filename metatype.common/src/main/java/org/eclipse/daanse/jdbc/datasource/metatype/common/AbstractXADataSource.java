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

import java.sql.SQLException;

import javax.sql.XAConnection;
import javax.sql.XADataSource;

public abstract class AbstractXADataSource extends AbstractCommonDataSource<XADataSource> implements XADataSource {

    protected AbstractXADataSource() {
        super();
    }

    @Override
    public XAConnection getXAConnection() throws SQLException {
        return ds().getXAConnection();
    }

    @Override
    public XAConnection getXAConnection(String user, String password) throws SQLException {
        return ds().getXAConnection(user, password);
    }

}
