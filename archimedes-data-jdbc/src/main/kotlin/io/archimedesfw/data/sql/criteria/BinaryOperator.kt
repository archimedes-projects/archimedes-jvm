package io.archimedesfw.data.sql.criteria

open class BinaryOperator(
    val sql: String
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BinaryOperator

        if (sql != other.sql) return false

        return true
    }

    override fun hashCode(): Int = sql.hashCode()

    override fun toString(): String = "${this::class.simpleName}(sql='$sql')"

    companion object {
        // Generic operators
        val EQ: BinaryOperator = BinaryOperator("=")
        val NE: BinaryOperator = BinaryOperator("!=")

        val LT: BinaryOperator = BinaryOperator("<")
        val LE: BinaryOperator = BinaryOperator("<=")
        val GT: BinaryOperator = BinaryOperator(">")
        val GE: BinaryOperator = BinaryOperator(">=")

        val LIKE: BinaryOperator = BinaryOperator("LIKE")

        val IN: BinaryOperator = BinaryOperator(" IN ")
    }

}
