package io.archimedesfw.data.sql.criteria.builder

import io.archimedesfw.data.sql.AuthorTable.Companion.tAuthor
import io.archimedesfw.data.sql.criteria.CriteriaBuilder
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class SqlInsertBuilderTest {

    private val criteriaBuilder = CriteriaBuilder()
    private val insertBuilder = SqlInsertBuilder()

    @Test
    fun `insert into table`() {
        val insert = criteriaBuilder
            .insertInto(tAuthor)

        val sql = insertBuilder.toSql(insert)

        assertEquals(SQL, sql.statement)
    }

    private companion object {
        private const val SQL = "INSERT INTO author AS author_ (name,height) VALUES (?,?)"
    }

}
