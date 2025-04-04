package io.archimedesfw.data.tx.action

public interface ElementIdentifier<T> {

    public fun identify(element: T): String

}
