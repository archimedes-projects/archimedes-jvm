package io.archimedesfw.data.sql.dsl.field

import io.archimedesfw.data.sql.dsl.predicate.Predicate
import io.archimedesfw.data.sql.dsl.predicate.PredicateOperator
import io.archimedesfw.data.sql.dsl.predicate.Predicates

interface StringSelectField : SelectField<String> {

    fun eq(other: String): Predicate = Predicates.predicate(this, PredicateOperator.EQ, other)
    fun ne(other: String): Predicate = Predicates.predicate(this, PredicateOperator.NE, other)
    fun lt(other: String): Predicate = Predicates.predicate(this, PredicateOperator.LT, other)
    fun le(other: String): Predicate = Predicates.predicate(this, PredicateOperator.LE, other)
    fun gt(other: String): Predicate = Predicates.predicate(this, PredicateOperator.GT, other)
    fun ge(other: String): Predicate = Predicates.predicate(this, PredicateOperator.GE, other)
    fun like(other: String): Predicate = Predicates.predicate(this, LIKE, other)

    fun eq(other: Field<String>): Predicate = Predicates.predicate(this, PredicateOperator.EQ, other)
    fun ne(other: Field<String>): Predicate = Predicates.predicate(this, PredicateOperator.NE, other)
    fun lt(other: Field<String>): Predicate = Predicates.predicate(this, PredicateOperator.LT, other)
    fun le(other: Field<String>): Predicate = Predicates.predicate(this, PredicateOperator.LE, other)
    fun gt(other: Field<String>): Predicate = Predicates.predicate(this, PredicateOperator.GT, other)
    fun ge(other: Field<String>): Predicate = Predicates.predicate(this, PredicateOperator.GE, other)
    fun like(other: Field<String>): Predicate = Predicates.predicate(this, LIKE, other)

    companion object {
        val LIKE: PredicateOperator = PredicateOperator("like")
    }

}
