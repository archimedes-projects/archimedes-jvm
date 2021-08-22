package io.archimedesfw.data.sql.dsl.table

import io.archimedesfw.data.sql.dsl.field.DoubleSelectField
import io.archimedesfw.data.sql.dsl.field.NumberField

class DoubleColumn(
    table: Table,
    name: String,
    isGenerated: Boolean = false
) : Column<Double>(table, name, isGenerated), DoubleSelectField
