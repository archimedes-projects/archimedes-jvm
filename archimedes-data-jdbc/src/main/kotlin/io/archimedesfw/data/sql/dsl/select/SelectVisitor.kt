package io.archimedesfw.data.sql.dsl.select

import io.archimedesfw.data.sql.dsl.field.Field

fun interface SelectVisitor {
    fun visit(sb: StringBuilder, field: Field<*>)
}
