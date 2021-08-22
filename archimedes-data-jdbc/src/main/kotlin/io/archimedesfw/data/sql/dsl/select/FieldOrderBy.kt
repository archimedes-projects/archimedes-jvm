package io.archimedesfw.data.sql.dsl.select

import io.archimedesfw.data.sql.dsl.field.SelectField

internal class FieldOrderBy(
    val field: SelectField<*>,
    val order: OrderBy.Order
) : OrderBy
