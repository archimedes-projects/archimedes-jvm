package io.archimedesfw.data.sql.dsl.predicate

import io.archimedesfw.data.sql.dsl.field.Field

internal class OperatorPredicate<T>(
    val left: Field<T>,
    val operator: PredicateOperator,
    val right: Field<T>
) : Predicate
