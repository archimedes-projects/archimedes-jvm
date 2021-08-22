package io.archimedesfw.data.sql.dsl.select

import io.archimedesfw.data.sql.dsl.SqlContext
import io.archimedesfw.data.sql.dsl.predicate.Predicate
import io.archimedesfw.data.sql.dsl.predicate.ToSqlPredicateVisitor

/**
 * This class holds a "pre-compiled" select, so you can refine it in runtime adding extra where or order by.
 *
 * This class is thread-safe. So you can safely cache instances of this class to reuse them multiple times.
 */
class ThreadSafeSelectBuilder(
    private val context: SqlContext,
    private val staticSelect: String,
    private val staticWhere: String,
    private val staticOrderBy: String,
    override val bindings: List<Any>
) : WhereStep {

    override fun where(predicates: List<Predicate>): WhereStep = staticSelectBuilder().where(predicates)
    override fun where(predicate: Predicate): WhereStep = staticSelectBuilder().where(predicate)

    override fun orderBy(ordersBy: List<OrderBy>): WhereStep = staticSelectBuilder().orderBy(ordersBy)
    override fun orderBy(orderBy: OrderBy): WhereStep = staticSelectBuilder().orderBy(orderBy)

    override fun toSql(): String = staticSelectBuilder().toSql()

    private fun staticSelectBuilder() = StaticSelectBuilder(
        StaticSelectQuery(
            staticSelect,
            WhereClause(staticWhere, context.toSqlPredicateVisitor),
            OrderByClause(staticOrderBy, context.toSqlOrderByVisitor),
            bindings.toMutableList()
        )
    )

}
