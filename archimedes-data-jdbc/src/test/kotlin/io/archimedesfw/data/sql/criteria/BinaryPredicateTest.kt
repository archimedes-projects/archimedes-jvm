package io.archimedesfw.data.sql.criteria

import io.archimedesfw.data.sql.criteria.BinaryOperator.Companion.EQ
import io.archimedesfw.data.sql.criteria.Expressions.Companion.expression
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotSame
import org.junit.jupiter.api.Test

internal class BinaryPredicateTest {

    @Test
    internal fun equals() {
        val predicate1 = BinaryPredicate(expression<String>("a"), EQ, expression("a"))
        val predicate2 = BinaryPredicate(expression<String>("a"), EQ, expression("a"))

        assertNotSame(predicate1, predicate2)
        assertEquals(predicate1, predicate2)
    }

}


