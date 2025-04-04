package io.archimedesfw.data.sql.criteria.parameter

import java.sql.PreparedStatement

class ArrayParameter<T>(
    val parameters: List<ParameterExpression<T>>,
) : ParameterExpression<T> {

    override var value: T?
        get() = throw UnsupportedOperationException("Cannot get value of ${this::class.simpleName}.")
        set(value) = throw UnsupportedOperationException("Cannot set value of ${this::class.simpleName}.")

    override val alias: String
        get() = throw UnsupportedOperationException("Cannot get alias of ${this::class.simpleName}.")

    override val sql: String =
        if (parameters.isNotEmpty()) parameters.joinToString(prefix = "(", postfix = ")") { "?" }
        else "(null)"

    override fun set(ps: PreparedStatement, index: Int): Unit =
        throw UnsupportedOperationException("Cannot set PreparedStatement of ${this::class.simpleName}.")

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ArrayParameter<*>) return false

        if (parameters != other.parameters) return false

        return true
    }

    override fun hashCode(): Int {
        return parameters.hashCode()
    }

}
