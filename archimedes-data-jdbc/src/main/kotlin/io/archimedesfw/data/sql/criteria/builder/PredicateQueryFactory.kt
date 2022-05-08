package io.archimedesfw.data.sql.criteria.builder

import io.archimedesfw.data.sql.criteria.Order
import io.archimedesfw.data.sql.criteria.Predicate
import io.archimedesfw.data.sql.criteria.PredicateQuery
import io.archimedesfw.data.sql.criteria.parameter.Parameter

/**
 * This class holds a "pre-compiled" select query, so you can refine it in runtime adding extra where or order by.
 *
 * This class is thread-safe. So you can safely cache instances of this class to reuse them multiple times.
 */
data class PredicateQueryFactory(
    private val staticSelect: String,
    private val staticWhere: String,
    private val staticOrderBy: String,
    private val parameters: List<Parameter<*>>
) : PredicateQuery {

    override fun where(predicate: Predicate): StaticPredicateQuery = createNewInstance().where(predicate)
    override fun where(predicates: List<Predicate>): StaticPredicateQuery = createNewInstance().where(predicates)

    override fun orderBy(order: Order): StaticPredicateQuery = createNewInstance().orderBy(order)
    override fun orderBy(orders: List<Order>): StaticPredicateQuery = createNewInstance().orderBy(orders)

    private fun createNewInstance() = StaticPredicateQuery(
        staticSelect,
        WhereClause(staticWhere),
        OrderByClause(staticOrderBy),
        parameters
    )

}
