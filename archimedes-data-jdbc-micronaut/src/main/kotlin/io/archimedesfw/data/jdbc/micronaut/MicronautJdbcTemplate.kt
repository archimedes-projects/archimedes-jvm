package io.archimedesfw.data.jdbc.micronaut

import io.archimedesfw.data.GeneratedKeysHolder
import io.archimedesfw.data.jdbc.BatchPreparedStatementSetter
import io.archimedesfw.data.jdbc.JdbcTemplate
import io.archimedesfw.data.jdbc.PreparedStatementSetter
import io.archimedesfw.data.jdbc.ResultSetExtractor
import io.archimedesfw.data.jdbc.extractGeneratedKeys
import io.micronaut.data.jdbc.runtime.JdbcOperations
import java.sql.PreparedStatement
import javax.inject.Singleton

@Singleton
class MicronautJdbcTemplate(
    val jdbcOperations: JdbcOperations
) : JdbcTemplate {

    override fun update(sql: String, pss: PreparedStatementSetter): Int =
        jdbcOperations.prepareStatement(sql) {
            pss.setValues(it)
            it.executeUpdate()
        }

    override fun update(sql: String, keysHolder: GeneratedKeysHolder, pss: PreparedStatementSetter): Int =
        jdbcOperations.execute {
            val ps = it.prepareStatement(sql, keysHolder.columnNames)
            pss.setValues(ps)
            val updatedRows = ps.executeUpdate()
            ps.extractGeneratedKeys(keysHolder)
            updatedRows
        }

    override fun batchUpdate(sql: String, bpss: BatchPreparedStatementSetter): IntArray {
        return jdbcOperations.prepareStatement(sql) {
            setBatchValues(it, bpss)
            it.executeBatch()
        }
    }

    override fun batchUpdate(
        sql: String,
        keysHolder: GeneratedKeysHolder,
        bpss: BatchPreparedStatementSetter
    ): IntArray {
        return jdbcOperations.execute {
            val ps = it.prepareStatement(sql, keysHolder.columnNames)
            setBatchValues(ps, bpss)
            val updatedRows = ps.executeBatch()
            ps.extractGeneratedKeys(keysHolder)
            updatedRows
        }
    }

    private fun setBatchValues(ps: PreparedStatement, bpss: BatchPreparedStatementSetter) {
        val batchSize = bpss.batchSize
        for (batchIndex in 0 until batchSize) {
            bpss.setValues(ps, batchIndex)
            ps.addBatch()
        }
    }

    override fun <T> query(sql: String, pss: PreparedStatementSetter, rse: ResultSetExtractor<T>): T =
        jdbcOperations.prepareStatement(sql) {
            pss.setValues(it)
            val rs = it.executeQuery()
            rse.extractData(rs)
        }

}
