package io.archimedesfw.cqrs.handler

import io.archimedesfw.cqrs.ActionMessage

interface ActionHandlerProvider {
    fun <A : ActionMessage<R>, R> finActionHandler(actionMessage: A): ActionHandler<A, R>?
}
