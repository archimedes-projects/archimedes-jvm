package io.archimedesfw.data.sql.criteria

internal class OrPredicate(
    predicates: List<Predicate>
) : CompoundPredicate(" OR ", predicates) {

    fun or(other: OrPredicate): Predicate = OrPredicate(predicates + other.predicates)

}
