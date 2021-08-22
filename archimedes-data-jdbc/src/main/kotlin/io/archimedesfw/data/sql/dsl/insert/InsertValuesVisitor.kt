package io.archimedesfw.data.sql.dsl.insert

import io.archimedesfw.data.sql.dsl.field.Field

interface InsertValuesVisitor {
    fun visit(sb: StringBuilder, field: Field<*>, bindings: MutableList<Any>)
}
