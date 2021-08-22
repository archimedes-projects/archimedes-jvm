package io.archimedesfw.data.sql.dsl.table

import io.archimedesfw.data.sql.dsl.field.StringSelectField

class StringColumn(
    table: Table,
    name: String,
    isGenerated: Boolean = false
) : Column<String>(table, name, isGenerated), StringSelectField
