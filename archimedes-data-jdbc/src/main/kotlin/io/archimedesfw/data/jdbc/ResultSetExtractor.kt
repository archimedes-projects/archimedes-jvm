package io.archimedesfw.data.jdbc

import java.sql.ResultSet

fun interface ResultSetExtractor<T> {

    fun extractData(rs: ResultSet): T

}
