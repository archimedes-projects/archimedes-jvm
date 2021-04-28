package io.archimedesfw.commons.time

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.time.LocalDateTime

@TestInstance(PER_CLASS)
internal class UtcDateTimeConverterTest {

    private val converter = UtcDateTimeConverter

    private fun tsAsStringArgumentProvider() = arrayOf(
        arrayOf("2019-06-18T22:00:00Z", LocalDateTime.of(2019, 6, 18, 22, 0, 0)),
        arrayOf("2020-03-29T01:00:00+01:00", LocalDateTime.of(2020, 3, 29, 0, 0)),
        arrayOf("2020-03-29T03:00:00+02:00", LocalDateTime.of(2020, 3, 29, 1, 0)),
        arrayOf("2020-10-25T03:00:00+02:00", LocalDateTime.of(2020, 10, 25, 1, 0)),
        arrayOf("2020-10-25T02:00:00+01:00", LocalDateTime.of(2020, 10, 25, 1, 0))
    )

    @ParameterizedTest
    @MethodSource("tsAsStringArgumentProvider")
    fun convert_from_string(text: String, expectedTs: LocalDateTime) {
        val ts = UtcDateTimeConverter.convert(text)

        assertEquals(expectedTs, ts)
    }

    private fun invalidTsAsStringArgumentProvider() = arrayOf(
        arrayOf("2019-60-18T22:00:00Z"), // Invalid date
        arrayOf("2019-06-18T22:00:00"),  // Date without zone
        arrayOf("2019-06-18T22:00:00X")  // Date with invalid character
    )

    @ParameterizedTest
    @MethodSource("invalidTsAsStringArgumentProvider")
    fun fail_if_cannot_convert(text: String) {
        assertThrows<IllegalArgumentException> {
            UtcDateTimeConverter.convert(text)
        }
    }

    @Test
    fun convert_to_string() {
        val now: LocalDateTime = LocalDateTime.of(2019, 9, 2, 16, 9, 21, 1234)
        val ts = UtcDateTimeConverter.convert(now)
        assertEquals("2019-09-02T16:09:21Z", ts)
    }

}
