package io.archimedesfw.cqrs.interceptor

import io.archimedesfw.cqrs.ActionMessage
import io.archimedesfw.cqrs.handler.ActionHandlerRegistry

class ExecuteHandlerInterceptor(
    private val actionHandlerRegistry: ActionHandlerRegistry
) : ActionInterceptor {

    override fun <R> intercept(actionMessage: ActionMessage<R>): R {
        val handler = actionHandlerRegistry.getHandler(actionMessage)
        return handler.handle(actionMessage)
    }

}
