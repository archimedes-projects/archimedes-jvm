package io.archimedesfw.data.sql.dsl.insert

import io.archimedesfw.data.sql.dsl.SqlContext
import io.archimedesfw.data.sql.dsl.field.Field
import io.archimedesfw.data.sql.dsl.field.Fields.Companion.value
import io.archimedesfw.data.sql.dsl.table.Column
import io.archimedesfw.data.sql.dsl.table.Table

class InsertBuilder(
    private val context: SqlContext,
    private val table: Table
) {

    private val columns = mutableListOf<Column<*>>()
    private val values = mutableListOf<List<Field<*>>>()
    private val _bindings = mutableListOf<Any>()
    val bindings: List<Any> by this::_bindings

    fun columns(vararg column: Column<*>): InsertBuilder = columns(column.asList())
    fun columns(columns: List<Column<*>>): InsertBuilder {
        this.columns.addAll(columns)
        return this
    }

    fun values(vararg value: Any): InsertBuilder = values(value.asList())
    fun values(values: List<Any>): InsertBuilder {
        val expressions = values.map { if (it is Field<*>) it else value(it) }
        return expressionValues(expressions)
    }

    private fun expressionValues(fields: List<Field<*>>): InsertBuilder {
        resolveColumns()
        require(fields.size == columns.size) {
            "Passed ${fields.size} values but expected ${columns.size} columns"
        }

        values.add(fields)

        return this
    }

    fun toSql(): String {
        resolveColumns()
        val sb = StringBuilder(1024)
        appendInsertInto(sb)
        appendColumns(sb)
        appendValues(sb)
        return sb.toString()
    }

    private fun appendInsertInto(sb: StringBuilder) {
        sb.append("INSERT INTO ").append(table.tableName).append(" AS ").append(table.tableAlias)
    }

    private fun appendColumns(sb: StringBuilder) {
        sb.append(" (")
        val lastIndex = columns.lastIndex
        for (i in 0 until lastIndex) {
            sb.append(columns[i].name)
            sb.append(',')
        }
        sb.append(columns[lastIndex].name)
        sb.append(')')
    }

    private fun resolveColumns() {
        if (columns.isNotEmpty()) return
        for (column in table.columns) {
            if (column.isGenerated) continue
            columns.add(column)
        }
    }

    private fun appendValues(sb: StringBuilder) {
        sb.append(" VALUES ")
        if (values.isEmpty()) {
            appendDefaultValues(sb)
        } else {
            appendBindingValues(sb)
        }
    }

    private fun appendDefaultValues(sb: StringBuilder) {
        sb.append("(?")
        for (i in 1 until columns.size) {
            sb.append(",?")
        }
        sb.append(')')
    }

    private fun appendBindingValues(sb: StringBuilder) {
        val lastIndex = values.lastIndex
        for (i in 0 until lastIndex) {
            appendRowValues(sb, values[i])
            sb.append(',')
        }
        appendRowValues(sb, values[lastIndex])
    }

    private fun appendRowValues(sb: StringBuilder, fields: List<Field<*>>) {
        sb.append('(')
        val lastIndex = fields.lastIndex
        for (i in 0 until lastIndex) {
            context.toSqlInsertValuesVisitor.visit(sb, fields[i], _bindings)
            sb.append(',')
        }
        context.toSqlInsertValuesVisitor.visit(sb, fields[lastIndex], _bindings)
        sb.append(')')
    }

}
