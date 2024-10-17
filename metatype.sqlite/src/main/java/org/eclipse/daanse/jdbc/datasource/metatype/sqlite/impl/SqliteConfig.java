/*
* Copyright (c) 2022 Contributors to the Eclipse Foundation.
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
package org.eclipse.daanse.jdbc.datasource.metatype.sqlite.impl;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.sqlite.JDBC;
import org.sqlite.SQLiteConfig.DateClass;
import org.sqlite.SQLiteConfig.DatePrecision;
import org.sqlite.SQLiteConfig.HexKeyMode;
import org.sqlite.SQLiteConfig.JournalMode;
import org.sqlite.SQLiteConfig.LockingMode;
import org.sqlite.SQLiteConfig.SynchronousMode;
import org.sqlite.SQLiteConfig.TempStore;
import org.sqlite.SQLiteConfig.TransactionMode;
import org.sqlite.SQLiteOpenMode;

@ObjectClassDefinition()
public @interface SqliteConfig {

    @AttributeDefinition(description = "url must start with " + JDBC.PREFIX, required = true)
    String url();

    @AttributeDefinition(description = "username")
    String user();

    /**
     * OSGi Service Component Spec.:
     *
     * Component properties whose names start with full stop are available to the component instance but
     * are not available as service properties of the registered service.
     *
     * A single low line ('_' \u005F) is converted into a full stop ('.' \u002E)
     *
     * @return password
     */
    @AttributeDefinition(description = "password", type = AttributeType.PASSWORD)
    String _password();

    @AttributeDefinition()
    SQLiteOpenMode[] openModeFlags();

    @AttributeDefinition()
    boolean sharedCache();

    @AttributeDefinition()
    boolean loadExtensionEnabled();

    @AttributeDefinition()
    boolean readOnly();

    @AttributeDefinition()
    int cacheSize();

    @AttributeDefinition()
    boolean caseSensitiveLike();

    @AttributeDefinition()
    int defaultCacheSize();

    @AttributeDefinition()
    boolean deferForeignKeys();

    @AttributeDefinition()
    boolean enforceForeignKeys();

    @AttributeDefinition()
    boolean fullSync();

    @AttributeDefinition()
    int incrementalVacuum();

    @AttributeDefinition()
    JournalMode journalMode();

    @AttributeDefinition()
    int jounalSizeLimit();

    @AttributeDefinition()
    boolean legacyFileFormat();

    @AttributeDefinition()
    LockingMode lockingMode();

    @AttributeDefinition()
    int pageSize();

    @AttributeDefinition()
    int maxPageCount();

    @AttributeDefinition()
    boolean readUncommited();

    @AttributeDefinition()
    boolean recursiveTriggers();

    @AttributeDefinition()
    boolean reverseUnorderedSelects();

    @AttributeDefinition()
    boolean shortColumnNames();

    @AttributeDefinition()
    SynchronousMode synchronous();

    @AttributeDefinition()
    HexKeyMode hexKeyMode();

    @AttributeDefinition()
    TempStore tempStore();

    @AttributeDefinition()
    String tempStoreDirectory();

    @AttributeDefinition()
    int userVersion();

    @AttributeDefinition()
    int applicationId();

    @AttributeDefinition()
    TransactionMode transactionMode();

    @AttributeDefinition()
    DatePrecision datePrecision();

    @AttributeDefinition()
    DateClass dateClass();

    @AttributeDefinition()
    String dateStringFormat();

    @AttributeDefinition()
    int busyTimeout();

}
