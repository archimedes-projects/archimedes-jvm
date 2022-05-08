package io.archimedesfw.data.sql.criteria

import io.archimedesfw.data.sql.Table
import io.archimedesfw.data.sql.criteria.builder.WhereClause

class CriteriaDelete(
    internal val table: Table
) {

    internal val where: WhereClause = WhereClause()

    fun where(predicate: Predicate): CriteriaDelete {
        where.predicates.add(predicate)
        return this
    }

    fun where(vararg predicate: Predicate): CriteriaDelete = where(predicate.asList())
    fun where(predicates: List<Predicate>): CriteriaDelete {
        where.predicates.addAll(predicates)
        return this
    }

}
