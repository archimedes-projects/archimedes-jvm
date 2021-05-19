package io.archimedesfw.data

import io.archimedesfw.data.Criterion.Operator.EQ
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

@TestInstance(PER_CLASS)
internal class CriteriaTest {

    private val criterion1 = Criterion<Any>("field1", EQ, 1)
    private val criterion2 = Criterion<Any>("field2", EQ, 2)
    private val andCriteria = AndCriteria(criterion1)
    private val orCriteria = OrCriteria(criterion2)

    private fun argumentProvider() = arrayOf(
        arrayOf(criterion1, "field1 = ?", listOf(1)),
        arrayOf(andCriteria, "(field1 = ?)", listOf(1)),
        arrayOf(andCriteria.and(criterion2), "(field1 = ? and field2 = ?)", listOf(1, 2)),
        arrayOf(andCriteria.and(orCriteria), "(field1 = ? and (field2 = ?))", listOf(1, 2)),
        arrayOf(andCriteria.or(orCriteria), "((field1 = ?) or (field2 = ?))", listOf(1, 2)),
        arrayOf(orCriteria, "(field2 = ?)", listOf(2)),
        arrayOf(orCriteria.or(criterion1), "(field2 = ? or field1 = ?)", listOf(2, 1)),
        arrayOf(orCriteria.or(andCriteria), "(field2 = ? or (field1 = ?))", listOf(2, 1)),
        arrayOf(orCriteria.and(andCriteria), "((field2 = ?) and (field1 = ?))", listOf(2, 1))
    )


    @ParameterizedTest
    @MethodSource("argumentProvider")
    internal fun and(criteria: Criteria<*>, expectedWhere: String, expectedArgs: List<*>) {
        val sb = StringBuilder()
        val args = mutableListOf<Any?>()

        criteria.toSql(sb, args)
        assertEquals(expectedWhere, sb.toString())
        assertEquals(expectedArgs, args)
    }

}
