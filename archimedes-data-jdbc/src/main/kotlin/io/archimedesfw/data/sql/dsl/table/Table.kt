package io.archimedesfw.data.sql.dsl.table

open class Table(
    val tableName: String,
    val tableAlias: String = tableName + '_',
) {
    private val _columns = mutableListOf<Column<*>>()
    private val _generatedColumns = mutableListOf<Column<*>>()
    private val _updateableColumns = mutableListOf<Column<*>>()

    val columns: List<Column<*>> by ::_columns
    val generatedColumns: List<Column<*>> by ::_generatedColumns
    val updateableColumns: List<Column<*>> by ::_updateableColumns

    protected fun <C : Column<*>> add(column: C): C {
        _columns.add(column)
        if (column.isGenerated) {
            _generatedColumns.add(column)
        } else {
            _updateableColumns.add(column)
        }
        return column
    }

    protected fun intColumn(label: String, isGenerated: Boolean = false): IntColumn =
        add(IntColumn(this, label, isGenerated))

    protected fun doubleColumn(label: String, isGenerated: Boolean = false): DoubleColumn =
        add(DoubleColumn(this, label, isGenerated))

    protected fun stringColumn(label: String, isGenerated: Boolean = false): StringColumn =
        add(StringColumn(this, label, isGenerated))

}
