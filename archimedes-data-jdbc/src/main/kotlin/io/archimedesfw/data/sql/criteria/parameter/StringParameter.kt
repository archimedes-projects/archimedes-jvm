package io.archimedesfw.data.sql.criteria.parameter

import java.sql.PreparedStatement

class StringParameter(value: String?, sql: String = "?", alias: String = "") : AbstractParameter<String>(value, sql, alias) {
    override fun set(ps: PreparedStatement, index: Int): Unit = ps.setString(index, value)
}
