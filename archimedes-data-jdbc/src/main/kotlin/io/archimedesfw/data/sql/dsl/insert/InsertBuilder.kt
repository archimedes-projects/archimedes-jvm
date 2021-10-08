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
        val fields = values.map { if (it is Field<*>) it else value(it) }
        return addRowValues(fields)
    }

    private fun addRowValues(fields: List<Field<*>>): InsertBuilder {
        resolveColumns()
        require(fields.size == columns.size) {
            "INSERT has ${columns.size} columns but only ${values.size} values are specified"
        }
        values.add(fields)
        return this
    }

    fun toSql(): String {
        resolveColumns()
        val sb = StringBuilder(1024)
        appendInsert(sb)
        appendColumns(sb)
        appendValues(sb)
        return sb.toString()
    }

    private fun appendInsert(sb: StringBuilder) {
        sb.append("INSERT INTO ").append(table.tableName).append(" AS ").append(table.tableAlias)
    }

    private fun appendColumns(sb: StringBuilder) {
        sb.append(" (")
        for (i in 0 until columns.lastIndex) {
            sb.append(columns[i].name)
            sb.append(',')
        }
        sb.append(columns[columns.lastIndex].name)
        sb.append(')')
    }

    private fun resolveColumns() {
        if (columns.isNotEmpty()) return
        columns.addAll(table.updatableColumns)
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
        sb.append('(')
        for (i in 0 until columns.lastIndex) {
            sb.append("?,")
        }
        sb.append("?)")
    }

    private fun appendBindingValues(sb: StringBuilder) {
        for (i in 0 until values.lastIndex) {
            appendRowValues(sb, values[i])
            sb.append(',')
        }

        appendRowValues(sb, values[values.lastIndex])
    }

    private fun appendRowValues(sb: StringBuilder, fields: List<Field<*>>) {
        sb.append('(')
        for (i in 0 until fields.lastIndex) {
            context.toSqlInsertValuesVisitor.visit(sb, fields[i], _bindings)
            sb.append(',')
        }
        context.toSqlInsertValuesVisitor.visit(sb, fields[fields.lastIndex], _bindings)
        sb.append(')')
    }

}
