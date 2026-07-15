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
package org.eclipse.daanse.jdbc.datasource.testkit.api;

import javax.sql.DataSource;

import org.eclipse.daanse.sql.dialect.api.Dialect;

/**
 * Pair of a live {@link DataSource} and the matching {@link Dialect}.
 */
public record ActiveDatabase(DataSource dataSource, Dialect dialect) {
}
