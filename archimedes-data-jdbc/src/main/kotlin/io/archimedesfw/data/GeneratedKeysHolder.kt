package io.archimedesfw.data

import io.archimedesfw.data.sql.dsl.table.Column
import io.archimedesfw.data.sql.dsl.table.Table

class GeneratedKeysHolder(
    val columnNames: Array<String>
) : GeneratedKeys {

    constructor(table: Table) : this(table.generatedColumns)
    constructor(columns: List<Column<*>>) : this(columns.map { it.name }.toTypedArray())

    private val keysRowsHolder = mutableListOf<Map<String, Any>>()

    override val keysRows: List<Map<String, Any>> by ::keysRowsHolder

    fun addRow(keys: Map<String, Any>) {
        keysRowsHolder.add(keys)
    }

    fun addAllRows(keysList: List<Map<String, Any>>) {
        keysRowsHolder.addAll(keysList)
    }

    fun clear() {
        keysRowsHolder.clear()
    }

}
