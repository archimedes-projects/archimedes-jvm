package io.archimedesfw.data

fun <T> or(): Criteria<T> = OrCriteria<T>()

internal class OrCriteria<T>(vararg criteria: Criteria<T>) : AbstractCriteria<T>(" or ", *criteria) {

    override fun and(criteria: Criteria<T>): Criteria<T> = AndCriteria(this).and(criteria)

    override fun or(criteria: Criteria<T>): Criteria<T> = OrCriteria(*super.criterias, criteria)

}
