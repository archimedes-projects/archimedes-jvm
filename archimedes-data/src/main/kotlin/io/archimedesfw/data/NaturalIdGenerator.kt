package io.archimedesfw.data

class NaturalIdGenerator<K> : IdGenerator<K> {

    override fun nextId(entity: Entity<K>): K = entity.id

    override fun afterInsertAdjust(id: K, afterInsertGeneratedKeys: GeneratedKeys): K = id

}
