package io.archimedesfw.data.jdbc

import java.sql.ResultSet

fun interface RowMapper<T> {

    fun mapRow(rs: ResultSet, rowIndex: Int): T

}
