package io.archimedesfw.data.sql.criteria.parameter

import io.archimedesfw.data.sql.criteria.Expressions
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test

internal class ArrayParameterTest {

    @Test
    fun `equals true`() {
        val arr1 = Expressions.parameter(listOf<Int>(1, 2, 3))
        val arr2 = Expressions.parameter(listOf<Int>(1, 2, 3))
        assertEquals(arr1, arr2)
    }

    @Test
    fun `equals false`() {
        val arr1 = Expressions.parameter(listOf<Int>(1, 2, 3))
        val arr2 = Expressions.parameter(listOf<Int>(11, 22, 33))
        assertNotEquals(arr1, arr2)
    }

    @Test
    fun `hash code`() {
        val arr1 = Expressions.parameter(listOf<Int>(1, 2, 3))
        val arr2 = Expressions.parameter(listOf<Int>(1, 2, 3))
        assertEquals(arr1.hashCode(), arr2.hashCode())
    }
}
