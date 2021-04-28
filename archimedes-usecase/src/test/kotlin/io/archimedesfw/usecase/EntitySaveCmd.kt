package io.archimedesfw.usecase

import io.archimedesfw.context.ServiceLocator.locate
import io.archimedesfw.usecase.audit.AuditLog
import io.archimedesfw.usecase.audit.persistence.jdbc.JdbcAuditRepository

internal class EntitySaveCmd internal constructor(
    val entity: AuditLog,
    private val repository: JdbcAuditRepository
) : Command<String>() {

    constructor(entity: AuditLog) : this(entity, locate())

    override fun run(): String {
        val findQry = EntityFindQry(entity.userId)
        val entities = run(findQry)

        repository.save(entity)
        return if (entities.isEmpty()) entity.result else entities[0].result
    }

}
