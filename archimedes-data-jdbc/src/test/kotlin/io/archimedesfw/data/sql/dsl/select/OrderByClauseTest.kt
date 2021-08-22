package io.archimedesfw.data.sql.dsl.select

import io.archimedesfw.data.sql.dsl.SQL
import io.archimedesfw.data.sql.dsl.field.Fields
import io.archimedesfw.data.sql.dsl.field.Fields.Companion.value
import io.archimedesfw.data.sql.dsl.select.OrderBy.Order.ASC
import io.archimedesfw.data.sql.dsl.select.OrderBy.Order.DESC
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class OrderByClauseTest {

    private val orderBy = OrderByClause("", SQL.GENERAL.toSqlOrderByVisitor)
    private val sb = StringBuilder()

    @Test
    internal fun `build with one order by`() {
        orderBy.ordersBy.add(FieldOrderBy(Fields.int("ignored_a", "_a"), ASC))

        orderBy.build(sb)

        assertEquals(" ORDER BY _a ASC", sb.toString())
    }

    @Test
    internal fun `build with more than one order by`() {
        orderBy.ordersBy.add(FieldOrderBy(Fields.int("ignored_a", "_a"), ASC))
        orderBy.ordersBy.add(FieldOrderBy(Fields.int("ignored_b", "_b"), DESC))

        orderBy.build(sb)

        assertEquals(" ORDER BY _a ASC,_b DESC", sb.toString())
    }

    @Test
    internal fun `build with a composite order by`() {
        orderBy.ordersBy.add(FieldOrderBy(Fields.int("ignored_a", "_a"), ASC))
        orderBy.ordersBy.add(
            CompositeOrderBy(
                FieldOrderBy(Fields.int("ignored_b", "_b"), DESC),
                FieldOrderBy(Fields.int("ignored_c", "_c"), ASC)
            )
        )

        orderBy.build(sb)

        assertEquals(" ORDER BY _a ASC,_b DESC,_c ASC", sb.toString())
    }

    @Test
    internal fun `build with static where`() {
        val staticOrderBy = OrderByClause(" ORDER BY _a ASC", SQL.GENERAL.toSqlOrderByVisitor)
        staticOrderBy.ordersBy.add(FieldOrderBy(Fields.int("ignored_b", "_b"), DESC))

        staticOrderBy.build(sb)

        assertEquals(" ORDER BY _a ASC,_b DESC", sb.toString())
    }

}
