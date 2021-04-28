package io.archimedesfw.data.jdbc

import io.archimedesfw.data.GeneratedKeysHolder

interface JdbcTemplate {

    fun update(sql: String, pss: PreparedStatementSetter): Int

    fun update(sql: String, keysHolder: GeneratedKeysHolder, pss: PreparedStatementSetter): Int

    fun batchUpdate(sql: String, bpss: BatchPreparedStatementSetter): IntArray

    fun batchUpdate(sql: String, keysHolder: GeneratedKeysHolder, bpss: BatchPreparedStatementSetter): IntArray

    fun <T> query(sql: String, pss: PreparedStatementSetter, rse: ResultSetExtractor<T>): T

    fun <T> query(sql: String, pss: PreparedStatementSetter, rowMapper: RowMapper<T>): List<T> =
        query(sql, pss, RowMapperResultSetExtractor(rowMapper))

}
