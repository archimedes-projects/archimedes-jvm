package io.archimedesfw.data.sql.dsl

import io.archimedesfw.data.sql.dsl.field.SelectField
import io.archimedesfw.data.sql.dsl.insert.InsertBuilder
import io.archimedesfw.data.sql.dsl.insert.UpdateBuilder
import io.archimedesfw.data.sql.dsl.select.SelectBuilder
import io.archimedesfw.data.sql.dsl.select.SelectStep
import io.archimedesfw.data.sql.dsl.table.Table

class SqlCreate(
    val context: SqlContext = SQL.ANSI
) {

    fun select(vararg field: SelectField<*>): SelectStep = select(field.asList())
    fun select(fields: List<SelectField<*>> = emptyList()): SelectStep = SelectBuilder(context, fields)

    fun insertInto(table: Table): InsertBuilder = InsertBuilder(context, table)

    fun update(table: Table): UpdateBuilder = UpdateBuilder(context, table)

}
