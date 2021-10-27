package io.archimedesfw.cqrs

interface ActionBus {

    fun <R> dispatch(action: ActionMessage<R>): R

}
