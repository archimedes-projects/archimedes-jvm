package io.archimedesfw.data.sql.dsl.table

import io.archimedesfw.data.sql.dsl.field.SqlSelectField

abstract class Column<C>(
    table: Table,
    val name: String,
    val isGenerated: Boolean
) : SqlSelectField<C>("${table.tableAlias}.$name", "${table.tableAlias}$name")
