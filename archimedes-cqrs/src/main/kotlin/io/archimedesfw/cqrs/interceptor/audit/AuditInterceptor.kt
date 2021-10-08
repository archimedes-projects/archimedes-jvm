package io.archimedesfw.cqrs.interceptor.audit

import io.archimedesfw.commons.logging.slf4j.logger
import io.archimedesfw.cqrs.ActionMessage
import io.archimedesfw.cqrs.interceptor.ActionInterceptor
import io.archimedesfw.cqrs.interceptor.audit.persistence.AuditRepository
import io.archimedesfw.data.tx.Transactional
import io.archimedesfw.security.Security
import java.time.LocalDateTime

class AuditInterceptor(
    private val tx: Transactional,
    private val auditRepository: AuditRepository,
    private val next: ActionInterceptor,
) : ActionInterceptor {

    override fun <R> intercept(actionMessage: ActionMessage<R>): R {
        val actionName = actionMessage.actionMessageName
        log.debug("Init {}", actionName)
        val startTime = System.currentTimeMillis()

        var result: Any? = null
        var success = true
        try {
            result = next.intercept(actionMessage)
            return result

        } catch (th: Throwable) {
            log.error("Failed to execute $actionName", th)
            result = th
            success = false
            throw th

        } finally {
            val endTime = System.currentTimeMillis()
            val elapsedTime = endTime - startTime
            log.debug("End {} ({} ms)", actionName, elapsedTime)

            val auditLog = auditLogOf(elapsedTime, actionMessage, result, success)
            tx.newWritable {
                auditRepository.save(auditLog)
            }
        }
    }

    private fun auditLogOf(
        elapsedTime: Long,
        action: ActionMessage<*>,
        result: Any?,
        success: Boolean
    ) = AuditLog(
        LocalDateTime.now(),
        elapsedTime,
        Security.username,
        action.actionMessageName,
        action.actionMessageIsReadOnly,
        success,
        arguments = action.toString(),
        result = if (result === Unit) "void" else result.toString()
    )

    private companion object {
        private val log = logger<AuditInterceptor>()
    }

}
