package io.archimedesfw.usecase

import io.archimedesfw.context.ServiceLocator.locate
import io.archimedesfw.usecase.audit.AuditLog
import io.archimedesfw.usecase.audit.persistence.jdbc.JdbcAuditRepository

internal class EntityFindQry internal constructor(
    val byUserId: String,
    private val repository: JdbcAuditRepository
) : Query<List<AuditLog>>() {

    constructor(byUserId: String) : this(byUserId, locate())

    override fun run(): List<AuditLog> = repository.findByUserId(byUserId)

}
