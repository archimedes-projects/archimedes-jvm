package io.archimedesfw.data.sql.criteria.parameter

import io.archimedesfw.commons.lang.setFloatOrNull
import java.sql.PreparedStatement

class FloatParameter(
    value: Float?,
    sql: String = "?",
    alias: String = ""
) : AbstractParameter<Float>(value, sql, alias) {

    override fun set(ps: PreparedStatement, index: Int): Unit = ps.setFloatOrNull(index, value)

}
