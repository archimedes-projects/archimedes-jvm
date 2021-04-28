package io.archimedesfw.data

import io.archimedesfw.commons.lang.singleOrNone
import io.archimedesfw.data.Criterion.Operator.GT
import io.archimedesfw.data.FinderVersioned.ByStart

abstract class AbstractRepositoryVersioned<K, E : EntityVersioned<K>> :
    AbstractRepository<K, E>(), RepositoryVersioned<K, E> {

    override fun update(entity: E): K {
        val byIdentity = identityCriteria(entity.id!!)
        val previous = findVersion(entity.version.start, byIdentity).singleOrNone()

        if (previous != null && entity.version.start == previous.version.start) {
            // timeline: ----(pe)----
            updateCurrentVersion(previous, entity)
            return entity.id
        }

        val byNextCriteria = byIdentity.and(ByStart(GT, entity.version.start))
        val next = find(byNextCriteria, 1).singleOrNone()
        insertNewVersion(previous, entity, next, byIdentity)

        return entity.id
    }

    protected fun updateCurrentVersion(current: E, entity: E) {
        require(entity.isSameEntity(current)) {
            "Cannot have equal version start but different version end." +
                    " Expected version end ${current.version.end} but found ${entity.version.end}"
        }
        updateRow(entity)
    }

    protected fun insertNewVersion(previous: E?, entity: E, next: E?, byIdentity: Criteria<E>): GeneratedKeys {
        require(entity.version.end == null) {
            "Cannot insert a new version if the expiration date is informed ${entity.version}"
        }

        // timeline: ----?---e---?----
        val keyHolder = insertRow(entity, entity.id)

        if (previous != null) {
            // timeline: ----p---e---?----
            expireVersionRow(previous, entity, byIdentity)
        }

        if (next != null) {
            // timeline: ----?---e---n----
            expireVersionRow(entity, next, byIdentity)
        }

        return keyHolder
    }

    protected abstract fun expireVersionRow(current: E, next: E, byIdentity: Criteria<E>)

}
