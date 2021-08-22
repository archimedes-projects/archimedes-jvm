package io.archimedesfw.data.sql.dsl.field

import io.archimedesfw.data.sql.dsl.predicate.Predicate
import io.archimedesfw.data.sql.dsl.predicate.PredicateOperator.Companion.EQ
import io.archimedesfw.data.sql.dsl.predicate.PredicateOperator.Companion.GE
import io.archimedesfw.data.sql.dsl.predicate.PredicateOperator.Companion.GT
import io.archimedesfw.data.sql.dsl.predicate.PredicateOperator.Companion.LE
import io.archimedesfw.data.sql.dsl.predicate.PredicateOperator.Companion.LT
import io.archimedesfw.data.sql.dsl.predicate.PredicateOperator.Companion.NE
import io.archimedesfw.data.sql.dsl.predicate.Predicates.Companion.predicate

interface NumberField<N : Number> : Field<N> {

    fun eq(other: N): Predicate = predicate(this, EQ, other)
    fun ne(other: N): Predicate = predicate(this, NE, other)
    fun lt(other: N): Predicate = predicate(this, LT, other)
    fun le(other: N): Predicate = predicate(this, LE, other)
    fun gt(other: N): Predicate = predicate(this, GT, other)
    fun ge(other: N): Predicate = predicate(this, GE, other)

    fun eq(other: Field<N>): Predicate = predicate(this, EQ, other)
    fun ne(other: Field<N>): Predicate = predicate(this, NE, other)
    fun lt(other: Field<N>): Predicate = predicate(this, LT, other)
    fun le(other: Field<N>): Predicate = predicate(this, LE, other)
    fun gt(other: Field<N>): Predicate = predicate(this, GT, other)
    fun ge(other: Field<N>): Predicate = predicate(this, GE, other)

}
