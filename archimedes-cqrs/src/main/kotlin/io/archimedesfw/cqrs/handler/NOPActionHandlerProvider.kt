package io.archimedesfw.cqrs.handler

import io.archimedesfw.cqrs.ActionMessage

class NOPActionHandlerProvider : ActionHandlerProvider {
    override fun <A : ActionMessage<R>, R> finActionHandler(actionMessage: A): ActionHandler<A, R>? = null
}
