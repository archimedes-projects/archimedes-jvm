package io.archimedesfw.data.sql.dsl.table

import io.archimedesfw.data.sql.dsl.field.IntSelectField

class IntColumn(
    table: Table,
    name: String,
    isGenerated: Boolean = false
) : Column<Int>(table, name, isGenerated), IntSelectField
