package io.archimedesfw.data.jdbc.spring

import io.archimedesfw.data.EntityInt.Companion.NEW
import io.archimedesfw.data.EntityVersionedInt
import io.archimedesfw.data.Version
import java.time.LocalDateTime

class FooVersionedEntity internal constructor(
    override val id: Int,
    override val version: Version,
    val data: Int
) : EntityVersionedInt {

    constructor(versionStart: LocalDateTime, data: Int) : this(NEW, Version(versionStart), data)

    fun copy(version: Version? = null, data: Int? = null): FooVersionedEntity {
        return FooVersionedEntity(
            this.id,
            version ?: this.version,
            data ?: this.data
        )
    }

    override fun equals(other: Any?): Boolean {
        if (other !is FooVersionedEntity) return false
        if (!isSameEntity(other)) return false

        if (data != other.data) return false

        return true
    }

    override fun hashCode(): Int {
        var result = entityHashCode()
        result = 31 * result + data
        return result
    }

    override fun toString(): String = "FooVersionedEntity(${entityToString()}, data=$data)"

}
