package io.archimedesfw.data

import io.archimedesfw.commons.time.Interval
import io.archimedesfw.data.Criterion.Operator.EQ
import io.archimedesfw.data.Criterion.Operator.GT
import io.archimedesfw.data.Criterion.Operator.LE
import java.time.LocalDateTime

interface FinderVersioned<T> : Finder<T> {

    fun findVersion(ts: LocalDateTime, criteria: Criteria<T>): List<T> {
        // timeline: ----1--------12-------ts----23-----------null----
        // return 2

        val byVersion = ByStart<T>(LE, ts)
            .and(
                ByEnd<T>(GT, ts)
                    .or(ByEnd(EQ, null))
            )

        return find(criteria.and(byVersion))
    }

    fun findVersion(interval: Interval, criteria: Criteria<T>): List<T> {
        // timeline: ----1--------12-------i.s----23--------34----------i.e-----45------null----
        // return 2, 3, 4

        val byInterval = ByStart<T>(LE, interval.endInclusive).and(
            ByEnd<T>(GT, interval.start).or(ByEnd(EQ, null))
        )

        return find(criteria.and(byInterval))
    }

    class ByStart<T>(operator: Operator, ts: LocalDateTime) : Criterion<T>("version_start", operator, ts) {
        constructor(ts: LocalDateTime) : this(EQ, ts)
    }

    class ByEnd<T>(operator: Operator, ts: LocalDateTime?) : Criterion<T>("version_end", operator, ts) {
        constructor(ts: LocalDateTime) : this(EQ, ts)
    }

}
