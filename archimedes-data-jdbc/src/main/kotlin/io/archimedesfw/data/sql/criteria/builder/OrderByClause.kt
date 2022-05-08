package io.archimedesfw.data.sql.criteria.builder

import io.archimedesfw.data.sql.criteria.Order

data class OrderByClause(
    val staticOrderBy: String = "",
    val orders: MutableList<Order> = mutableListOf()
)
