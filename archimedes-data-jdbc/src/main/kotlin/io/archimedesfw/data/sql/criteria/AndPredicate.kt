package io.archimedesfw.data.sql.criteria

internal class AndPredicate(
    predicates: List<Predicate>
) : CompoundPredicate(" AND ", predicates) {

    fun and(other: AndPredicate): Predicate = AndPredicate(predicates + other.predicates)

}
