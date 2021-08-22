package io.archimedesfw.data.sql.dsl.select

class OrderByClause(
    val staticOrderBy: String,
    val orderByVisitor: OrderByVisitor
) {
    val ordersBy: MutableList<OrderBy> = mutableListOf()

    fun build(sb: StringBuilder) {
        var init = " ORDER BY "

        if (staticOrderBy.isNotEmpty()) {
            sb.append(staticOrderBy)
            init = ","
        }

        if (ordersBy.isEmpty()) return

        sb.append(init)
        orderByVisitor.visit(sb, CompositeOrderBy(ordersBy))

        ordersBy.clear()
    }

}
