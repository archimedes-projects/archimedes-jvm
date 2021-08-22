package io.archimedesfw.data.sql

import io.archimedesfw.data.sql.TBuilding.Companion.tBuilding
import io.archimedesfw.data.sql.dsl.SqlCreate
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class InsertBuilderTest {

    private val create = SqlCreate()

    @Test
    fun `insert values`() {
        val insert = create
            .insertInto(tBuilding)
            .values("name 1", 1.1, 1.2)
            .values("name 2", 2.1, 2.2)

        assertEquals(SQL, insert.toSql())
        assertEquals(listOf("name 1", 1.1, 1.2, "name 2", 2.1, 2.2), insert.bindings)
    }

    private companion object {
        private const val SQL = "INSERT INTO building AS building_ (" +
                "name," +
                "coords_latitude," +
                "coords_longitude" +
                ") VALUES (?,?,?),(?,?,?)"
    }

}

