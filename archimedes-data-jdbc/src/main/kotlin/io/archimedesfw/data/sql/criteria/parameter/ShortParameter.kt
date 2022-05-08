package io.archimedesfw.data.sql.criteria.parameter

import io.archimedesfw.commons.lang.setShortOrNull
import java.sql.PreparedStatement

class ShortParameter(value: Short?, sql: String = "?", alias: String = "") : AbstractParameter<Short>(value, sql, alias) {
    override fun set(ps: PreparedStatement, index: Int): Unit = ps.setShortOrNull(index, value)
}
