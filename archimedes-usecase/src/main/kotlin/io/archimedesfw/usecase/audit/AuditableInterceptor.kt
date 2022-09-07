package io.archimedesfw.usecase.audit

import io.archimedesfw.commons.logging.slf4j.logger
import io.archimedesfw.data.tx.Transactional
import io.archimedesfw.security.Security
import io.archimedesfw.usecase.Interceptor
import io.archimedesfw.usecase.UseCase
import io.archimedesfw.usecase.audit.persistence.AuditRepository
import java.time.LocalDateTime
import kotlin.reflect.KProperty1
import kotlin.reflect.KVisibility.PUBLIC
import kotlin.reflect.full.memberProperties

internal class AuditableInterceptor(
    private val tx: Transactional,
    private val auditRepository: AuditRepository,
    private val next: Interceptor,
) : Interceptor {

    override fun <R> intercept(useCase: UseCase<R>): R {
        val useCaseName = useCase.name
        log.debug("Init {}", useCaseName)
        val startTime = System.currentTimeMillis()

        var result: Any? = null
        try {
            result = next.intercept(useCase)
            return result

        } catch (th: Throwable) {
            log.error("Failed to execute $useCaseName", th)
            result = th
            throw th

        } finally {
            val elapsed = System.currentTimeMillis() - startTime
            log.debug("End {} ({} ms)", useCaseName, elapsed)

            val auditLog = auditLogOf(elapsed, useCase, result)
            tx.newWritable {
                auditRepository.save(auditLog)
            }
        }
    }

    private fun auditLogOf(
        elapsedTime: Long,
        useCase: UseCase<*>,
        result: Any?
    ) = AuditLog(
        LocalDateTime.now(),
        elapsedTime,
        Security.name ?: "ANONYMOUS",
        useCase.name,
        useCase.isReadOnly,
        success = result !is Throwable,
        arguments = getProperties(useCase),
        result = if (result === Unit) "void" else result.toString()
    )

    private fun getProperties(useCase: UseCase<*>): String {
        val properties = useCase::class.memberProperties as Collection<KProperty1<UseCase<*>, *>>
        val sb = StringBuilder(128)
        for (property in properties) {
            if (property.visibility !== PUBLIC) continue

            val name = property.name
            val value = property.get(useCase)

            sb.append(name).append("=").append(value).append(SEPARATOR)
        }
        if (sb.isNotEmpty()) sb.setLength(sb.length - SEPARATOR.length)
        return sb.toString()
    }

    private companion object {
        private const val SEPARATOR = ","
        private val log = logger<AuditableInterceptor>()
    }

}
