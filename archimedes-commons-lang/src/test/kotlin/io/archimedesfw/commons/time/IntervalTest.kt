package io.archimedesfw.commons.time

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset.UTC

@TestInstance(PER_CLASS)
internal class IntervalTest {

    @Test
    fun first_and_last_day() {
        val interval = Interval(
            LocalDateTime.of(2020, 1, 1, 23, 0),
            LocalDateTime.of(2020, 1, 2, 1, 0)
        )

        assertEquals(LocalDate.of(2020, 1, 1), interval.startDay)
        assertEquals(LocalDate.of(2020, 1, 2), interval.endDayInclusive)
    }

    private fun hoursInIntervalProvider() = arrayOf(
        arrayOf(LocalDateTime.of(2020, 1, 1, 23, 0), LocalDateTime.of(2020, 1, 2, 1, 0), 3),
        arrayOf(LocalDateTime.of(2020, 2, 28, 0, 0), LocalDateTime.of(2020, 3, 1, 0, 0), 49), // leap year
        arrayOf(
            LocalDateTime.of(2020, 3, 29, 0, 0),
            LocalDateTime.of(2020, 3, 30, 0, 0),
            25
        ), // change in summer time (In UTC a day always has 24h)
        arrayOf(
            LocalDateTime.of(2020, 10, 25, 0, 0),
            LocalDateTime.of(2020, 10, 26, 0, 0),
            25
        ) // change in winter time (In UTC a day always has 24h)
    )

    @ParameterizedTest
    @MethodSource("hoursInIntervalProvider")
    fun hours_in_interval(firstHour: LocalDateTime, lastHourInclusive: LocalDateTime, expectedHours: Int) {
        val interval = Interval(firstHour, lastHourInclusive)

        assertEquals(expectedHours, interval.hours)
    }

    private fun ofMonthProvider() = arrayOf(
        arrayOf(
            LocalDate.of(2020, 2, 13), UTC, // leap year
            Interval(
                LocalDateTime.of(2020, 2, 1, 0, 0),
                LocalDateTime.of(2020, 2, 29, 23, 0)
            )
        ),
        arrayOf(
            LocalDate.of(2020, 3, 13), EUROPE_MADRID, // winter to summer
            Interval(
                LocalDateTime.of(2020, 2, 29, 23, 0),
                LocalDateTime.of(2020, 3, 31, 21, 0)
            )
        ),
        arrayOf(
            LocalDate.of(2020, 10, 13), EUROPE_MADRID, // summer to winter
            Interval(
                LocalDateTime.of(2020, 9, 30, 22, 0),
                LocalDateTime.of(2020, 10, 31, 22, 0)
            )
        )
    )

    @ParameterizedTest
    @MethodSource("ofMonthProvider")
    fun ofMonth(localDate: LocalDate, zoneId: ZoneId, expectedInterval: Interval) {
        val interval = Interval.ofMonth(localDate, zoneId)

        assertEquals(expectedInterval, interval)
    }

}
