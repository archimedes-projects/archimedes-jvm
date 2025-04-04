package io.archimedesfw.data.tx

public class NoOpTransactional : Transactional {

    override fun <R> newReadOnly(block: () -> R): R = block()

    override fun <R> newWritable(block: () -> R): R = block()

    override fun <R> readOnly(block: () -> R): R = block()

    override fun <R> writable(block: () -> R): R = block()

}
