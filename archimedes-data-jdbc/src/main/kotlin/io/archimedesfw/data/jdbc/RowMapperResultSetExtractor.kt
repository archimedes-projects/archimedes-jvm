package io.archimedesfw.data.jdbc

import java.sql.ResultSet
import java.util.ArrayList

class RowMapperResultSetExtractor<T>(
    private val rowMapper: RowMapper<T>,
    private val rowsExpected: Int = 0
) : ResultSetExtractor<List<T>> {

    override fun extractData(rs: ResultSet): List<T> {
        val results = if (rowsExpected > 0) ArrayList<T>(rowsExpected) else ArrayList<T>()
        var rowIndex = 0
        while (rs.next()) {
            results.add(rowMapper.mapRow(rs, rowIndex))
            rowIndex++
        }
        return results
    }

}
