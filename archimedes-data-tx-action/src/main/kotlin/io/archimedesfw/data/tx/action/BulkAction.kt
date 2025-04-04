package io.archimedesfw.data.tx.action

import org.slf4j.LoggerFactory
import java.util.stream.Stream

public class BulkAction<T>(
    private val eachElementTransaction: EachElementTransaction<T>,
    private val elementIdentifier: ElementIdentifier<T> = ToStringElementIdentifier()
) {
    private var index = 0
    private val _summary = MutableBulkActionSummary()
    public val summary: BulkActionSummary = _summary

    public fun forEach(elements: Stream<T>, action: (T) -> Unit): BulkActionSummary {
        elements.forEach { forOne(it, action) }
        return summary
    }

    public fun forEach(elements: Iterable<T>, action: (T) -> Unit): BulkActionSummary =
        forEach(elements.iterator(), action)

    public fun forEach(elements: Iterator<T>, action: (T) -> Unit): BulkActionSummary {
        for (e: T in elements) forOne(e, action)
        return summary
    }

    private fun forOne(element: T, action: (T) -> Unit): BulkActionSummary {
        try {
            eachElementTransaction.decorate(element, action)
            _summary.addSuccess()
        } catch (ex: Exception) {
            log.error(
                "Exception caught by bulk action. DON'T STOP EXECUTION and the exception is added to bulk action summary.",
                ex
            )
            _summary.addError(index, extractId(element), ex)
        }
        index++
        return summary
    }

    private fun extractId(element: T): String = try {
        elementIdentifier.identify(element)
    } catch (e: Exception) {
        val msg = "Cannot get identifier of: $element"
        log.error(msg, e)
        msg
    }

    private companion object {
        private val log = LoggerFactory.getLogger(BulkAction::class.java)
    }

}
