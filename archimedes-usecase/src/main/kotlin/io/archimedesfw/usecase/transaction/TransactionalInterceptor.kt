package io.archimedesfw.usecase.transaction

import io.archimedesfw.data.tx.Transactional
import io.archimedesfw.usecase.Interceptor
import io.archimedesfw.usecase.UseCase

internal class TransactionalInterceptor(
    private val tx: Transactional,
    private val next: Interceptor
) : Interceptor {

    override fun <R> intercept(useCase: UseCase<R>): R =
        if (useCase.isReadOnly) {
            tx.readOnly {
                next.intercept(useCase)
            }
        } else {
            tx.writable {
                next.intercept(useCase)
            }
        }

}

