package io.archimedesfw.data.sql

open class Table(
    val tableName: String,
    val tableAlias: String = tableName + '_',
) {
    private val _columns = mutableListOf<Column<*>>()
    private val _updatableColumns = mutableListOf<Column<*>>()
    private val _generatedColumns = mutableListOf<Column<*>>()

    val columns: List<Column<*>> by ::_columns
    val updatableColumns: List<Column<*>> by ::_updatableColumns
    val generatedColumns: List<Column<*>> by ::_generatedColumns

    protected fun <C : Column<*>> add(column: C): C {
        _columns.add(column)
        if (column.isGenerated) {
            _generatedColumns.add(column)
        } else {
            _updatableColumns.add(column)
        }
        return column
    }

    protected fun <T> column(name: String, isGenerated: Boolean = false): Column<T> =
        add(Column(name, columnPath(name), columnAlias(name), isGenerated))

    protected fun columnPath(columnName: String): String = "${tableAlias}.$columnName"

    protected fun columnAlias(columnName: String): String = "${tableAlias}$columnName"

}
