package io.archimedesfw.data.sql.dsl.insert

import io.archimedesfw.data.sql.dsl.field.Field
import io.archimedesfw.data.sql.dsl.field.ValueField

internal class ToSqlInsertValuesVisitor : InsertValuesVisitor {

    override fun visit(sb: StringBuilder, field: Field<*>, bindings: MutableList<Any>) {
        when (field) {
            is ValueField -> {
                val value = field.value ?: error("Constant value cannot be null")
                bindings.add(value)
                sb.append('?')
            }

            else -> throw UnsupportedOperationException(
                "${this::class.simpleName} doesn't support visit ${field::class.simpleName} type"
            )
        }
    }

}
