package io.archimedesfw.data.sql.criteria.parameter

import io.archimedesfw.commons.lang.setBooleanOrNull
import java.sql.PreparedStatement

class BooleanParameter(
    value: Boolean?,
    sql: String = "?",
    alias: String = ""
) : AbstractParameter<Boolean>(value, sql, alias) {

    override fun set(ps: PreparedStatement, index: Int): Unit = ps.setBooleanOrNull(index, value)

}
