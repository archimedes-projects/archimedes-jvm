package io.archimedesfw.data.sql.dsl.table

import io.archimedesfw.data.sql.dsl.field.DoubleSelectField

class DoubleColumn(
    table: Table,
    name: String,
    isGenerated: Boolean = false
) : Column<Double>(table, name, isGenerated), DoubleSelectField
