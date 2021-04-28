package io.archimedesfw.data.tx.action

import io.archimedesfw.data.tx.Transactional
import io.archimedesfw.data.tx.action.BulkAction.EachElementTransaction.NO_TX
import io.archimedesfw.data.tx.action.BulkAction.EachElementTransaction.READONLY_TX
import io.archimedesfw.data.tx.action.BulkAction.EachElementTransaction.WRITABLE_TX
import io.archimedesfw.context.ServiceLocator
import org.slf4j.LoggerFactory
import java.util.stream.Stream

class BulkAction private constructor(
    private val eachElementTransaction: EachElementTransaction,
    private val identify: (Any) -> String = Any::toString
) {
    private var index = 0
    private val _summary = MutableBulkActionSummary()
    val summary: BulkActionSummary = _summary

    fun <T> identifyElement(identifier: (T) -> String): BulkAction =
        BulkAction(eachElementTransaction, identifier as (Any) -> String)

    fun <T> forEach(elements: Stream<T>, action: (T) -> Unit): BulkActionSummary {
        elements.forEach { forOne(it, action) }
        return summary
    }

    fun <T> forEach(elements: Iterable<T>, action: (T) -> Unit): BulkActionSummary =
        forEach(elements.iterator(), action)

    fun <T> forEach(elements: Iterator<T>, action: (T) -> Unit): BulkActionSummary {
        for (e: T in elements) forOne(e, action)
        return summary
    }

    fun <T> forOne(element: T, action: (T) -> Unit): BulkActionSummary {
        try {
            eachElementTransaction.decorate(element as Any, action as (Any) -> Unit)
            _summary.addSuccess()
        } catch (ex: Exception) {
            LOG.error(
                "Exception caught by bulk action. DON'T STOP EXECUTION and the exception is added to bulk action summary.",
                ex
            )
            _summary.addError(index, extractId(element), ex)
        }
        index++
        return summary
    }

    private fun <T> extractId(element: T): String = try {
        identify(element as Any)
    } catch (e: Exception) {
        val msg = "Cannot get identifier of: $element"
        LOG.error(msg, e)
        msg
    }

    private enum class EachElementTransaction(val decorate: (element: Any, action: (Any) -> Unit) -> Unit) {
        NO_TX({ element, action -> action(element) }),
        READONLY_TX({ element, action -> TX.newReadOnly { action(element) } }),
        WRITABLE_TX({ element, action -> TX.newWritable { action(element) } })
    }


    companion object {
        private val LOG = LoggerFactory.getLogger(BulkAction::class.java)
        private val TX = ServiceLocator.locate<Transactional>()

        fun ofNoTx(): BulkAction = BulkAction(NO_TX)
        fun ofReadonlyTx(): BulkAction = BulkAction(READONLY_TX)
        fun ofWritableTx(): BulkAction = BulkAction(WRITABLE_TX)
    }

}
