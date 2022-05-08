package io.archimedesfw.data.sql.criteria

import io.archimedesfw.data.sql.Table

class CriteriaBuilder {

    fun select(vararg field: Selection<*>): CriteriaQuery = select(field.asList())
    fun select(selections: List<Selection<*>> = emptyList()): CriteriaQuery = CriteriaQuery().select(selections)

    fun insertInto(table: Table): CriteriaInsert = CriteriaInsert(table)

    fun update(table: Table): CriteriaUpdate = CriteriaUpdate(table)

    fun deleteFrom(table: Table): CriteriaDelete = CriteriaDelete(table)

}
