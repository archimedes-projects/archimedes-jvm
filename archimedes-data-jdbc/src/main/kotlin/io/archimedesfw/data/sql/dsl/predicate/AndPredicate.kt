package io.archimedesfw.data.sql.dsl.predicate

internal class AndPredicate(
    predicates: List<Predicate>
) : CompositePredicate(predicates, " AND ") {

    constructor(vararg predicates: Predicate) : this(predicates.asList())

    fun and(other: AndPredicate): Predicate = AndPredicate(predicates + other.predicates)

}
