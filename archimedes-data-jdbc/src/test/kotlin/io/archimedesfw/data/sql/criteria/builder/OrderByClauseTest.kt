package io.archimedesfw.data.sql.criteria.builder

import io.archimedesfw.data.sql.criteria.Expressions.Companion.expression
import io.archimedesfw.data.sql.criteria.asc
import io.archimedesfw.data.sql.criteria.desc
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class OrderByClauseTest {

    private val orderBy = OrderByClause()
    private val sb = StringBuilder()
    private val orderByBuilder = SqlOrderByBuilder()

    @Test
    internal fun `build with one order by`() {
        orderBy.orders.add(expression<Int>("a", "_a").asc())

        orderByBuilder.appendOrderBy(sb, orderBy)

        assertEquals(" ORDER BY _a ASC", sb.toString())
    }

    @Test
    internal fun `build with more than one order by`() {
        orderBy.orders.add(expression<Int>("a", "_a").asc())
        orderBy.orders.add(expression<Int>("b", "_b").desc())

        orderByBuilder.appendOrderBy(sb, orderBy)

        assertEquals(" ORDER BY _a ASC,_b DESC", sb.toString())
    }

    @Test
    internal fun `build with static order by`() {
        val staticOrderBy = OrderByClause(" ORDER BY _a ASC")
        staticOrderBy.orders.add(expression<Int>("b", "_b").desc())

        orderByBuilder.appendOrderBy(sb, staticOrderBy)

        assertEquals(" ORDER BY _a ASC,_b DESC", sb.toString())
    }

}
