package io.archimedesfw.data.sql.criteria.parameter

import io.archimedesfw.commons.lang.setEnumOrNull
import java.sql.PreparedStatement

class EnumParameter<E : Enum<E>>(
    value: E?,
    sql: String = "?",
    alias: String = ""
) : AbstractParameter<E>(value, sql, alias) {

    override fun set(ps: PreparedStatement, index: Int): Unit = ps.setEnumOrNull(index, value)

}
