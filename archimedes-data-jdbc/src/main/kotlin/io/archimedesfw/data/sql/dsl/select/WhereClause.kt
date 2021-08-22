package io.archimedesfw.data.sql.dsl.select

import io.archimedesfw.data.sql.dsl.predicate.AndPredicate
import io.archimedesfw.data.sql.dsl.predicate.Predicate
import io.archimedesfw.data.sql.dsl.predicate.PredicateVisitor

class WhereClause internal constructor(
    val staticWhere: String,
    val toSqlPredicateVisitor: PredicateVisitor
) {
    val predicates: MutableList<Predicate> = mutableListOf()

    fun build(sb: StringBuilder, bindings: MutableList<Any>) {
        var init = " WHERE "

        if (staticWhere.isNotEmpty()) {
            sb.append(staticWhere)
            init = " AND "
        }

        if (predicates.isEmpty()) return

        sb.append(init)
        val predicate = when (predicates.size) {
            1 -> predicates[0]
            else -> AndPredicate(predicates)
        }
        toSqlPredicateVisitor.visit(sb, predicate, bindings)
    }

}
