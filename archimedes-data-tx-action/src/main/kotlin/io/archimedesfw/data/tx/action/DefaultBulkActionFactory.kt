package io.archimedesfw.data.tx.action

import io.archimedesfw.data.tx.Transactional

public class DefaultBulkActionFactory(
    private val transactional: Transactional
) : BulkActionFactory {

    public override fun <T> ofNoTx(elementIdentifier: ElementIdentifier<T>) =
        BulkAction<T>(EachElementNoTransaction(), elementIdentifier)

    public override fun <T> ofReadonlyTx(elementIdentifier: ElementIdentifier<T>) =
        BulkAction<T>(EachElementReadOnlyTransaction(transactional), elementIdentifier)

    public override fun <T> ofWritableTx(elementIdentifier: ElementIdentifier<T>) =
        BulkAction<T>(EachElementWritableTransaction(transactional), elementIdentifier)

}
