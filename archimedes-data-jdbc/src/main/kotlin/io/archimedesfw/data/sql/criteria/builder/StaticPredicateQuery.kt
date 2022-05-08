package io.archimedesfw.data.sql.criteria.builder

import io.archimedesfw.data.sql.criteria.Order
import io.archimedesfw.data.sql.criteria.Predicate
import io.archimedesfw.data.sql.criteria.PredicateQuery
import io.archimedesfw.data.sql.criteria.parameter.Parameter

data class StaticPredicateQuery(
    val staticSelect: String,
    val where: WhereClause,
    val orderBy: OrderByClause,
    val parameters: List<Parameter<*>>
) : PredicateQuery {

    override fun where(predicate: Predicate): StaticPredicateQuery {
        where.predicates.add(predicate)
        return this
    }

    override fun where(predicates: List<Predicate>): StaticPredicateQuery {
        where.predicates.addAll(predicates)
        return this
    }

    override fun orderBy(order: Order): StaticPredicateQuery {
        orderBy.orders.add(order)
        return this
    }

    override fun orderBy(orders: List<Order>): StaticPredicateQuery {
        orderBy.orders.addAll(orders)
        return this
    }

}
