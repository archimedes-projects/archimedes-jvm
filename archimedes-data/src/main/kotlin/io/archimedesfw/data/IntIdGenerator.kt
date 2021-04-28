package io.archimedesfw.data

import io.archimedesfw.data.EntityInt.Companion.NEW

class IntIdGenerator private constructor() : IdGenerator<Int> {

    override fun nextId(entity: Entity<Int>): Int = NEW

    override fun afterInsertAdjust(id: Int, afterInsertGeneratedKeys: GeneratedKeys): Int =
        afterInsertGeneratedKeys.singleKey() as Int

    companion object {
        private val INSTANCE: IntIdGenerator by lazy { IntIdGenerator() }

        fun of(): IntIdGenerator = INSTANCE
    }

}
