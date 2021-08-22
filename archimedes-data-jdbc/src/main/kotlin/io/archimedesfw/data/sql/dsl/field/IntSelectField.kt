package io.archimedesfw.data.sql.dsl.field

import java.sql.ResultSet

interface IntSelectField : SelectField<Int>, IntField {

    override fun get(rs: ResultSet): Int = rs.getInt(alias)

}
