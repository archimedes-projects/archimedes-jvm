package io.archimedesfw.data.jdbc

import java.sql.PreparedStatement

class ListBatchPreparedStatementSetter<T>(
    private val list: List<T>,
    private val setter: ElementSetter<T>
) : BatchPreparedStatementSetter {

    override val batchSize: Int = list.size

    override fun setValues(ps: PreparedStatement, batchIndex: Int) {
        val element = list[batchIndex]
        setter.setValues(ps, batchIndex, element)
    }

    fun interface ElementSetter<T> {
        fun setValues(ps: PreparedStatement, batchIndex: Int, element: T)
    }

}
