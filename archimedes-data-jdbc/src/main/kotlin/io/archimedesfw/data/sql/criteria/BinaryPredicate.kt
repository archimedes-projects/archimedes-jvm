package io.archimedesfw.data.sql.criteria

internal data class BinaryPredicate<T>(
    val left: Expression<T>,
    val operator: BinaryOperator,
    val right: Expression<T>
) : Predicate {

}
