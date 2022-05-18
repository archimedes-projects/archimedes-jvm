package io.archimedesfw.data

data class GeneratedKeysHolder(
    val columnNames: Array<String>,
    override val rows: MutableList<Map<String, Any>> = mutableListOf()
) : GeneratedKeys {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GeneratedKeysHolder

        // Compares the array content, not just the array reference
        if (!columnNames.contentEquals(other.columnNames)) return false

        if (rows != other.rows) return false

        return true
    }

    override fun hashCode(): Int {
        var result = columnNames.contentHashCode()
        result = 31 * result + rows.hashCode()
        return result
    }

}
