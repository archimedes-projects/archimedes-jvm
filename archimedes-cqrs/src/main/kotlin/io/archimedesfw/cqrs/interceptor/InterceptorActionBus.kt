package io.archimedesfw.cqrs.interceptor

import io.archimedesfw.cqrs.ActionBus
import io.archimedesfw.cqrs.ActionMessage

class InterceptorActionBus(
    private val chain: ActionInterceptor
) : ActionBus {

    override fun <R> dispatch(action: ActionMessage<R>): R = chain.intercept(action)

}
