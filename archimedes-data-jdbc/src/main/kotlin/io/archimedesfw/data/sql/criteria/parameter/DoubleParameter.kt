package io.archimedesfw.data.sql.criteria.parameter

import io.archimedesfw.commons.lang.setDoubleOrNull
import java.sql.PreparedStatement

class DoubleParameter(
    value: Double?,
    sql: String = "?",
    alias: String = ""
) : AbstractParameter<Double>(value, sql, alias) {

    override fun set(ps: PreparedStatement, index: Int): Unit = ps.setDoubleOrNull(index, value)

}
