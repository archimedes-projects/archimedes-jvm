package io.archimedesfw.data.tx.action

public interface EachElementTransaction<T> {

    fun decorate(element: T, action: (T) -> Unit)

}
