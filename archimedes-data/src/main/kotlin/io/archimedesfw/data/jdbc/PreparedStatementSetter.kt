package io.archimedesfw.data.jdbc

import java.sql.PreparedStatement

fun interface PreparedStatementSetter {

    fun setValues(ps: PreparedStatement)

}
