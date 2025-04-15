package io.archimedesfw.data.sql.criteria

internal data class UnaryPredicate<T>(
    val value: Expression<T>,
    val operator: UnaryOperator
) : Predicate {

}
