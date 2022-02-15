package io.archimedesfw.usecase

import io.archimedesfw.context.ServiceLocator.locate

internal class EntityFindQry internal constructor(
    val byName: String,
    private val repository: EntityRepository
) : Query<List<Entity>>() {

    constructor(byName: String) : this(byName, locate())

    override fun run(): List<Entity> = repository.findByName(byName)

}
