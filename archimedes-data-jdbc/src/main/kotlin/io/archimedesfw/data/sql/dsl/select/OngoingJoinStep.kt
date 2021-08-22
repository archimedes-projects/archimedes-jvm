package io.archimedesfw.data.sql.dsl.select

import io.archimedesfw.data.sql.dsl.predicate.AndPredicate
import io.archimedesfw.data.sql.dsl.predicate.Predicate

interface OngoingJoinStep {

    fun on(vararg predicates: Predicate): JoinStep = on(predicates.asList())
    fun on(predicates: List<Predicate>): JoinStep = on(AndPredicate(predicates))
    fun on(predicate: Predicate): JoinStep

}
