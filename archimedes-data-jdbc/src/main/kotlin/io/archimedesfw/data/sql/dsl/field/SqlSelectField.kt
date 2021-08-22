package io.archimedesfw.data.sql.dsl.field

abstract class SqlSelectField<T>(
    override val sql: String,
    override val alias: String
) : SelectField<T>
