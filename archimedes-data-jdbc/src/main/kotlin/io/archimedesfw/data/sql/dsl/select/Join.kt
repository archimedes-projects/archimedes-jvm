package io.archimedesfw.data.sql.dsl.select

import io.archimedesfw.data.sql.dsl.predicate.Predicate
import io.archimedesfw.data.sql.dsl.table.Table

class Join(
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

}
