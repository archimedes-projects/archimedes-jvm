package io.archimedesfw.data.sql.dsl.field

class DoubleSqlSelectField(
    sql: String,
    alias: String
) : SqlSelectField<Double>(sql, alias), DoubleSelectField
