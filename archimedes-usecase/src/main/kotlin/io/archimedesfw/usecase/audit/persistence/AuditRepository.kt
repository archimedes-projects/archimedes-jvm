package io.archimedesfw.usecase.audit.persistence

import io.archimedesfw.usecase.audit.AuditLog

internal interface AuditRepository {

    fun save(auditLog: AuditLog)

}
