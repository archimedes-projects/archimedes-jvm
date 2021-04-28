package io.archimedesfw.commons.time

import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter.ISO_DATE_TIME
import java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME
import java.time.format.DateTimeParseException
import java.time.temporal.ChronoUnit.SECONDS

object UtcDateTimeConverter {

    /**
     * Supports string as https://en.wikipedia.org/wiki/ISO_8601
     */
    fun convert(text: String): LocalDateTime {
        val lastCharIndex = text.length - 1
        try {
            return if (text[lastCharIndex] == 'Z') {
                val withoutZ = text.substring(0, lastCharIndex)
                ISO_LOCAL_DATE_TIME.parse(withoutZ, LocalDateTime::from)
            } else {
                val atZone = ISO_DATE_TIME.parse(text, ZonedDateTime::from)
                ClockUtils.toUtc(atZone)
            }
        } catch (e: DateTimeParseException) {
            throw IllegalArgumentException("Cannot parse $text to UTC LocalDateTime", e)
        }
    }

    fun convert(utcZeroDateTime: LocalDateTime): String =
        utcZeroDateTime.truncatedTo(SECONDS).format(ISO_LOCAL_DATE_TIME) + "Z"

}
