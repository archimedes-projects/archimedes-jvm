package io.archimedesfw.data.sql.criteria

open class SqlExpression<T>(
    override val sql: String,
    override val alias: String
) : Expression<T> {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SqlExpression<*>

        if (sql != other.sql) return false
        if (alias != other.alias) return false

        return true
    }

    override fun hashCode(): Int {
        var result = sql.hashCode()
        result = 31 * result + alias.hashCode()
        return result
    }

    override fun toString(): String = "${this::class.simpleName}(sql='$sql', alias='$alias')"

}
