package io.archimedesfw.data.sql.dsl.field

class IntSqlSelectField(
    sql: String,
    alias: String
) : SqlSelectField<Int>(sql, alias), IntSelectField
