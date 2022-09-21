package io.archimedesfw.data.sql.criteria.parameter

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertNotSame
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.sql.PreparedStatement

@TestInstance(PER_CLASS)
internal class AbstractParameterTest {

    @Test
    fun equals() {
        val parameter1 = SomeAbstractParameter("a")
        val parameter2 = SomeAbstractParameter("a")

        assertNotSame(parameter1, parameter2)
        assertEquals(parameter1, parameter2)
    }

    private fun notEqualsProvider() = arrayOf(
        SomeAbstractParameter("different", "sql", "alias"),
        SomeAbstractParameter("value", "different", "alias"),
        SomeAbstractParameter("value", "sql", "different")
    )

    @ParameterizedTest
    @MethodSource("notEqualsProvider")
    fun `not equals`(other: AbstractParameter<String>) {
        assertNotEquals(SomeAbstractParameter("value", "sql", "alias"), other)
    }

    private class SomeAbstractParameter(
        value: String,
        sql: String = "sql",
        alias: String = "alias"
    ) : AbstractParameter<String>(value, sql, alias) {
        override fun set(ps: PreparedStatement, index: Int): Unit = throw UnsupportedOperationException()
    }

}
