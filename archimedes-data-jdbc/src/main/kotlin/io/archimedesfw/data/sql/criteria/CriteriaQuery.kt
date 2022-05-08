package io.archimedesfw.data.sql.criteria

import io.archimedesfw.data.sql.Table
import io.archimedesfw.data.sql.criteria.builder.OrderByClause
import io.archimedesfw.data.sql.criteria.builder.WhereClause

class CriteriaQuery : PredicateQuery {

    internal val selections: MutableList<Selection<*>> = mutableListOf()
    internal lateinit var table: Table
    internal val joins: MutableList<Join> = mutableListOf()
    internal val where: WhereClause = WhereClause()
    internal val orderBy = OrderByClause()

    fun select(selection: Selection<*>): CriteriaQuery {
        this.selections.add(selection)
        return this
    }

    fun select(vararg selection: Selection<*>): CriteriaQuery = select(selection.asList())
    fun select(selections: List<Selection<*>>): CriteriaQuery {
        this.selections.addAll(selections)
        return this
    }

    fun from(table: Table): CriteriaQuery {
        this.table = table
        return this
    }

    fun join(join: Join): CriteriaQuery {
        this.joins.add(join)
        return this
    }

    override fun where(predicate: Predicate): CriteriaQuery {
        where.predicates.add(predicate)
        return this
    }

    override fun where(predicates: List<Predicate>): CriteriaQuery {
        where.predicates.addAll(predicates)
        return this
    }

    override fun orderBy(order: Order): CriteriaQuery {
        orderBy.orders.add(order)
        return this
    }

    override fun orderBy(orders: List<Order>): CriteriaQuery {
        orderBy.orders.addAll(orders)
        return this
    }

}
