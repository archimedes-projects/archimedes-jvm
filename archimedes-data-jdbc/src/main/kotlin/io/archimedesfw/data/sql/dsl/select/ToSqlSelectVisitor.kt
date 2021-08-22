package io.archimedesfw.data.sql.dsl.select

import io.archimedesfw.data.sql.dsl.field.Field
import io.archimedesfw.data.sql.dsl.field.SelectField
import io.archimedesfw.data.sql.dsl.field.ValueField

internal class ToSqlSelectVisitor : SelectVisitor {

    override fun visit(sb: StringBuilder, field: Field<*>) {
        when (field) {
            is ValueField -> sb.append(field.value)

            is SelectField -> sb.append(field.sql).append(" AS ").append(field.alias)

            else -> throw UnsupportedOperationException(
                "${this::class.simpleName} doesn't support visit ${field::class.simpleName} type"
            )
        }
    }

}
