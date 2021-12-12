package io.archimedesfw.data.sql.dsl.table

import io.archimedesfw.data.sql.dsl.field.EnumSelectField

class EnumColumn<E : Enum<E>>(
    table: Table,
    name: String,
    isGenerated: Boolean = false
) : Column<E>(table, name, isGenerated), EnumSelectField<E>
