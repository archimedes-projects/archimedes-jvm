package io.archimedesfw.data

import io.archimedesfw.commons.time.Interval
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit.MICROS

@TestInstance(PER_CLASS)
internal class EntityVersionedToIntervalConverterTest {

    private fun versionsProvider() = arrayOf(
        listOf(PAST, CURRENT), // exact capacities
        listOf(VERY_PAST, PAST, CURRENT), // leftovers at the beginning
        listOf(PAST, CURRENT, FUTURE), // leftovers at the end
        listOf(VERY_PAST, PAST, CURRENT, FUTURE), // leftovers in both
    )

    @ParameterizedTest
    @MethodSource("versionsProvider")
    internal fun versions_to_values_interval(versions: List<FooEntityVersioned>) {
        val values = versions.toInterval(INTERVAL) { it.id }

        assertEquals(EXPECTED_VALUES, values)
    }

    private fun versionsWithGapsProvider() = arrayOf(
        emptyList(), // none value is added, so all period is a gap
        listOf(CURRENT) // gap at the beginning of the period
    )

    @ParameterizedTest
    @MethodSource("versionsWithGapsProvider")
    fun fail_if_gaps(versions: List<FooEntityVersioned>) {
        val ex = assertThrows<IllegalArgumentException> {
            versions.toInterval(INTERVAL) { it.id }
        }
        assertThat(ex.message).startsWith("There is not enough info to complete the values interval.")
    }

    private companion object {
        private val TS = LocalDateTime.of(2020, 2, 7, 0, 0, 0)
        private val INTERVAL = Interval(TS.minusHours(2), TS.plusHours(2))

        private val VERY_PAST = FooEntityVersioned(TS.minusDays(10), -1)
        private val PAST = FooEntityVersioned(TS.minusDays(1), 1)
        private val CURRENT = FooEntityVersioned(TS, 2)
        private val FUTURE = FooEntityVersioned(TS.plusDays(1).plus(1, MICROS), 10)

        private val EXPECTED_VALUES = listOf(1, 1, 2, 2, 2)
    }

    internal data class FooEntityVersioned(
        override val version: Version,
        override val id: Int
    ) : EntityVersionedInt {

        internal constructor(start: LocalDateTime, id: Int) : this(Version(start), id)
    }

}
