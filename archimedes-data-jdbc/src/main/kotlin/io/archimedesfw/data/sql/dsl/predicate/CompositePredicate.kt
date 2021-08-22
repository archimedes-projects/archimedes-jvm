package io.archimedesfw.data.sql.dsl.predicate

internal abstract class CompositePredicate(
    val predicates: List<Predicate>,
    val concatenationOperator: String
) : Predicate
