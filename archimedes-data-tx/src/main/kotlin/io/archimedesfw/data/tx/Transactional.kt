package io.archimedesfw.data.tx

interface Transactional {

    fun <R> writable(block: () -> R): R

    fun <R> readOnly(block: () -> R): R

    fun <R> newWritable(block: () -> R): R

    fun <R> newReadOnly(block: () -> R): R

}
