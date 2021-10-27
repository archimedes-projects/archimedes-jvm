package io.archimedesfw.cqrs.handler

import io.archimedesfw.cqrs.ActionHandler
import io.archimedesfw.cqrs.ActionMessage

interface ActionHandlerRegistry {

    fun <M : ActionMessage<R>, R> getHandler(actionMessage: M): ActionHandler<M, R>

}
