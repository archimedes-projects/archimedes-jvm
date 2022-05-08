package io.archimedesfw.data.jdbc

import java.math.BigDecimal
import java.sql.PreparedStatement

class ConsecutivePreparedStatementSetter(
    private val setValues: (ConsecutivePreparedStatementSetter) -> Unit
) : PreparedStatementSetter {

    private lateinit var ps: PreparedStatement
    private var parameterIndex = 1

    override fun setValues(ps: PreparedStatement) {
        this.ps = ps
        setValues(this)
    }

    fun set(value: BigDecimal): Unit = ps.setBigDecimal(parameterIndex++, value)
    fun set(value: Double): Unit = ps.setDouble(parameterIndex++, value)
    fun set(value: Enum<*>): Unit = ps.setString(parameterIndex++, value.name)
    fun set(value: Int): Unit = ps.setInt(parameterIndex++, value)
    fun set(value: String): Unit = ps.setString(parameterIndex++, value)
    fun setAny(value: Any): Unit = ps.setObject(parameterIndex++, value)

}
