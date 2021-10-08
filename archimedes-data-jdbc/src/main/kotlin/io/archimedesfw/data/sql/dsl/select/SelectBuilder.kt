package io.archimedesfw.data.sql.dsl.select

import io.archimedesfw.data.sql.dsl.SqlContext
import io.archimedesfw.data.sql.dsl.field.SelectField
import io.archimedesfw.data.sql.dsl.predicate.Predicate
import io.archimedesfw.data.sql.dsl.select.Join.Type.INNER
import io.archimedesfw.data.sql.dsl.select.Join.Type.LEFT
import io.archimedesfw.data.sql.dsl.select.Join.Type.OUTER
import io.archimedesfw.data.sql.dsl.select.Join.Type.RIGHT
import io.archimedesfw.data.sql.dsl.table.Table

class SelectBuilder(
    private val context: SqlContext,
    private val selectQuery: SelectQuery = SelectQuery(context)
) : SelectStep, JoinStep {

    constructor(context: SqlContext, selectList: List<SelectField<*>>) :
            this(context, SelectQuery(context).apply { selects.addAll(selectList) })

    override val bindings: List<Any> by selectQuery::bindings

    override fun select(expressions: List<SelectField<*>>): SelectStep {
        selectQuery.selects.addAll(expressions)
        return this
    }

    override fun from(table: Table): JoinStep {
        selectQuery.table = table
        return this
    }

    override fun leftJoin(table: Table): OngoingJoinStep = OngoingJoinBuilder(this, LEFT, table)
    override fun rightJoin(table: Table): OngoingJoinStep = OngoingJoinBuilder(this, RIGHT, table)
    override fun innerJoin(table: Table): OngoingJoinStep = OngoingJoinBuilder(this, INNER, table)
    override fun outerJoin(table: Table): OngoingJoinStep = OngoingJoinBuilder(this, OUTER, table)

    internal fun addJoin(join: Join) {
        selectQuery.joins.add(join)
    }

    override fun where(predicates: List<Predicate>): WhereStep {
        selectQuery.where.predicates.addAll(predicates)
        return this
    }

    override fun where(predicate: Predicate): WhereStep {
        selectQuery.where.predicates.add(predicate)
        return this
    }

    override fun orderBy(ordersBy: List<OrderBy>): WhereStep {
        selectQuery.orderBy.ordersBy.addAll(ordersBy)
        return this
    }

    override fun orderBy(orderBy: OrderBy): WhereStep {
        selectQuery.orderBy.ordersBy.add(orderBy)
        return this
    }

    override fun build(): ThreadSafeSelectBuilder {
        selectQuery.build()
        return ThreadSafeSelectBuilder(
            context,
            selectQuery.selectSql,
            selectQuery.whereSql,
            selectQuery.orderBySql,
            selectQuery.bindings
        )
    }

    override fun toSql(): String = selectQuery.toSql()

}
