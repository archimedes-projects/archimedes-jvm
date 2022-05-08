package io.archimedesfw.data.sql.criteria

import io.archimedesfw.data.sql.Column
import io.archimedesfw.data.sql.Table

class CriteriaInsert(
    internal val table: Table
) {

    internal val columns = mutableListOf<Column<*>>()

    fun columns(vararg column: Column<*>): CriteriaInsert = columns(column.asList())
    fun columns(columns: List<Column<*>>): CriteriaInsert {
        this.columns.addAll(columns)
        return this
    }

}
