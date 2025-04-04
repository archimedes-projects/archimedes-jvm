package io.archimedesfw.usecase

import io.archimedesfw.data.tx.Transactional
import io.archimedesfw.usecase.audit.AuditableInterceptor
import io.archimedesfw.usecase.audit.persistence.AuditRepository
import io.archimedesfw.usecase.security.SecureInterceptor
import io.archimedesfw.usecase.transaction.TransactionalInterceptor
import io.micronaut.context.annotation.Factory
import jakarta.inject.Singleton

@Factory
internal class UseCaseBusFactory {

    @Singleton
    internal fun bus(
        auditRepository: AuditRepository,
        transactional: Transactional
    ): UseCaseBus {
        val chain = AuditableInterceptor(
            transactional,
            auditRepository,
            SecureInterceptor(
                TransactionalInterceptor(
                    transactional,
                    TailInterceptor()
                )
            )
        )

        return InterceptedUseCaseBus(chain)
    }

}
