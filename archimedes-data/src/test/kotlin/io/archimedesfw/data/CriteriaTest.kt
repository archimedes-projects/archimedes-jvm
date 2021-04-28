package io.archimedesfw.data

import io.archimedesfw.data.Criterion.Operator.EQ
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource

internal class CriteriaTest {

    private val sb = StringBuilder()
    private val args: MutableList<Any?> = mutableListOf()

    @ParameterizedTest
    @MethodSource("argumentProvider")
    internal fun and(criteria: Criteria<*>, expectedWhere: String, expectedArgs: List<*>) {
        criteria.toSql(sb, args)
        assertEquals(expectedWhere, sb.toString())
        assertEquals(expectedArgs, args)
    }

    private companion object {
        // All Criteria should be immutable so we can define as constants and reuse in several tests
        private val CRITERION_1 = Criterion<Any>("field1", EQ, 1)
        private val CRITERION_2 = Criterion<Any>("field2", EQ, 2)
        private val AND_CRITERIA =
            AndCriteria(CRITERION_1)
        private val OR_CRITERIA =
            OrCriteria(CRITERION_2)

        @JvmStatic
        private fun argumentProvider(): List<Arguments> {
            return listOf(
                arguments(CRITERION_1, "field1 = ?", listOf(1)),

                arguments(AND_CRITERIA, "(field1 = ?)", listOf(1)),
                arguments(
                    AND_CRITERIA.and(
                        CRITERION_2
                    ), "(field1 = ? and field2 = ?)", listOf(1, 2)
                ),
                arguments(
                    AND_CRITERIA.and(
                        OR_CRITERIA
                    ), "(field1 = ? and (field2 = ?))", listOf(1, 2)
                ),
                arguments(
                    AND_CRITERIA.or(
                        OR_CRITERIA
                    ), "((field1 = ?) or (field2 = ?))", listOf(1, 2)
                ),

                arguments(OR_CRITERIA, "(field2 = ?)", listOf(2)),
                arguments(
                    OR_CRITERIA.or(
                        CRITERION_1
                    ), "(field2 = ? or field1 = ?)", listOf(2, 1)
                ),
                arguments(
                    OR_CRITERIA.or(
                        AND_CRITERIA
                    ), "(field2 = ? or (field1 = ?))", listOf(2, 1)
                ),
                arguments(
                    OR_CRITERIA.and(
                        AND_CRITERIA
                    ), "((field2 = ?) and (field1 = ?))", listOf(2, 1)
                )
            )
        }
    }

}
