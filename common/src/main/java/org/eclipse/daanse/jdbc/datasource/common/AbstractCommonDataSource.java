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

import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

import javax.sql.CommonDataSource;

public abstract class AbstractCommonDataSource<T extends CommonDataSource> {

    protected AbstractCommonDataSource() {
        super();
    }

    protected abstract T ds();

    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return ds().getParentLogger();
    }

    public PrintWriter getLogWriter() throws SQLException {
        return ds().getLogWriter();
    }

    public void setLogWriter(PrintWriter out) throws SQLException {
        ds().setLogWriter(out);
    }

    public void setLoginTimeout(int seconds) throws SQLException {
        ds().setLoginTimeout(seconds);
    }

    public int getLoginTimeout() throws SQLException {
        return ds().getLoginTimeout();
    }

}
