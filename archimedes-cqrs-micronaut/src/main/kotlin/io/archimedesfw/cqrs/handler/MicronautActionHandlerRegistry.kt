package io.archimedesfw.cqrs.handler

import io.archimedesfw.cqrs.ActionHandler
import io.archimedesfw.cqrs.ActionMessage
import io.micronaut.context.BeanLocator
import io.micronaut.inject.qualifiers.Qualifiers

class MicronautActionHandlerRegistry(
    private val beanLocator: BeanLocator,
) : ActionHandlerRegistry {

    override fun <M : ActionMessage<R>, R> getHandler(actionMessage: M): ActionHandler<M, R> {
        return beanLocator.getBean(
            ActionHandler::class.java,
            Qualifiers.byTypeArguments(actionMessage.javaClass, Any::class.java)
        ) as ActionHandler<M, R>
    }

}
