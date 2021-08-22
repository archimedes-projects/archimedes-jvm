package io.archimedesfw.data.sql.dsl.select

import io.archimedesfw.data.sql.dsl.predicate.Predicate

interface WhereStep {

    fun where(predicate: Predicate): WhereStep
    fun where(vararg predicate: Predicate): WhereStep = where(predicate.asList())
    fun where(predicates: List<Predicate>): WhereStep

    fun orderBy(orderBy: OrderBy): WhereStep
    fun orderBy(vararg orderBy: OrderBy): WhereStep = orderBy(orderBy.asList())
    fun orderBy(ordersBy: List<OrderBy>): WhereStep

    val bindings: List<Any>
    
    fun toSql(): String

}
