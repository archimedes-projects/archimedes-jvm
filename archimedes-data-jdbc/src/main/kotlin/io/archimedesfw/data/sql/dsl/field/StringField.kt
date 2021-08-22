package io.archimedesfw.data.sql.dsl.field

import io.archimedesfw.data.sql.dsl.predicate.Predicate
import io.archimedesfw.data.sql.dsl.predicate.PredicateOperator
import io.archimedesfw.data.sql.dsl.predicate.PredicateOperator.Companion.EQ
import io.archimedesfw.data.sql.dsl.predicate.PredicateOperator.Companion.GE
import io.archimedesfw.data.sql.dsl.predicate.PredicateOperator.Companion.GT
import io.archimedesfw.data.sql.dsl.predicate.PredicateOperator.Companion.LE
import io.archimedesfw.data.sql.dsl.predicate.PredicateOperator.Companion.LT
import io.archimedesfw.data.sql.dsl.predicate.PredicateOperator.Companion.NE
import io.archimedesfw.data.sql.dsl.predicate.Predicates.Companion.predicate

interface StringField : Field<String> {

    fun eq(other: String): Predicate = predicate(this, EQ, other)
    fun ne(other: String): Predicate = predicate(this, NE, other)
    fun lt(other: String): Predicate = predicate(this, LT, other)
    fun le(other: String): Predicate = predicate(this, LE, other)
    fun gt(other: String): Predicate = predicate(this, GT, other)
    fun ge(other: String): Predicate = predicate(this, GE, other)
    fun like(other: String): Predicate = predicate(this, LIKE, other)

    fun eq(other: Field<String>): Predicate = predicate(this, EQ, other)
    fun ne(other: Field<String>): Predicate = predicate(this, NE, other)
    fun lt(other: Field<String>): Predicate = predicate(this, LT, other)
    fun le(other: Field<String>): Predicate = predicate(this, LE, other)
    fun gt(other: Field<String>): Predicate = predicate(this, GT, other)
    fun ge(other: Field<String>): Predicate = predicate(this, GE, other)
    fun like(other: Field<String>): Predicate = predicate(this, LIKE, other)

    companion object {
        val LIKE: PredicateOperator = PredicateOperator("like")
    }

}
