package io.archimedesfw.commons.time

import io.archimedesfw.commons.time.ClockUtils.toUtc
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.temporal.ChronoUnit.DAYS
import java.time.temporal.ChronoUnit.HOURS

data class Interval(val start: LocalDateTime, val endInclusive: LocalDateTime) {
    init {
        require(start < endInclusive) {
            "The start '$start' cannot be later than the end '$endInclusive'."
        }
    }

    val hours: Int = HOURS.between(start, endInclusive).toInt() + 1 // +1 because between() is exclusive
    val startDay: LocalDate = start.toLocalDate()
    val endDayInclusive: LocalDate = endInclusive.toLocalDate()
    val days: Int = DAYS.between(startDay, endDayInclusive).toInt() + 1 // +1 because between() is exclusive

    companion object {
        fun ofMonth(localDate: LocalDate, zoneId: ZoneId): Interval {
            val start = localDate.withDayOfMonth(1).atStartOfDay()
            val endInclusive = start.plusMonths(1).minusHours(1)

            val utcStart = toUtc(start, zoneId)
            val utcEndInclusive = toUtc(endInclusive, zoneId)

            return Interval(utcStart, utcEndInclusive)
        }
    }
}
