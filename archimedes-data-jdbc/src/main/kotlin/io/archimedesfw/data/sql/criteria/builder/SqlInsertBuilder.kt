package io.archimedesfw.data.sql.criteria.builder

import io.archimedesfw.data.sql.Column
import io.archimedesfw.data.sql.Sql
import io.archimedesfw.data.sql.criteria.CriteriaInsert

class SqlInsertBuilder {

    fun toSql(query: CriteriaInsert): Sql {
        val sb = StringBuilder(BUFFER_SIZE)
        appendInsert(sb, query)
        val columns = appendColumns(sb, query)
        appendValues(sb, query, columns)

        return Sql(sb.toString(), mutableListOf())
    }

    private fun appendInsert(sb: StringBuilder, query: CriteriaInsert) {
        val table = query.table
        sb.append("INSERT INTO ").append(table.tableName).append(" AS ").append(table.tableAlias)
    }

    private fun appendColumns(sb: StringBuilder, query: CriteriaInsert): List<Column<*>> {
        val columns = resolveColumns(query)
        sb.append(" (")
        for (i in 0 until columns.lastIndex) {
            sb.append(columns[i].name)
            sb.append(',')
        }
        sb.append(columns[columns.lastIndex].name)
        sb.append(')')
        return columns
    }

    private fun resolveColumns(query: CriteriaInsert): List<Column<*>> =
        query.columns.ifEmpty {
            query.table.updatableColumns
        }

    private fun appendValues(sb: StringBuilder, query: CriteriaInsert, columns: List<Column<*>>) {
        sb.append(" VALUES (")
        for (i in 0 until columns.lastIndex) {
            sb.append("?,")
        }
        sb.append("?)")
    }

    private companion object {
        private const val BUFFER_SIZE = 1024
    }

}
