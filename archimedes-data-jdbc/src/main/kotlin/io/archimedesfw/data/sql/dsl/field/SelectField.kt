package io.archimedesfw.data.sql.dsl.field

import io.archimedesfw.data.sql.dsl.select.FieldOrderBy
import io.archimedesfw.data.sql.dsl.select.OrderBy
import io.archimedesfw.data.sql.dsl.select.OrderBy.Order.ASC
import io.archimedesfw.data.sql.dsl.select.OrderBy.Order.DESC
import java.sql.ResultSet

interface SelectField<T> : Field<T> {

    val sql: String
    val alias: String

    fun asc(): OrderBy = FieldOrderBy(this, ASC)
    fun desc(): OrderBy = FieldOrderBy(this, DESC)

    fun get(rs: ResultSet): T

}
