package io.archimedesfw.data.tx.micronaut

import io.micronaut.transaction.TransactionDefinition
import io.micronaut.transaction.annotation.ReadOnly
import jakarta.inject.Singleton
import jakarta.transaction.Transactional
import jakarta.transaction.Transactional.TxType.REQUIRED
import jakarta.transaction.Transactional.TxType.REQUIRES_NEW

/**
 * `open class` because Micronaut needs to do their magic
 */
@Singleton
open class DefaultMicronautTransactional : io.archimedesfw.data.tx.Transactional {

    @Transactional(REQUIRED)
    override fun <R> writable(block: () -> R): R = block()

    @ReadOnly(propagation = TransactionDefinition.Propagation.REQUIRED)
    override fun <R> readOnly(block: () -> R): R = block()

    @Transactional(REQUIRES_NEW)
    override fun <R> newWritable(block: () -> R): R = block()

    @ReadOnly(propagation = TransactionDefinition.Propagation.REQUIRES_NEW)
    override fun <R> newReadOnly(block: () -> R): R = block()

}
