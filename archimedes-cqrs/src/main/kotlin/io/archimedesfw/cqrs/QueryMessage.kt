package io.archimedesfw.cqrs

interface QueryMessage<R> : ActionMessage<R> {
    override val actionMessageIsReadOnly: Boolean
        get() = true
}
