package io.archimedesfw.data.sql.criteria

open class UnaryOperator(
    val sql: String
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UnaryOperator

        if (sql != other.sql) return false

        return true
    }

    override fun hashCode(): Int = sql.hashCode()

    override fun toString(): String = "${this::class.simpleName}(sql='$sql')"

    companion object {
        // Generic operators
        val IS_NULL: UnaryOperator = UnaryOperator(" IS NULL")
        val IS_NOT_NULL: UnaryOperator = UnaryOperator(" IS NOT NULL")
    }

}
