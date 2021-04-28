package io.archimedesfw.data

import io.archimedesfw.commons.time.Interval
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit.HOURS

class EntityVersionedToIntervalConverter<E : EntityVersioned<*>, V>(
    private val interval: Interval,
    private val valueSupplier: ValueSupplier<E, V>
) {
    private val values = ArrayList<V>(interval.hours)

    fun toInterval(list: List<E>): List<V> {
        require(list.isNotEmpty()) { "There is not enough info to complete the values interval. The list is empty" }
        require(list[0].version.start <= interval.start) {
            "There is not enough info to complete the values interval." +
                    " First value starts at ${list[0].version.start}" +
                    " which is later than the expected start of the interval ${interval.start}"
        }
        val firstOverlapped = findFirstOverlapInterval(list)

        var firstHour = interval.start
        var current = list[firstOverlapped]
        valueSupplier.changeCurrentEventListener(current, firstHour)

        for (i in firstOverlapped + 1 until list.size) {
            val next = list[i]
            val nextStart = next.version.start
            if (nextStart > interval.endInclusive) break // The `next` is outside (after) the interval, so we can finish

            fillUntil(firstHour, nextStart, current)

            current = next
            firstHour = nextStart
            valueSupplier.changeCurrentEventListener(current)
        }

        fillUntil(firstHour, interval.endInclusive, current)
        values.add(valueSupplier.get(current)) // Add one more because `fillUntil()` is exclusive

        check(values.size == interval.hours) {
            "Cannot create a complete values interval of $interval." +
                    " Expected ${interval.hours} values, but found ${values.size}"
        }

        return values
    }

    private fun findFirstOverlapInterval(list: List<EntityVersioned<*>>): Int {
        var i = 1
        while (i < list.size) {
            if (list[i].version.start > interval.start) break
            i++
        }

        val firstBeforeTheInterval = i - 1

        return firstBeforeTheInterval
    }

    private fun fillUntil(firstHour: LocalDateTime, endHourExclusive: LocalDateTime, current: E) {
        val hours = HOURS.between(firstHour, endHourExclusive).toInt()
        for (i in 0 until hours) {
            val value = valueSupplier.get(current)
            values.add(value)
        }
    }

    interface ValueSupplier<E : EntityVersioned<*>, V> {
        fun changeCurrentEventListener(current: E, firstHour: LocalDateTime = current.version.start) {}
        fun get(current: E): V
    }

}

inline fun <E : EntityVersioned<*>, V> List<E>.toInterval(
    interval: Interval,
    crossinline valueSupplier: (E) -> V
): List<V> =
    EntityVersionedToIntervalConverter<E, V>(
        interval,
        object : EntityVersionedToIntervalConverter.ValueSupplier<E, V> {
            override fun get(current: E): V = valueSupplier(current)
        }
    ).toInterval(this)

fun <E : EntityVersioned<*>, V> List<E>.toInterval(
    interval: Interval,
    valueSupplier: EntityVersionedToIntervalConverter.ValueSupplier<E, V>
): List<V> = EntityVersionedToIntervalConverter(interval, valueSupplier).toInterval(this)
