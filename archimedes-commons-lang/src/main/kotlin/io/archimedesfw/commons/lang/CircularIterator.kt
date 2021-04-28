package io.archimedesfw.commons.lang

class CircularIterator<T>(private val iterable: Iterable<T>, beginAtIndex: Int = 0) : Iterator<T> {

    private var iterator = iterable.iterator()

    init {
        require(iterator.hasNext()) { "Cannot do circular iterations over an empty iterable" }
        ignoreUntil(beginAtIndex)
    }

    private fun ignoreUntil(index: Int) {
        for (i in 0 until index) iterator.next()
    }

    override fun hasNext(): Boolean = true

    override fun next(): T {
        if (!iterator.hasNext()) iterator = iterable.iterator()
        return iterator.next()
    }

}

fun <T> Iterable<T>.circularIterator(beginAtIndex: Int = 0): CircularIterator<T> =
    CircularIterator<T>(this, beginAtIndex)
