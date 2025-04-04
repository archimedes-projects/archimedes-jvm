package io.archimedesfw.data.tx.action

import io.archimedesfw.data.tx.Transactional

public class EachElementWritableTransaction<T>(
    private val transactional: Transactional
) : EachElementTransaction<T> {

    override fun decorate(element: T, action: (T) -> Unit) {
        transactional.newWritable {
            action(element)
        }
    }

}
