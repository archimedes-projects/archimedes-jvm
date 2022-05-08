package io.archimedesfw.data.sql.criteria

interface PredicateQuery {

    fun where(predicate: Predicate): PredicateQuery
    fun where(vararg predicate: Predicate): PredicateQuery = where(predicate.asList())
    fun where(predicates: List<Predicate>): PredicateQuery

    fun orderBy(order: Order): PredicateQuery
    fun orderBy(vararg order: Order): PredicateQuery = orderBy(order.asList())
    fun orderBy(orders: List<Order>): PredicateQuery

}
