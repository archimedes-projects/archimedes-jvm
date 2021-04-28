package io.archimedesfw.data

interface Finder<T> {

    fun find(criteria: Criteria<T>, limit: Int = DEFAULT_LIMIT): List<T>

    companion object {
        const val DEFAULT_LIMIT: Int = 1024
    }

}
