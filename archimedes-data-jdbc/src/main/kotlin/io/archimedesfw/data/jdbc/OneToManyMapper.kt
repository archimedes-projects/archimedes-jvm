package io.archimedesfw.data.jdbc

import java.sql.ResultSet

open class OneToManyMapper<K, O, M>(
    private val oneKeyMapper: OneSidePrimaryKeyMapper<K>,
    private val oneMapper: OneSideMapper<O, M>,
    private val manyMapper: ManySideMapper<O, M>
) : RowMapper<O> {

    private val one = mutableMapOf<K, O>()
    private val many = mutableMapOf<K, MutableList<M>>()

    override fun mapRow(rs: ResultSet, rowIndex: Int): O {
        if (rowIndex == 0) initMapper()

        val key = oneKeyMapper.mapRow(rs, rowIndex)
        val manySide = many.getOrPut(key) { mutableListOf() }
        val oneSide = one.getOrPut(key) { oneMapper.mapRow(rs, rowIndex, manySide) }
        manySide.add(manyMapper.mapRow(rs, rowIndex, oneSide))

        return oneSide
    }

    private fun initMapper() {
        one.clear()
        many.clear()
    }

    fun interface OneSidePrimaryKeyMapper<K> {
        fun mapRow(rs: ResultSet, rowIndex: Int): K
    }

    fun interface OneSideMapper<O, M> {
        fun mapRow(rs: ResultSet, rowIndex: Int, manyAccumulator: MutableList<M>): O
    }

    fun interface ManySideMapper<O, M> {
        fun mapRow(rs: ResultSet, rowIndex: Int, one: O): M
    }

    class OneSidePrimaryKeyColumn<K>(private val columnIndex: Int) : OneSidePrimaryKeyMapper<K> {
        override fun mapRow(rs: ResultSet, rowIndex: Int): K {
            val any = rs.getObject(columnIndex)
                ?: error("one side key is null in row $rowIndex and column $columnIndex")
            return any as K
        }
    }
}
