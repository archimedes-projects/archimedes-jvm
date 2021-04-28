package io.archimedesfw.data

abstract class AbstractRepository<K, E : Entity<K>> : Repository<K, E> {

    protected abstract val idGenerator: IdGenerator<K>

    override fun save(entity: E): K =
        if (entity.isNewEntity()) {
            insert(entity)
        } else {
            update(entity)
        }

    protected open fun insert(entity: E): K {
        var id = idGenerator.nextId(entity)
        val generatedKeys = insertRow(entity, id)
        id = idGenerator.afterInsertAdjust(id, generatedKeys)
        return id
    }

    protected open fun update(entity: E): K {
        updateRow(entity)
        return entity.id
    }

    protected abstract fun insertRow(entity: E, id: K): GeneratedKeys
    protected abstract fun updateRow(entity: E)

}
