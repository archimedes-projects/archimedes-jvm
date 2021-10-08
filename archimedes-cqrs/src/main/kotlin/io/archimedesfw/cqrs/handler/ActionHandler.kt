package io.archimedesfw.cqrs.handler

import io.archimedesfw.cqrs.ActionMessage

fun interface ActionHandler<A : ActionMessage<R>, R> {
    fun handle(action: A): R
}
