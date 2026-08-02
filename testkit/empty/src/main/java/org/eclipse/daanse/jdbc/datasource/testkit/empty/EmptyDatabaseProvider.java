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
 *   SmartCity Jena, Stefan Bischof - initial
 */
package org.eclipse.daanse.jdbc.datasource.testkit.empty;

import org.eclipse.daanse.jdbc.datasource.testkit.api.ActiveDatabase;
import org.eclipse.daanse.jdbc.datasource.testkit.api.DatabaseProvider;

/**
 * A provider without a database.
 * <p>
 * Selected like any other by id, it lets a suite run the cases that never touch
 * JDBC without paying for a container or an embedded engine. {@link #activate()}
 * returns {@code null} rather than an empty {@link ActiveDatabase}: there is no
 * DataSource to hand out, and a caller that does reach for one should fail
 * loudly instead of on a stub that pretends to work.
 */
public class EmptyDatabaseProvider implements DatabaseProvider {

    @Override
    public String id() {
        return "empty";
    }

    @Override
    public ActiveDatabase activate() {
        return null;
    }
}
