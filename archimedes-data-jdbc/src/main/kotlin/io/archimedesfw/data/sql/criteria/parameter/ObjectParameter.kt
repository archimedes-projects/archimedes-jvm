package io.archimedesfw.data.sql.criteria.parameter

import java.sql.PreparedStatement

class ObjectParameter<T>(
    value: T?,
    sql: String = "?",
    alias: String = ""
) : AbstractParameter<T>(value, sql, alias) {

    override fun set(ps: PreparedStatement, index: Int): Unit = ps.setObject(index, value)

}
