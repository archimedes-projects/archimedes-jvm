package io.archimedesfw.data

interface EntityLong : Entity<Long> {

    override fun isNewEntity(): Boolean = id == NEW

    companion object {
        const val NEW: Long = -1
    }

}
