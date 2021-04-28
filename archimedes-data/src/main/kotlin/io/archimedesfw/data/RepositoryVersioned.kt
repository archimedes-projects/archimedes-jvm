package io.archimedesfw.data

interface RepositoryVersioned<K, E : EntityVersioned<K>> : Repository<K, E>, FinderVersioned<E>
