package io.archimedesfw.data.jdbc.spring

import io.archimedesfw.data.GeneratedKeysHolder
import io.archimedesfw.data.jdbc.BatchPreparedStatementSetter
import io.archimedesfw.data.jdbc.JdbcTemplate
import io.archimedesfw.data.jdbc.PreparedStatementSetter
import io.archimedesfw.data.jdbc.ResultSetExtractor
import jakarta.inject.Singleton

@Singleton
class SpringJdbcTemplate(
    val jdbcTemplate: org.springframework.jdbc.core.JdbcTemplate
) : JdbcTemplate {

    override fun update(sql: String, pss: PreparedStatementSetter): Int =
        jdbcTemplate.update(sql) { pss.setValues(it) }

    override fun update(sql: String, keysHolder: GeneratedKeysHolder, pss: PreparedStatementSetter): Int {
        val springKeyHolder = org.springframework.jdbc.support.GeneratedKeyHolder()
        keysHolder.clear()
        val updatedRows = jdbcTemplate.update(
            {
                val ps = it.prepareStatement(sql, keysHolder.columnNames)
                pss.setValues(ps)
                ps
            },
            springKeyHolder
        )
        keysHolder.addAllRows(springKeyHolder.keyList)
        return updatedRows
    }

    override fun batchUpdate(sql: String, bpss: BatchPreparedStatementSetter): IntArray {
        TODO("Not yet implemented")
    }

    override fun batchUpdate(
        sql: String,
        keysHolder: GeneratedKeysHolder,
        bpss: BatchPreparedStatementSetter
    ): IntArray {
        TODO("Not yet implemented")
    }

    override fun <T> query(sql: String, pss: PreparedStatementSetter, rse: ResultSetExtractor<T>): T {
        val spss = org.springframework.jdbc.core.PreparedStatementSetter { pss.setValues(it) }
        val srse = org.springframework.jdbc.core.ResultSetExtractor { rse.extractData(it) }
        return jdbcTemplate.query(sql, spss, srse)
    }

}
