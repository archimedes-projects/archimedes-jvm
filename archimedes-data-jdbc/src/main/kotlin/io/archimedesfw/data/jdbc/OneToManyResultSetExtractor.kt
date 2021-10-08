package io.archimedesfw.data.jdbc

import java.sql.ResultSet

open class OneToManyResultSetExtractor<K, O, M>(
    private val oneKeyMapper: OneSidePrimaryKeyMapper<K>,
    private val oneMapper: OneSideMapper<O, M>,
    private val manyMapper: ManySideMapper<O, M>,
    private val rowsExpected: Int = 0
) : ResultSetExtractor<List<O>> {

    private var rowIndex = 0
    private val one = mutableMapOf<K, O>()
    private val many = mutableMapOf<K, MutableList<M>>()

    override fun extractData(rs: ResultSet): List<O> {
        val results = if (rowsExpected > 0) ArrayList<O>(rowsExpected) else ArrayList<O>()
        while (rs.next()) {
            val key = oneKeyMapper.mapRow(rs, rowIndex)
            val manyAccumulator = many.getOrPut(key) { mutableListOf() }

            var oneSideIsNew = false
            val oneSide = one.getOrPut(key) {
                oneSideIsNew = true
                oneMapper.mapRow(rs, rowIndex, manyAccumulator)
            }

            val manySide = manyMapper.mapRow(rs, rowIndex, oneSide)
            if (manySide != null) manyAccumulator.add(manySide)

            if (oneSideIsNew) results.add(oneSide)

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
                ?: error("one side key is null in row $rowIndex and column $columnIndex")
            return any as K
        }
    }

}
