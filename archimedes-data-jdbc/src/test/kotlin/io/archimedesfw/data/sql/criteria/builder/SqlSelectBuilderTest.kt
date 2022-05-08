package io.archimedesfw.data.sql.criteria.builder

import io.archimedesfw.data.sql.AuthorTable.Companion.tAuthor
import io.archimedesfw.data.sql.BookTable.Companion.tBook
import io.archimedesfw.data.sql.criteria.CriteriaBuilder
import io.archimedesfw.data.sql.criteria.Expressions.Companion.expression
import io.archimedesfw.data.sql.criteria.Join
import io.archimedesfw.data.sql.criteria.Join.Type.INNER
import io.archimedesfw.data.sql.criteria.desc
import io.archimedesfw.data.sql.criteria.eq
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class SqlSelectBuilderTest {

    private val criteriaBuilder = CriteriaBuilder()
    private val sqlSelectBuilder = SqlSelectBuilder()

    @Test
    fun `sql from table`() {
        val criteriaQuery = criteriaBuilder
            .select()
            .from(tAuthor)
        val sql = sqlSelectBuilder.toSql(criteriaQuery)

        assertEquals(
            "SELECT author_.id AS author_id,author_.name AS author_name,author_.height AS author_height" +
                    " FROM author AS author_",
            sql.statement
        )
    }

    @Test
    fun `sql with where`() {
        val criteriaQuery = criteriaBuilder
            .select()
            .from(tAuthor)
            .where(tAuthor.name.eq("Sir Arthur"))
        val sql = sqlSelectBuilder.toSql(criteriaQuery)

        assertEquals(
            "SELECT author_.id AS author_id,author_.name AS author_name,author_.height AS author_height" +
                    " FROM author AS author_" +
                    " WHERE author_.name=?",
            sql.statement
        )
        assertEquals("Sir Arthur", sql.parameters.single().value)
    }

    @Test
    fun `sql with order by`() {
        val criteriaQuery = criteriaBuilder
            .select()
            .from(tAuthor)
            .orderBy(tAuthor.name.desc())
        val sql = sqlSelectBuilder.toSql(criteriaQuery)

        assertEquals(
            "SELECT author_.id AS author_id,author_.name AS author_name,author_.height AS author_height" +
                    " FROM author AS author_" +
                    " ORDER BY author_name DESC",
            sql.statement
        )
    }

    @Test
    fun `build sql with join`() {
        val criteriaQuery = criteriaBuilder
            .select()
            .from(tAuthor)
            .join(Join(INNER, tBook, tAuthor.id.eq(tBook.authorId)))
        val sql = sqlSelectBuilder.toSql(criteriaQuery)

        assertEquals(
            "SELECT author_.id AS author_id,author_.name AS author_name,author_.height AS author_height" +
                    ",book_.id AS book_id,book_.author_id AS book_author_id,book_.title AS book_title,book_.pages_count AS book_pages_count" +
                    " FROM author AS author_" +
                    " INNER JOIN book book_ ON author_.id=book_.author_id",
            sql.statement
        )
    }

    @Test
    fun `sql with raw fields`() {
        val fNameLength = expression<Int>("length(author_name)", "author_name_length")

        val criteriaQuery = criteriaBuilder
            .select(fNameLength).select(tAuthor.columns)
            .from(tAuthor)
        val sql = sqlSelectBuilder.toSql(criteriaQuery)

        assertEquals(
            "SELECT length(author_name) AS author_name_length" +
                    ",author_.id AS author_id,author_.name AS author_name,author_.height AS author_height" +
                    " FROM author AS author_",
            sql.statement
        )

    }

}
