package io.archimedesfw.commons.time

import io.archimedesfw.commons.time.ClockUtils.toUtc
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.time.LocalDateTime
import java.time.ZoneId

@TestInstance(PER_CLASS)
internal class ClockUtilsTest {

    @ParameterizedTest
    @MethodSource("datesArgumentProvider")
    fun toUtc(ts: String, expectedTs: String) {
        val utc = toUtc(LocalDateTime.parse(ts), ZoneId.of("Europe/Madrid"))

        val expected = LocalDateTime.parse(expectedTs)

        assertEquals(expected, utc)
    }

    private fun datesArgumentProvider(): List<Arguments> {
        val winter = "2012-12-06T11:00"
        val expectedWinter = "2012-12-06T10:00"

        val summer = "2012-05-06T11:00"
        val expectedSummer = "2012-05-06T09:00"

        val hourToChangeDay = "2012-05-06T01:00"
        val expectedHourToChangeDay = "2012-05-05T23:00"

        return listOf(
            Arguments.arguments(winter, expectedWinter),
            Arguments.arguments(summer, expectedSummer),
            Arguments.arguments(hourToChangeDay, expectedHourToChangeDay)
        )
    }
}
