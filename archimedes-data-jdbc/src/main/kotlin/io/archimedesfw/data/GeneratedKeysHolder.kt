package io.archimedesfw.data

class GeneratedKeysHolder(
    val columnNames: Array<String>
) : GeneratedKeys {

    private val rowsHolder = mutableListOf<Map<String, Any>>()

    override val rows: List<Map<String, Any>> by ::rowsHolder

    fun addRow(keys: Map<String, Any>) {
        rowsHolder.add(keys)
    }

    fun addAllRows(keysList: List<Map<String, Any>>) {
        rowsHolder.addAll(keysList)
    }

    fun clear() {
        rowsHolder.clear()
    }

}
