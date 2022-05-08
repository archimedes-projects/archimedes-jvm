package io.archimedesfw.data.sql.criteria.parameter

import io.archimedesfw.commons.lang.setByteOrNull
import java.sql.PreparedStatement

class ByteParameter(
    value: Byte?,
    sql: String = "?",
    alias: String = ""
) : AbstractParameter<Byte>(value, sql, alias) {

    override fun set(ps: PreparedStatement, index: Int): Unit = ps.setByteOrNull(index, value)

}
