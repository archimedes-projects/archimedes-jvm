package io.archimedesfw.data.sql.criteria

import io.archimedesfw.data.sql.Table
import io.archimedesfw.data.sql.criteria.Join.Type.INNER
import io.archimedesfw.data.sql.criteria.Join.Type.LEFT
import io.archimedesfw.data.sql.criteria.Join.Type.OUTER
import io.archimedesfw.data.sql.criteria.Join.Type.RIGHT

data class Join(
    val type: Type,
    val table: Table,
    val on: Predicate
) {

    enum class Type(val sql: String) {
        LEFT("LEFT JOIN"),
        RIGHT("RIGHT JOIN"),
        INNER("INNER JOIN"),
        OUTER("OUTER JOIN")
    }

    companion object {
        fun left(table: Table, on: Predicate): Join = Join(LEFT, table, on)
        fun right(table: Table, on: Predicate): Join = Join(RIGHT, table, on)
        fun inner(table: Table, on: Predicate): Join = Join(INNER, table, on)
        fun outer(table: Table, on: Predicate): Join = Join(OUTER, table, on)
    }

}
