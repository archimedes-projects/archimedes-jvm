package io.archimedesfw.data.tx.action

public class EachElementNoTransaction<T> : EachElementTransaction<T> {

    override fun decorate(element: T, action: (T) -> Unit) {
        action(element)
    }

}
