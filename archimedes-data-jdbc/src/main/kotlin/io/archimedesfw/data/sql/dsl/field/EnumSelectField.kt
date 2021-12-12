package io.archimedesfw.data.sql.dsl.field

import io.archimedesfw.data.sql.dsl.predicate.Predicate
import io.archimedesfw.data.sql.dsl.predicate.PredicateOperator.Companion.EQ
import io.archimedesfw.data.sql.dsl.predicate.PredicateOperator.Companion.NE
import io.archimedesfw.data.sql.dsl.predicate.Predicates

interface EnumSelectField<E : Enum<E>> : SelectField<E> {

    fun eq(other: E): Predicate = Predicates.predicate(this, EQ, other)
    fun ne(other: E): Predicate = Predicates.predicate(this, NE, other)

    fun eq(other: Field<E>): Predicate = Predicates.predicate(this, EQ, other)
    fun ne(other: Field<E>): Predicate = Predicates.predicate(this, NE, other)

}
