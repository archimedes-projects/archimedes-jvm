package io.archimedesfw.commons.lang

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class CircularIteratorTest {

    @Test
    internal fun iterate() {
        val list = listOf(1, 2, 3)
        val ciruclarIterator = list.circularIterator(1)

        val result = List(3) { ciruclarIterator.next() }

        assertEquals(listOf(2, 3, 1), result)
    }

}
