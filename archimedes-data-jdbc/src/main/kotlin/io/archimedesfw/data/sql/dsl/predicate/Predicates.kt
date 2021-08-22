package io.archimedesfw.data.sql.dsl.predicate

import io.archimedesfw.data.sql.dsl.field.Field
import io.archimedesfw.data.sql.dsl.field.Fields

class Predicates {

    companion object {

        fun predicate(sql: String): Predicate = SqlPredicate(sql)

        fun <T> predicate(left: Field<T>, operator: PredicateOperator, right: T): Predicate =
            OperatorPredicate(left, operator, Fields.value(right))

        fun <T> predicate(left: Field<T>, operator: PredicateOperator, right: Field<T>): Predicate =
            OperatorPredicate(left, operator, right)

        fun and(vararg predicate: Predicate): Predicate = and(predicate.asList())
        fun and(predicates: List<Predicate>): Predicate = AndPredicate(predicates)

        fun or(vararg predicate: Predicate): Predicate = or(predicate.asList())
        fun or(predicates: List<Predicate>): Predicate = OrPredicate(predicates)

    }

}
