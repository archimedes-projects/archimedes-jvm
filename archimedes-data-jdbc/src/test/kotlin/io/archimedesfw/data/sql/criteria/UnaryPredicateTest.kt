package io.archimedesfw.data.sql.criteria

import io.archimedesfw.data.sql.criteria.UnaryOperator.Companion.IS_NULL
import io.archimedesfw.data.sql.criteria.Expressions.Companion.expression
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotSame
import org.junit.jupiter.api.Test

internal class UnaryPredicateTest {

    @Test
    internal fun equals() {
        val predicate1 = UnaryPredicate(expression<String>("a"), IS_NULL)
        val predicate2 = UnaryPredicate(expression<String>("a"), IS_NULL)

        assertNotSame(predicate1, predicate2)
        assertEquals(predicate1, predicate2)
    }

}


