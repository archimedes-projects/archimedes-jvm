package io.archimedesfw.usecase

internal interface EntityRepository {

    fun save(entity: Entity)

    fun findByName(name: String): List<Entity>

}
