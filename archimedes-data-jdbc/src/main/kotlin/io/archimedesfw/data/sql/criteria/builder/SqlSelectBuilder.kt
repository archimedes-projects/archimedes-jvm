package io.archimedesfw.data.sql.criteria.builder

import io.archimedesfw.data.sql.Sql
import io.archimedesfw.data.sql.criteria.CriteriaQuery
import io.archimedesfw.data.sql.criteria.Selection
import io.archimedesfw.data.sql.criteria.parameter.Parameter

class SqlSelectBuilder(
    private val whereBuilder: SqlWhereBuilder = SqlWhereBuilder(),
    private val orderByBuilder: SqlOrderByBuilder = SqlOrderByBuilder()
) {

    fun toPredicateQueryFactory(query: CriteriaQuery): PredicateQueryFactory {
        val sb = StringBuilder(BUFFER_SIZE)
        val parameters = mutableListOf<Parameter<*>>()

        appendSelect(sb, query)
        appendFrom(sb, query)
        appendJoin(sb, query, parameters)

        val selectSql = sb.toString()

        sb.setLength(0)
        whereBuilder.appendWhere(sb, query.where, parameters)
        val whereSql = sb.toString()

        sb.setLength(0)
        orderByBuilder.appendOrderBy(sb, query.orderBy)
        val orderBySql = sb.toString()

        return PredicateQueryFactory(selectSql, whereSql, orderBySql, parameters)
    }

    fun toSql(query: CriteriaQuery): Sql {
        val sb = StringBuilder(BUFFER_SIZE)
        val parameters = mutableListOf<Parameter<*>>()

        appendSelect(sb, query)
        appendFrom(sb, query)
        appendJoin(sb, query, parameters)
        whereBuilder.appendWhere(sb, query.where, parameters)
        orderByBuilder.appendOrderBy(sb, query.orderBy)

        return Sql(sb.toString(), parameters)
    }

    fun toSql(query: StaticPredicateQuery): Sql {
        val sb = StringBuilder(BUFFER_SIZE)
        val parameters = query.parameters.toMutableList()

        sb.append(query.staticSelect)
        whereBuilder.appendWhere(sb, query.where, parameters)
        orderByBuilder.appendOrderBy(sb, query.orderBy)

        return Sql(sb.toString(), parameters)
    }

    private fun appendSelect(sb: StringBuilder, query: CriteriaQuery) {
        sb.append("SELECT ")

        val selections = resolveSelections(query)
        val lastIndex = selections.lastIndex
        for (i in 0 until lastIndex) {
            val selection = selections[i]
            sb.append(selection.sql).append(" AS ").append(selection.alias)
            sb.append(',')
        }
        val selection = selections[lastIndex]
        sb.append(selection.sql).append(" AS ").append(selection.alias)
    }

    private fun resolveSelections(query: CriteriaQuery): List<Selection<*>> =
        query.selections.ifEmpty {
            mutableListOf<Selection<*>>().apply {
                addAll(query.table.columns)
                query.joins.forEach {
                    addAll(it.table.columns)
                }
            }
        }

    private fun appendFrom(sb: StringBuilder, query: CriteriaQuery) {
        val table = query.table
        sb.append(" FROM ").append(table.tableName).append(" AS ").append(table.tableAlias)
    }

    private fun appendJoin(sb: StringBuilder, query: CriteriaQuery, parameters: MutableList<Parameter<*>>) {
        query.joins.forEach {
            sb.append(' ').append(it.type.sql)
            val tableToJoin = it.table
            sb.append(' ').append(tableToJoin.tableName).append(' ').append(tableToJoin.tableAlias)
            sb.append(" ON ")
            whereBuilder.appendPredicate(sb, it.on, parameters)
        }
    }

    private companion object {
        private const val BUFFER_SIZE = 1024
    }

}

