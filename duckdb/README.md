# DuckDB DataSource

OSGi `javax.sql.DataSource` services for [DuckDB](https://duckdb.org) (`org.duckdb:duckdb_jdbc` 2.0.0.1), subprotocol `duckdb`.
Two factory configurations:

| PID | OCD | What |
|---|---|---|
| `daanse.jdbc.datasource.duckdb.DataSource` | `DsConfig` | Embedded database, file or in-memory |
| `daanse.jdbc.datasource.duckdb.RemoteDataSource` | `RemoteDsConfig` | Client of a remote DuckDB server (Quack protocol) |

The DataSource opens one connection, keeps it for its lifetime and hands out `duplicate()`s of it, so all connections share one database, an in-memory one included.
Connections are plain `DuckDBConnection`s: `unwrap(DuckDBConnection.class)` gives access to the appender and Java table functions.

## Attributes

Both PIDs: `settings` (engine settings, `name=value`), `customUserAgent`, `streamResults`, `autoCommit`, `ignoreUnsupportedOptions`, `jfrMemoryMonitor`.

Embedded: `databaseName` (path; empty or `:memory:` for in-memory, `memory:<label>`, `ducklake:<path>`), `readOnly`, `accessMode`, `pinDb`, `instanceCache`, `sessionInitSqlFile`, `sessionInitSqlFileSha256`.

Remote: `remoteUri` (required), `.remoteToken`, `remoteAlias` (default `remote`), `remoteDisableSsl`.

```json
{
  ":configurator:resource-version": 1,
  "daanse.jdbc.datasource.duckdb.DataSource~foodmart": {
    "databaseName": "/data/foodmart.duckdb",
    "settings": ["threads=4", "memory_limit=4GB"]
  },
  "daanse.jdbc.datasource.duckdb.RemoteDataSource~remote": {
    "remoteUri": "quack:db.example.org:9494",
    ".remoteToken": "..."
  }
}
```

## Remote (Quack)

The server is started with `CALL quack_serve('quack:0.0.0.0:9494', token = '...', allow_other_hostname = true)`.
The client attaches it to a private in-memory database and makes it the default catalog of every connection; the token goes into a temporary secret, not into `duckdb_databases()`.

- No `?` parameters on remote tables: DuckDB prepares them on the server without the values. Use literal SQL, or load a local temporary table and `INSERT INTO <table> SELECT ...` from it.
- No read-only option: Quack does not enforce `READ_ONLY` on an attach.
- The client caches the remote catalog: a table created on the server by someone else after the attach shows up after `CALL quack_clear_cache()`.
- Plain HTTP to a non-local host needs `remoteDisableSsl=true`; client settings such as `quack_enable_reconnects` go into `settings`.

## Loading data

Fastest first, 200k rows (int, varchar, double), embedded:

| Way | Time | Remote |
|---|---|---|
| `INSERT ... SELECT * FROM read_csv(...)` (file) | ~35 ms | yes, the client reads the file |
| Java table function, `DuckDBWritableVector.setStrings` | ~30 ms | yes, streams to the server |
| `DuckDBAppender` | ~110 ms | via a local temp table |
| Standard JDBC: one `java.sql.Array` per column, `INSERT ... SELECT unnest(?::INTEGER[]), ...` | ~240 ms | via a local temp table |
| Standard JDBC: multi-row `VALUES (?,?,?),...`, 1000 rows per statement | ~670 ms | via a local temp table |
| `addBatch` / `executeBatch` | ~22 s | no |

The driver runs `executeBatch` as one statement per row and has no batch rewrite option.

## Tests

`QuackRemoteTest` starts a Quack server in the test JVM; it is skipped when the `quack` extension cannot be installed (no network).
