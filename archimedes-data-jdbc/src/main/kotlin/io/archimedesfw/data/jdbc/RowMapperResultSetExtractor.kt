package io.archimedesfw.data.jdbc

import java.sql.ResultSet

class RowMapperResultSetExtractor<T>(
    private val rowsExpected: Int,
    private val rowMapper: RowMapper<T>
) : ResultSetExtractor<List<T>> {

    constructor(rowMapper: RowMapper<T>) : this(0, rowMapper)

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
