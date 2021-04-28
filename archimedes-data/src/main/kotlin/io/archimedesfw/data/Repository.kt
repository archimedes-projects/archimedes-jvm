package io.archimedesfw.data

interface Repository<K, E : Entity<K>> : Finder<E> {

    fun save(entity: E): K

    fun identityCriteria(id: K): Criteria<E>

}
