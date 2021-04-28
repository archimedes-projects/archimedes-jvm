package io.archimedesfw.data

interface EntityInt : Entity<Int> {

    override fun isNewEntity(): Boolean = id == NEW

    companion object {
        const val NEW: Int = -1
    }

}
