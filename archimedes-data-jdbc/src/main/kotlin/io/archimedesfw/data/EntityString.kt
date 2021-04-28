package io.archimedesfw.data

interface EntityString : Entity<String> {

    override fun isNewEntity(): Boolean = id == NEW

    companion object {
        const val NEW: String = "NEW ID PLACEHOLDER"
    }

}
