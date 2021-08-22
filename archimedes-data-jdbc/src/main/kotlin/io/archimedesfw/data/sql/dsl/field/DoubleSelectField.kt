package io.archimedesfw.data.sql.dsl.field

import java.sql.ResultSet

interface DoubleSelectField : SelectField<Double>, DoubleField {

    override fun get(rs: ResultSet): Double = rs.getDouble(alias)

}
