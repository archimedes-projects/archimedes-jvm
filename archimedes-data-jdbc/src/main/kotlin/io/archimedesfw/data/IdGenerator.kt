package io.archimedesfw.data

interface IdGenerator<K> {

    fun nextId(entity: Entity<K>): K

    fun afterInsertAdjust(id: K, afterInsertGeneratedKeys: GeneratedKeys): K

}
