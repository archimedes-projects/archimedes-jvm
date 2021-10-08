package io.archimedesfw.cqrs.handler

import io.archimedesfw.cqrs.ActionMessage
import kotlin.reflect.KClass

class ActionHandlerRegistry(
    private val actionHandlerProvider: ActionHandlerProvider = NOPActionHandlerProvider()
) {

    private val handlers = mutableMapOf<KClass<*>, ActionHandler<*, *>>()

    inline fun <reified A : ActionMessage<R>, R> register(actionHandler: ActionHandler<A, R>): Unit =
        this.register(A::class, actionHandler)

    fun <A : ActionMessage<R>, R> register(actionMessageClass: KClass<A>, actionHandler: ActionHandler<A, R>) {
        handlers[actionMessageClass] = actionHandler
    }

    fun <A : ActionMessage<R>, R> getHandler(actionMessage: A): ActionHandler<A, R> {
        val handler = handlers.getOrPut(actionMessage::class) {
            val foundHandler = actionHandlerProvider.finActionHandler(actionMessage)
            check(foundHandler != null) { "There is not any handler registered for the action $actionMessage" }
            foundHandler
        }
        return handler as ActionHandler<A, R>
    }

}
