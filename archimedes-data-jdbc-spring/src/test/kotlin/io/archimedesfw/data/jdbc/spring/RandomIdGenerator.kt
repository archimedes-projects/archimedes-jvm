package io.archimedesfw.data.jdbc.spring

import io.archimedesfw.data.Entity
import io.archimedesfw.data.GeneratedKeys
import io.archimedesfw.data.IdGenerator
import kotlin.random.Random

internal class RandomIdGenerator : IdGenerator<Int> {

    override fun nextId(entity: Entity<Int>): Int = Random.nextInt(1, 10000)

    override fun afterInsertAdjust(id: Int, afterInsertGeneratedKeys: GeneratedKeys): Int = id

}
