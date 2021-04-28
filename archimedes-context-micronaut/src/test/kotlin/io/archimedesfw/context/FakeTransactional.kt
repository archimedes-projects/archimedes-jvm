package io.archimedesfw.context

import io.archimedesfw.data.tx.Transactional

class FakeTransactional : Transactional {
    override fun <R> writable(block: () -> R): R = block()
    override fun <R> readOnly(block: () -> R): R = block()
    override fun <R> newWritable(block: () -> R): R = block()
    override fun <R> newReadOnly(block: () -> R): R = block()
}
