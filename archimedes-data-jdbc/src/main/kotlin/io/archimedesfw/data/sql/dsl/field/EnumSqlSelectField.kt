package io.archimedesfw.data.sql.dsl.field

class EnumSqlSelectField<E : Enum<E>>(
    sql: String,
    alias: String
) : SqlSelectField<E>(sql, alias), EnumSelectField<E>
