package io.archimedesfw.data.tx.action

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS

@TestInstance(PER_CLASS)
internal class BulkActionTest {

    @Test
    internal fun summarize_success_and_errors() {
        val elements = listOf("foo", "error", "bar")
        val bulkAction = BulkAction.ofWritableTx().identifyElement<String> { it.first().toString() }

        val summary = bulkAction.forEach(elements) { check(it != "error") }

        assertEquals(2, summary.successCount)
        assertEquals(1, summary.errorCount)

        val errorDetail = summary.errors[0]
        assertEquals(1, errorDetail.index)
        assertEquals("e", errorDetail.id)
        assertTrue(errorDetail.exception is java.lang.IllegalStateException)
    }

    @Test
    internal fun support_stream() {
        val stream = listOf("foo", "zoo", "bar").stream()

        val summary = BulkAction.ofNoTx().forEach(stream) { /* Do nothing */ }

        assertEquals(3, summary.successCount)
        assertEquals(0, summary.errorCount)
    }

}
