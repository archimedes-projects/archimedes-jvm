package io.archimedesfw.data

fun <T> and(): Criteria<T> = AndCriteria<T>()

internal class AndCriteria<T>(vararg criteria: Criteria<T>) : AbstractCriteria<T>(" and ", *criteria) {

    override fun and(criteria: Criteria<T>): Criteria<T> = AndCriteria(*criterias, criteria)

    override fun or(criteria: Criteria<T>): Criteria<T> = OrCriteria(this).or(criteria)

}
