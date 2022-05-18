package io.archimedesfw.data.jdbc

import java.sql.ResultSet

/**
 * Extractor of a one-to-many join.
 *
 * **Note that this class is not thread safe.**
 */
open class OneToManyResultSetExtractor<K, O, M>(
    private val oneKeyMapper: OneSidePrimaryKeyMapper<K>,
    private val oneMapper: OneSideMapper<O, M>,
    private val manyMapper: ManySideMapper<O, M>,
    private val oneSideExpected: Int = 0
) : ResultSetExtractor<List<O>> {

    private var rowIndex = 0
    private val one = mutableMapOf<K, OneSide<O,M>>()

    override fun extractData(rs: ResultSet): List<O> {
        val results = if (oneSideExpected > 0) ArrayList<O>(oneSideExpected) else ArrayList<O>()
        while (rs.next()) {
            val key = oneKeyMapper.mapRow(rs, rowIndex)

            val oneSide = one.getOrPut(key) {
                val manyAccumulator = mutableListOf<M>()
                val one = oneMapper.mapRow(rs, rowIndex, manyAccumulator)

                results.add(one)
                OneSide(one, manyAccumulator)
            }

            val manySide = manyMapper.mapRow(rs, rowIndex, oneSide.one)
            if (manySide != null) oneSide.manyAccumulator.add(manySide)

            rowIndex++
        }

        return results
    }

    fun interface OneSidePrimaryKeyMapper<K> {
        fun mapRow(rs: ResultSet, rowIndex: Int): K
    }

    fun interface OneSideMapper<O, M> {
        fun mapRow(rs: ResultSet, rowIndex: Int, manyAccumulator: MutableList<M>): O
    }

    fun interface ManySideMapper<O, M> {
        fun mapRow(rs: ResultSet, rowIndex: Int, one: O): M?
    }

    class OneSidePrimaryKeyColumn<K>(private val columnIndex: Int) : OneSidePrimaryKeyMapper<K> {
        override fun mapRow(rs: ResultSet, rowIndex: Int): K {
            val any = rs.getObject(columnIndex)
                ?: error("One side key is null in row $rowIndex and column $columnIndex.")
            return any as K
        }
    }

    private class OneSide<O, M>(
        val one: O,
        val manyAccumulator: MutableList<M>
    )

}
