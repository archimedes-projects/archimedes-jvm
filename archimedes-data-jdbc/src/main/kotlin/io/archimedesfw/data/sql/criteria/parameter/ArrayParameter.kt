package io.archimedesfw.data.sql.criteria.parameter

import java.sql.PreparedStatement

class ArrayParameter<T>(
    val parameters: List<ParameterExpression<T>>,
) : ParameterExpression<T> {

    override var value: T?
        get() = throw UnsupportedOperationException("Cannot get value of ${this::class.simpleName}")
        set(value) = throw UnsupportedOperationException("Cannot set value of ${this::class.simpleName}")

    override val alias: String
        get() = throw UnsupportedOperationException("Cannot get alias of ${this::class.simpleName}")

    override val sql: String = parameters.joinToString(prefix = "(", postfix = ")") { "?" }

    override fun set(ps: PreparedStatement, index: Int): Unit =
        throw UnsupportedOperationException("Cannot set PreparedStatement of ${this::class.simpleName}")

}
