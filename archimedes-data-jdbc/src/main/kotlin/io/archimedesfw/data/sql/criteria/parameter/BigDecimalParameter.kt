package io.archimedesfw.data.sql.criteria.parameter

import java.math.BigDecimal
import java.sql.PreparedStatement

class BigDecimalParameter(
    value: BigDecimal?,
    sql: String = "?",
    alias: String = ""
) : AbstractParameter<BigDecimal>(value, sql, alias) {

    override fun set(ps: PreparedStatement, index: Int): Unit = ps.setBigDecimal(index, value)

}
