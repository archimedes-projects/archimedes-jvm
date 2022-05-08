package io.archimedesfw.data.sql.criteria

interface Predicate {

    fun and(other: Predicate): Predicate = Predicates.and(this, other)

    fun or(other: Predicate): Predicate = Predicates.or(this, other)

}
