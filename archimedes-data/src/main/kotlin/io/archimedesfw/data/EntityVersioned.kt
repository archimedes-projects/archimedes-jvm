package io.archimedesfw.data

interface EntityVersioned<K> : Entity<K> {

    val version: Version

    override fun isSameEntity(other: Entity<*>): Boolean {
        if (!super.isSameEntity(other)) return false

        if (other !is EntityVersioned<*>) return false
        if (this.version.start != other.version.start) return false

        return this.version.end == null
                || (this.version.end == other.version.end)
    }


    override fun entityHashCode(): Int {
        var result = super.entityHashCode()
        result = 31 * result + version.hashCode()
        return result
    }

    override fun entityToString(): String = "EntityVersioned(${super.entityToString()}, version=$version)"

}

