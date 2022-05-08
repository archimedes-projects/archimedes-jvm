package io.archimedesfw.data.sql.criteria.builder

import io.archimedesfw.data.sql.Sql
import io.archimedesfw.data.sql.criteria.CriteriaDelete
import io.archimedesfw.data.sql.criteria.parameter.Parameter

class SqlDeleteBuilder(
    private val whereBuilder: SqlWhereBuilder = SqlWhereBuilder()
) {

    fun toSql(query: CriteriaDelete): Sql {
        val sb = StringBuilder(BUFFER_SIZE)
        val parameters = mutableListOf<Parameter<*>>()

        appendDelete(sb, query)
        appendWhere(sb, query, parameters)

        return Sql(sb.toString(), parameters)
    }

    private fun appendDelete(sb: StringBuilder, query: CriteriaDelete) {
        val table = query.table
        sb.append("DELETE FROM ").append(table.tableName).append(" AS ").append(table.tableAlias)
    }

    private fun appendWhere(sb: StringBuilder, query: CriteriaDelete, parameters: MutableList<Parameter<*>>) {
        whereBuilder.appendWhere(sb, query.where, parameters)
    }

    private companion object {
        private const val BUFFER_SIZE = 1024
    }

}
