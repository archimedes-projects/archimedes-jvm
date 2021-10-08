package io.archimedesfw.data.sql.dsl.insert

import io.archimedesfw.data.sql.dsl.SqlContext
import io.archimedesfw.data.sql.dsl.predicate.Predicate
import io.archimedesfw.data.sql.dsl.select.WhereClause
import io.archimedesfw.data.sql.dsl.table.Column
import io.archimedesfw.data.sql.dsl.table.Table

class UpdateBuilder(
    private val context: SqlContext,
    private val table: Table
) {

    private val columns = mutableListOf<Column<*>>()
    private val where: WhereClause = WhereClause("", context.toSqlPredicateVisitor)
    val bindings: MutableList<Any> = mutableListOf<Any>()

    fun set(vararg column: Column<*>): UpdateBuilder = set(column.asList())
    fun set(columns: List<Column<*>>): UpdateBuilder {
        this.columns.addAll(columns)
        return this
    }

    fun where(predicates: List<Predicate>): UpdateBuilder {
        where.predicates.addAll(predicates)
        return this
    }

    fun where(predicate: Predicate): UpdateBuilder {
        where.predicates.add(predicate)
        return this
    }

    fun toSql(): String {
        resolveColumns()
        val sb = StringBuilder(1024)
        appendUpdate(sb)
        appendSet(sb)
        appendWhere(sb)
        return sb.toString()
    }

    private fun appendUpdate(sb: StringBuilder) {
        sb.append("UPDATE ").append(table.tableName).append(" AS ").append(table.tableAlias)
    }

    private fun appendSet(sb: StringBuilder) {
        sb.append(" SET (")
        for (i in 0 until columns.lastIndex) {
            sb.append(columns[i].name)
            sb.append(',')
        }
        sb.append(columns[columns.lastIndex].name)
        sb.append(") = ")
        appendDefaultValues(sb)
    }

    private fun resolveColumns() {
        if (columns.isNotEmpty()) return
        columns.addAll(table.updatableColumns)
    }

    private fun appendDefaultValues(sb: StringBuilder) {
        sb.append('(')
        for (i in 0 until columns.lastIndex) {
            sb.append("?,")
        }
        sb.append("?)")
    }

    private fun appendWhere(sb: StringBuilder) {
        where.build(sb, bindings)
    }

}
