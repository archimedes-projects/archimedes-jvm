package io.archimedesfw.data.sql.criteria.builder

import io.archimedesfw.data.sql.Column
import io.archimedesfw.data.sql.Sql
import io.archimedesfw.data.sql.criteria.CriteriaUpdate
import io.archimedesfw.data.sql.criteria.parameter.Parameter

class SqlUpdateBuilder(
    private val whereBuilder: SqlWhereBuilder = SqlWhereBuilder()
) {

    fun toSql(query: CriteriaUpdate): Sql {
        val columns = resolveColumns(query)
        val sb = StringBuilder(BUFFER_SIZE)
        val parameters = mutableListOf<Parameter<*>>()

        appendUpdate(sb, query)
        appendSet(sb, columns)
        appendWhere(sb, query, parameters)

        return Sql(sb.toString(), parameters)
    }

    private fun appendUpdate(sb: StringBuilder, query: CriteriaUpdate) {
        val table = query.table
        sb.append("UPDATE ").append(table.tableName).append(" AS ").append(table.tableAlias)
    }

    private fun resolveColumns(query: CriteriaUpdate): List<Column<*>> =
        query.columns.ifEmpty {
            query.table.updatableColumns
        }

    private fun appendSet(sb: StringBuilder, columns: List<Column<*>>) {
        sb.append(" SET (")
        for (i in 0 until columns.lastIndex) {
            sb.append(columns[i].name)
            sb.append(',')
        }
        sb.append(columns[columns.lastIndex].name)
        sb.append(") = ")
        appendDefaultValues(sb, columns)
    }

    private fun appendDefaultValues(sb: StringBuilder, columns: List<Column<*>>) {
        sb.append('(')
        for (i in 0 until columns.lastIndex) {
            sb.append("?,")
        }
        sb.append("?)")
    }

    private fun appendWhere(sb: StringBuilder, query: CriteriaUpdate, parameters: MutableList<Parameter<*>>) {
        whereBuilder.appendWhere(sb, query.where, parameters)
    }

    private companion object {
        private const val BUFFER_SIZE = 1024
    }

}
