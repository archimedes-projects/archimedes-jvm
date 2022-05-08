package io.archimedesfw.data.sql.criteria.parameter

import io.archimedesfw.commons.lang.setIntOrNull
import java.sql.PreparedStatement

class IntParameter(
    value: Int?,
    sql: String = "?",
    alias: String = ""
) : AbstractParameter<Int>(value, sql, alias) {

    override fun set(ps: PreparedStatement, index: Int): Unit = ps.setIntOrNull(index, value)

}
