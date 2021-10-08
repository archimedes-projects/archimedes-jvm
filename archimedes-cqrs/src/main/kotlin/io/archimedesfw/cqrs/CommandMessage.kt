package io.archimedesfw.cqrs

interface CommandMessage<R> : ActionMessage<R> {
    override val actionMessageIsReadOnly: Boolean
        get() = false
}
