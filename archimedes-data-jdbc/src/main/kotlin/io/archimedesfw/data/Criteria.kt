package io.archimedesfw.data

interface Criteria<T> {

    fun and(criteria: Criteria<T>): Criteria<T>

    fun or(criteria: Criteria<T>): Criteria<T>

    fun toSql(sb: StringBuilder, args: MutableList<Any?>)

}
