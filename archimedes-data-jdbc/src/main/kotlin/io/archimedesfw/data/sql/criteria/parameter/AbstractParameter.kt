package io.archimedesfw.data.sql.criteria.parameter

abstract class AbstractParameter<T>(
    override var value: T?,
    override val sql: String,
    override val alias: String
) : ParameterExpression<T> {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AbstractParameter<*>

        if (value != other.value) return false
        if (sql != other.sql) return false
        if (alias != other.alias) return false

        return true
    }

    override fun hashCode(): Int {
        var result = value?.hashCode() ?: 0
        result = 31 * result + sql.hashCode()
        result = 31 * result + alias.hashCode()
        return result
    }

    protected fun propertiesToString() = "value=$value, sql='$sql', alias='$alias'"

    override fun toString(): String = "${this::class.simpleName}(${propertiesToString()})"

}
