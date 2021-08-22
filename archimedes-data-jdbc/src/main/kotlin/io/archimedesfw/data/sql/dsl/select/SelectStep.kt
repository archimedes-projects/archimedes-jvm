package io.archimedesfw.data.sql.dsl.select

import io.archimedesfw.data.sql.dsl.field.SelectField
import io.archimedesfw.data.sql.dsl.table.Table

interface SelectStep {

    fun select(vararg expression: SelectField<*>): SelectStep = select(expression.asList())
    fun select(expressions: List<SelectField<*>>): SelectStep

    fun from(table: Table): JoinStep

}
