package io.archimedesfw.cqrs

fun interface ActionHandler<M : ActionMessage<R>, R> {

    fun handle(action: M): R

}
