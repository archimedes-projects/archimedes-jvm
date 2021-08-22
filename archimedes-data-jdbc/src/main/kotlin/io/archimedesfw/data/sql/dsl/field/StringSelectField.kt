package io.archimedesfw.data.sql.dsl.field

import java.sql.ResultSet

interface StringSelectField: SelectField<String>, StringField {

    override fun get(rs: ResultSet): String = rs.getString(alias)

}
