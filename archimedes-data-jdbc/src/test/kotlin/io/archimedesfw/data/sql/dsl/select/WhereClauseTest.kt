package io.archimedesfw.data.sql.dsl.select

import io.archimedesfw.data.sql.dsl.SQL
import io.archimedesfw.data.sql.dsl.field.Fields
import io.archimedesfw.data.sql.dsl.predicate.PredicateOperator.Companion.EQ
import io.archimedesfw.data.sql.dsl.predicate.Predicates.Companion.predicate
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class WhereClauseTest {

    private val where = WhereClause("", SQL.ANSI.toSqlPredicateVisitor)
    private val sb = StringBuilder()
    private val bindings = mutableListOf<Any>()

    @Test
    internal fun `build with one predicate`() {
        where.predicates.add(predicate(Fields.int("a", "ignored"), EQ, 42))

        where.build(sb, bindings)

        assertEquals(" WHERE a=?", sb.toString())
        assertEquals(listOf(42), bindings)
    }

    @Test
    internal fun `build with more than one predicate`() {
        where.predicates.add(predicate(Fields.int("a", "ignored"), EQ, 42))
        where.predicates.add(predicate("false"))

        where.build(sb, bindings)

        assertEquals(" WHERE (a=? AND false)", sb.toString())
        assertEquals(listOf(42), bindings)
    }

    @Test
    internal fun `build with static where`() {
        val staticWhere = WhereClause(" WHERE 1=1", SQL.ANSI.toSqlPredicateVisitor)
        staticWhere.predicates.add(predicate("false"))

        staticWhere.build(sb, bindings)

        assertEquals(" WHERE 1=1 AND false", sb.toString())
        assertTrue(bindings.isEmpty())
    }

}
