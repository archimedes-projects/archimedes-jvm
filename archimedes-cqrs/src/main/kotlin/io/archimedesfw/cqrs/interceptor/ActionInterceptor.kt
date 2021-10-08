package io.archimedesfw.cqrs.interceptor

import io.archimedesfw.cqrs.ActionMessage

interface ActionInterceptor {
    fun <R> intercept(actionMessage: ActionMessage<R>): R
}
