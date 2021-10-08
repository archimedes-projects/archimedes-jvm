package io.archimedesfw.cqrs.interceptor.audit.persistence

import io.archimedesfw.cqrs.interceptor.audit.AuditLog

interface AuditRepository {

    fun save(auditLog: AuditLog)

}
