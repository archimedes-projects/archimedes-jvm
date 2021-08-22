package io.archimedesfw.data.sql.dsl.field

class StringSqlSelectField(
    sql: String,
    alias: String
) : SqlSelectField<String>(sql, alias), StringSelectField
