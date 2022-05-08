package io.archimedesfw.data.sql

import io.archimedesfw.data.sql.criteria.parameter.Parameter

data class Sql(
    val statement: String,
    val parameters: List<Parameter<*>>
)
