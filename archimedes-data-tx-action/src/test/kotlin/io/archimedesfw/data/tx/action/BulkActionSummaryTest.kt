package io.archimedesfw.data.tx.action

import io.archimedesfw.data.tx.action.BulkActionSummary.BulkActionException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows


internal class BulkActionSummaryTest {

    private val summary = MutableBulkActionSummary()

    @Test
    internal fun counters() {
        summary
            .addSuccess()
            .addError(0, "id0", RuntimeException("message0"))
            .addSuccess()
            .addError(0, "id0", RuntimeException("message0"))
            .addSuccess()

        assertEquals(5, summary.totalCount)
        assertEquals(3, summary.successCount)
        assertEquals(2, summary.errorCount)
    }

    @Test
    internal fun add_errors() {
        summary.addError(0, "id0", RuntimeException("message0"))
        summary.addError(
            1, "id1", BulkActionException(
                MutableBulkActionSummary().addError(0, "nested0", RuntimeException("nested exception")),
                "nestedSummary"
            )
        )

        assertEquals(2, summary.errorCount)

        val errorDetail0 = summary.errors[0]
        assertEquals(0, errorDetail0.index)
        assertEquals("id0", errorDetail0.id)
        assertEquals("message0", errorDetail0.exception.message)

        val errorDetail1 = summary.errors[1]
        assertEquals(1, errorDetail1.index)
        assertEquals("id1", errorDetail1.id)

        val nestedException = errorDetail1.exception as BulkActionException
        assertEquals("nestedSummary", nestedException.message)
        assertEquals(0, nestedException.summary.errors[0].index)
        assertEquals("nested0", nestedException.summary.errors[0].id)
        assertEquals("nested exception", nestedException.summary.errors[0].exception.message)
    }

    @Test
    internal fun throws_with_errors() {
        summary.addError(0, "id", RuntimeException())

        assertThrows<BulkActionException> { summary.successOrThrow() }
    }

}
