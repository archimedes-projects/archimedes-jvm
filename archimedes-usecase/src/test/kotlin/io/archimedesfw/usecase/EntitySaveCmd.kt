package io.archimedesfw.usecase

import io.archimedesfw.context.ServiceLocator.locate
import io.archimedesfw.usecase.audit.AuditLog
import io.archimedesfw.usecase.audit.persistence.jdbc.JdbcAuditRepository

internal class EntitySaveCmd internal constructor(
    val entity: Entity,
    private val repository: EntityRepository
) : Command<String>() {

    constructor(entity: Entity) : this(entity, locate())

    override fun run(): String {
        val findQry = EntityFindQry(entity.name)
        val entities = run(findQry)

        repository.save(entity)
        return if (entities.isEmpty()) entity.name else entities[0].name
    }

}
