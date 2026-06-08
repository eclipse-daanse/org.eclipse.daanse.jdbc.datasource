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
package org.eclipse.daanse.jdbc.datasource.h2.impl;

import java.util.Map;

import org.eclipse.daanse.jdbc.datasource.h2.api.Constants;

class UrlBuilder {

    private UrlBuilder() {
    }

    private static final String JDBC_H2 = "jdbc:h2:";

    static String buildUrl(Map<String, Object> map) {

        StringBuilder urlStringBuilder = new StringBuilder(JDBC_H2);
        appendDebug(urlStringBuilder, map);
        appendFileSystem(urlStringBuilder, map);
        // to here is pre identifier. so separated with ":".
        appendIdentifier(urlStringBuilder, map);
        // from here is post identifier. so separated with ";".
        appendDatabaseToUpper(urlStringBuilder, map);
        appendDbCloseDelay(urlStringBuilder, map);
        return urlStringBuilder.toString();
    }

    /**
     * Appends the H2 {@code DB_CLOSE_DELAY} setting, but only when it has been
     * explicitly configured. For in-memory databases {@code DB_CLOSE_DELAY=-1} keeps
     * the database alive for the lifetime of the JVM; without it the content is
     * discarded as soon as the last connection is closed.
     */
    private static void appendDbCloseDelay(StringBuilder urlStringBuilder, Map<String, Object> map) {
        if (map.containsKey(Constants.DATASOURCE_PROPERTY_DB_CLOSE_DELAY)) {
            Object dbCloseDelay = map.get(Constants.DATASOURCE_PROPERTY_DB_CLOSE_DELAY);
            if (dbCloseDelay != null) {
                urlStringBuilder.append(";DB_CLOSE_DELAY=").append(dbCloseDelay);
            }
        }
    }

    private static void appendDatabaseToUpper(StringBuilder urlStringBuilder, Map<String, Object> map) {
        if (map.containsKey(Constants.DATASOURCE_PROPERTY_DATABASE_TO_UPPER)) {
            Boolean databaseToUpper = (Boolean) map.get(Constants.DATASOURCE_PROPERTY_DATABASE_TO_UPPER);
            String val = (databaseToUpper != null && databaseToUpper) ? "TRUE" : "FALSE";
            urlStringBuilder.append(";DATABASE_TO_UPPER=" + val);
        }
    }

    private static void appendIdentifier(StringBuilder urlStringBuilder, Map<String, Object> map) {
        if (map.containsKey(Constants.DATASOURCE_PROPERTY_IDENTIFIER)) {
            String identifier = (String) map.get(Constants.DATASOURCE_PROPERTY_IDENTIFIER);
            if (identifier != null) {
                urlStringBuilder.append(identifier);
            }
        }
    }

    private static void appendFileSystem(StringBuilder urlStringBuilder, Map<String, Object> map) {

        if (map.containsKey(Constants.DATASOURCE_PROPERTY_PLUGABLE_FILESYSTEM)) {
            String plugableFileSystem = (String) map.get(Constants.DATASOURCE_PROPERTY_PLUGABLE_FILESYSTEM);
            if (plugableFileSystem != null) {
                urlStringBuilder.append(plugableFileSystem).append(":");
            }
        }
    }

    private static void appendDebug(StringBuilder urlStringBuilder, Map<String, Object> map) {
        if (map.containsKey(Constants.DATASOURCE_PROPERTY_DEBUG)) {
            Boolean debug = (Boolean) map.get(Constants.DATASOURCE_PROPERTY_DEBUG);
            if (debug != null && debug) {
                urlStringBuilder.append("debug:");
            }
        }
    }
}
