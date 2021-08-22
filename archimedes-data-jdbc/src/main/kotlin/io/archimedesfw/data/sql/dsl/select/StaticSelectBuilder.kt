package io.archimedesfw.data.sql.dsl.select

import io.archimedesfw.data.sql.dsl.SqlContext
import io.archimedesfw.data.sql.dsl.predicate.Predicate

class StaticSelectBuilder(
    private val staticSelectQuery: StaticSelectQuery,
) : WhereStep {

    constructor(
        context: SqlContext,
        staticSelect: String,
        staticWhere: String = "",
        staticOrderBy: String = "",
        bindings: MutableList<Any> = mutableListOf()
    ) : this(StaticSelectQuery(context, staticSelect, staticWhere, staticOrderBy, bindings))

    override val bindings: List<Any> by staticSelectQuery::bindings

    override fun where(predicates: List<Predicate>): WhereStep {
        staticSelectQuery.where.predicates.addAll(predicates)
        return this
    }

    override fun where(predicate: Predicate): WhereStep {
        staticSelectQuery.where.predicates.add(predicate)
        return this
    }

    override fun orderBy(ordersBy: List<OrderBy>): WhereStep {
        staticSelectQuery.orderBy.ordersBy.addAll(ordersBy)
        return this
    }

    override fun orderBy(orderBy: OrderBy): WhereStep {
        staticSelectQuery.orderBy.ordersBy.add(orderBy)
        return this
    }

    override fun toSql(): String = staticSelectQuery.toSql()

}
