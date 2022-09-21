package io.archimedesfw.data.sql.criteria

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotSame
import org.junit.jupiter.api.Test

internal class CompoundPredicateTest {

    @Test
    fun equals() {
        val predicate1 = SomeCompoundPredicate(listOf(SqlPredicate("a=1"), SqlPredicate("b=2")))
        val predicate2 = SomeCompoundPredicate(listOf(SqlPredicate("a=1"), SqlPredicate("b=2")))

        assertNotSame(predicate1, predicate2)
        assertEquals(predicate1, predicate2)
    }

    private class SomeCompoundPredicate(predicates: List<Predicate>) : CompoundPredicate("some", predicates)

}
