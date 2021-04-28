package io.archimedesfw.commons.time

import java.time.Clock
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime

val EUROPE_MADRID: ZoneId = ZoneId.of("Europe/Madrid")

object ClockUtils {

    internal var clock: Clock = Clock.system(ZoneOffset.UTC)

    fun nowUtc(): LocalDateTime = LocalDateTime.now(clock)

    fun toUtc(ts: LocalDateTime, fromZoneId: ZoneId): LocalDateTime {
        val zoned = ts.atZone(fromZoneId)
        return toUtc(zoned)
    }

    fun toUtc(zoned: ZonedDateTime): LocalDateTime {
        val utc = zoned.withZoneSameInstant(ZoneOffset.UTC)
        return utc.toLocalDateTime()
    }

    fun toZone(utc: LocalDateTime, toZoneId: ZoneId): LocalDateTime {
        val zoned = toZonedDateTime(utc, toZoneId)
        return zoned.toLocalDateTime()
    }

    fun toZonedDateTime(utc: LocalDateTime, toZoneId: ZoneId): ZonedDateTime {
        val offsetDateTime = utc.atOffset(ZoneOffset.UTC)
        return offsetDateTime.atZoneSameInstant(toZoneId)
    }

}

fun LocalDateTime.isOClock(): Boolean {
    return minute == 0 && second == 0 && this.nano == 0
}
