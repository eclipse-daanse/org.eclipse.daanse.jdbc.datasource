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
*   Stefan Bischof (bipolis.org) - initial
*/
package org.eclipse.daanse.jdbc.datasource.pools.hikari.api;

import org.osgi.framework.Bundle;

/**
 * Constants of this {@link Bundle}.
 */
public class Constants {

    private Constants() {
    }

    /** Value of the shared pool-kind service property. */
    public static final String KIND = "hikari";

    /**
     * Factory PID. One configuration is one pool; several configurations over the
     * same DataSource give several independent pools.
     */
    public static final String PID_CONNECTION_POOL = "daanse.jdbc.datasource.pools.hikari.ConnectionPool";
}
