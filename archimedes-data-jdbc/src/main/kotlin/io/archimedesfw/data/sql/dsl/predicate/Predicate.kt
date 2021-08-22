package io.archimedesfw.data.sql.dsl.predicate

import io.archimedesfw.data.sql.dsl.predicate.Predicates.Companion.and
import io.archimedesfw.data.sql.dsl.predicate.Predicates.Companion.or

interface Predicate {

    fun and(other: Predicate): Predicate = and(this, other)

    fun or(other: Predicate): Predicate = or(this, other)

}
