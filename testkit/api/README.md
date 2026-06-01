# Daanse JDBC DataSource Test Kit — API

The pure-API jar of the test-kit. Defines the SPI types only — no
drivers, no containers, no implementations.

## Why it exists

Consumers of the test-kit (cwm test code, rolap test harness, etl
tests, anyone else who wants a `(DataSource, Dialect)` pair) should
not have to pick a database vendor at compile time. They depend on
this API jar and use the runtime SPI to discover whatever
`DatabaseProvider` is on the classpath.

This is the only jar test code ever needs to `import` from. The
concrete provider modules (h2, postgresql, mysql, mssqlserver,
mariadb, oracle) are pulled in only at test-runtime scope.

## Features

| Type | Role |
|------|------|
| `DatabaseProvider` | SPI: `id()` + `activate()`. Static helpers `byId(id)` (case-insensitive lookup) and `selected()` (env-var-driven: `DAANSE_TEST_DB` → `daanse.test.db` sys-prop → `"h2"`). |
| `ActiveDatabase` (record) | The `(DataSource, Dialect)` pair returned by `activate()`. Named accessors `.dataSource()` / `.dialect()`. |

## Typical use

```java
// env-var dispatch (CI matrix)
ActiveDatabase db = DatabaseProvider.selected().activate();

// pin a specific provider by id
ActiveDatabase pg = DatabaseProvider.byId("postgres").activate();

// direct construction (multi-DB parallel)
ActiveDatabase h2 = new H2DatabaseProvider().activate();
```

## Bundle exports

`org.eclipse.daanse.jdbc.datasource.testkit.api;version="1.0"` —
single `@Export`ed package. No internal/impl sub-package.

## Dependencies

- `org.eclipse.daanse.jdbc.db.dialect.api` — for the `Dialect` type
  referenced by `ActiveDatabase`.

No driver deps, no Testcontainers dep, no `cwm.*`. This is the lowest
common denominator that any test-only consumer should be able to pull
in without dragging vendor jars.
