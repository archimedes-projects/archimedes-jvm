package io.archimedesfw.data.tx.action

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS

internal class BulkActionTest {

    private val bulkAction = BulkAction<String>(EachElementNoTransaction())

    @Test
    fun `summarize success and errors`() {
        val elements = listOf("foo", "boom!", "bar")

        val summary = bulkAction.forEach(elements) { check(it != "boom!") }

        assertEquals(2, summary.successCount)
        assertEquals(1, summary.errorCount)

        val errorDetail = summary.errors[0]
        assertEquals(1, errorDetail.index)
        assertEquals("boom!", errorDetail.id)
        assertTrue(errorDetail.exception is java.lang.IllegalStateException)
    }

    @Test
    fun `support stream`() {
        val stream = listOf("foo", "zoo", "bar").stream()

        val summary = bulkAction.forEach(stream) { /* Do nothing */ }

        assertEquals(3, summary.successCount)
        assertEquals(0, summary.errorCount)
    }

}
