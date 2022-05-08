package io.archimedesfw.data.sql.criteria

import io.archimedesfw.data.sql.Column
import io.archimedesfw.data.sql.Table
import io.archimedesfw.data.sql.criteria.builder.WhereClause

class CriteriaUpdate(
    internal val table: Table
) {

    internal val columns = mutableListOf<Column<*>>()
    internal val where: WhereClause = WhereClause()

    fun set(vararg column: Column<*>): CriteriaUpdate = set(column.asList())
    fun set(columns: List<Column<*>>): CriteriaUpdate {
        this.columns.addAll(columns)
        return this
    }

    fun where(predicate: Predicate): CriteriaUpdate {
        where.predicates.add(predicate)
        return this
    }

    fun where(vararg predicate: Predicate): CriteriaUpdate = where(predicate.asList())
    fun where(predicates: List<Predicate>): CriteriaUpdate {
        where.predicates.addAll(predicates)
        return this
    }

}
