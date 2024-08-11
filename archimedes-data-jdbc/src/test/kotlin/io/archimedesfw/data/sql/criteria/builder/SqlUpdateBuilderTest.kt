package io.archimedesfw.data.sql.criteria.builder

import io.archimedesfw.data.sql.AuthorTable.Companion.tAuthor
import io.archimedesfw.data.sql.criteria.CriteriaBuilder
import io.archimedesfw.data.sql.criteria.eq
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class SqlUpdateBuilderTest {

    private val criteriaBuilder = CriteriaBuilder()
    private val updateBuilder = SqlUpdateBuilder()

    @Test
    fun `update table`() {
        val update = criteriaBuilder
            .update(tAuthor).where(tAuthor.id.eq(1))

        val sql = updateBuilder.toSql(update)

        assertEquals(SQL, sql.statement)
    }

    @Test
    fun `update single column in table`() {
        val update = criteriaBuilder
            .update(tAuthor)
            .set(tAuthor.height)
            .where(tAuthor.id.eq(1))

        val sql = updateBuilder.toSql(update)

        assertEquals(SQL_SINGLE_COLUMN, sql.statement)
    }

    private companion object {
        private const val SQL = "UPDATE author AS author_ SET (name,height) = (?,?) WHERE author_.id=?"
        private const val SQL_SINGLE_COLUMN = "UPDATE author AS author_ SET height = ? WHERE author_.id=?"
    }

}
