package io.archimedesfw.data.sql.criteria.builder

class SqlOrderByBuilder() {

    fun appendOrderBy(sb: StringBuilder, orderBy: OrderByClause) {
        val staticOrderBy = orderBy.staticOrderBy
        val ordersBy = orderBy.orders

        if (staticOrderBy.isNotEmpty()) {
            sb.append(staticOrderBy)
            if (ordersBy.isEmpty()) return // Nothing more to add
            sb.append(",")
        } else {
            if (ordersBy.isEmpty()) return // Nothing more to add
            sb.append(" ORDER BY ")
        }

        val lastIndex = ordersBy.lastIndex
        for (i in 0 until lastIndex) {
            val order = ordersBy[i]
            sb.append(order.selection.alias).append(order.direction.sql)
            sb.append(',')
        }
        val order = ordersBy[lastIndex]
        sb.append(order.selection.alias).append(order.direction.sql)
    }

}
