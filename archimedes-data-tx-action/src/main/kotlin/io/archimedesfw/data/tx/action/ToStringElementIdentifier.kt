package io.archimedesfw.data.tx.action

public class ToStringElementIdentifier<T> : ElementIdentifier<T> {

    override fun identify(element: T): String = element.toString()

}
