package io.archimedesfw.data.jdbc

import io.archimedesfw.data.Criteria
import io.archimedesfw.data.Order

class QueryBuilder(select: String) {

    private val sb: StringBuilder = StringBuilder(select)
    private val args: MutableList<Any?> = mutableListOf()
    private var groupBy: String? = null
    private var orderBy: Order? = null
    private var limit: Int? = null

    fun where(criteria: Criteria<*>): QueryBuilder {
        sb.append(" where ")
        criteria.toSql(sb, args)
        return this
    }

    fun group(by: String): QueryBuilder {
        groupBy = by
        return this
    }

    fun order(by: String, direction: Order.Direction): QueryBuilder = order(Order(by, direction))
    fun order(by: Order): QueryBuilder {
        orderBy = by
        return this
    }

    fun limit(limit: Int): QueryBuilder {
        this.limit = limit
        return this
    }

    fun toSql(): String {
        groupBy?.let { sb.append(" group by ").append(it) }
        orderBy?.let { sb.append(" order by ").append(it.by).append(' ').append(it.direction) }
        limit?.let {
            sb.append(" limit ?")
            args.add(limit)
        }
        return sb.toString()
    }

    fun getArgs(): Array<Any?> = args.toTypedArray()

}
