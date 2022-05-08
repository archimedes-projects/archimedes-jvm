package io.archimedesfw.data.sql.criteria.parameter

import io.archimedesfw.commons.lang.setLongOrNull
import java.sql.PreparedStatement

class LongParameter(
    value: Long?,
    sql: String = "?",
    alias: String = ""
) : AbstractParameter<Long>(value, sql, alias) {

    override fun set(ps: PreparedStatement, index: Int): Unit = ps.setLongOrNull(index, value)

}
