package io.archimedesfw.cqrs

interface ActionMessage<R> {

    val actionMessageName: String
        get() = this.javaClass.name

    val actionMessageIsReadOnly: Boolean

    override fun toString(): String

}
