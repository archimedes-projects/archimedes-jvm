package io.archimedesfw.data.tx.action

public interface BulkActionFactory {

    public fun <T> ofNoTx(elementIdentifier: ElementIdentifier<T> = ToStringElementIdentifier()): BulkAction<T>
    public fun <T> ofReadonlyTx(elementIdentifier: ElementIdentifier<T> = ToStringElementIdentifier()): BulkAction<T>
    public fun <T> ofWritableTx(elementIdentifier: ElementIdentifier<T> = ToStringElementIdentifier()): BulkAction<T>

}
