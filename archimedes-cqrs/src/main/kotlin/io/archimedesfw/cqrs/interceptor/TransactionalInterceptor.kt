package io.archimedesfw.cqrs.interceptor

import io.archimedesfw.cqrs.ActionMessage
import io.archimedesfw.data.tx.Transactional

class TransactionalInterceptor(
    private val tx: Transactional,
    private val next: ActionInterceptor
) : ActionInterceptor {

    override fun <R> intercept(actionMessage: ActionMessage<R>): R =
        if (actionMessage.actionMessageIsReadOnly) {
            tx.readOnly {
                next.intercept(actionMessage)
            }
        } else {
            tx.writable {
                next.intercept(actionMessage)
            }
        }

}
