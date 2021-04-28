package io.archimedesfw.commons.time.test

import io.archimedesfw.commons.time.ClockUtils
import java.time.Clock
import java.time.LocalDateTime
import java.time.ZoneOffset

object ClockTestUtils {

    fun <T> runWithFixed(ts: LocalDateTime, block: () -> T): T {
        val previous = ClockUtils.clock
        ClockUtils.clock = fixedClock(ts)

        val result = block()
        
        ClockUtils.clock = previous
        return result
    }

    private fun fixedClock(localDateTime: LocalDateTime): Clock {
        val instant = localDateTime.atZone(ZoneOffset.UTC).toInstant()
        return Clock.fixed(instant, ZoneOffset.UTC)
    }

}
