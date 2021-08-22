package io.archimedesfw.data.sql.dsl.predicate

internal class OrPredicate(
    predicates: List<Predicate>
) : CompositePredicate(predicates, " OR ") {

    constructor(vararg predicates: Predicate) : this(predicates.asList())

    fun or(other: OrPredicate): Predicate = OrPredicate(predicates + other.predicates)

}
