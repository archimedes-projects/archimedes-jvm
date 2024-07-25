package io.archimedesfw.data.sql.criteria.builder

import io.archimedesfw.data.sql.BookTable.Companion.tBook
import io.archimedesfw.data.sql.criteria.Predicates.Companion.predicate
import io.archimedesfw.data.sql.criteria.eq
import io.archimedesfw.data.sql.criteria.ne
import io.archimedesfw.data.sql.criteria.parameter.Parameter
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class SqlWhereBuilderTest {

    private val where = WhereClause()
    private val sb = StringBuilder()
    private val parameters = mutableListOf<Parameter<*>>()
    private val whereBuilder = SqlWhereBuilder()

    @Test
    internal fun `build with one predicate`() {
        where.predicates.add(tBook.id.eq(42))

        whereBuilder.appendWhere(sb, where, parameters)

        assertEquals(" WHERE book_.id=?", sb.toString())
        assertEquals(listOf(42), parameters.map { it.value })
    }

    @Test
    internal fun `build with more than one predicate`() {
        where.predicates.add(tBook.id.eq(42))
        where.predicates.add(predicate("false"))

        whereBuilder.appendWhere(sb, where, parameters)

        assertEquals(" WHERE (book_.id=? AND false)", sb.toString())
        assertEquals(listOf(42), parameters.map { it.value })
    }

    @Test
    internal fun `build with static where`() {
        val staticWhere = WhereClause(" WHERE 1=1")
        staticWhere.predicates.add(predicate("false"))

        whereBuilder.appendWhere(sb, staticWhere, parameters)

        assertEquals(" WHERE 1=1 AND false", sb.toString())
        assertTrue(parameters.isEmpty())
    }

    @Test
    internal fun `build with null comparison`() {
        where.predicates.add(tBook.pagesCount.eq(null))

        whereBuilder.appendWhere(sb, where, parameters)

        assertEquals(" WHERE book_.pages_count IS NULL", sb.toString())
        assertEquals(emptyList<Parameter<*>>(), parameters.map { it.value })
    }

    @Test
    internal fun `build with negative null comparison`() {
        where.predicates.add(tBook.pagesCount.ne(null))

        whereBuilder.appendWhere(sb, where, parameters)

        assertEquals(" WHERE book_.pages_count IS NOT NULL", sb.toString())
        assertEquals(emptyList<Parameter<*>>(), parameters.map { it.value })
    }

}
