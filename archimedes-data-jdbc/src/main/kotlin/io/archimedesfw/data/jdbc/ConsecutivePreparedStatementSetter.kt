package io.archimedesfw.data.jdbc

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

    fun set(value: String): Unit = ps.setString(parameterIndex++, value)
    fun set(value: Double): Unit = ps.setDouble(parameterIndex++, value)
    fun set(value: Int): Unit = ps.setInt(parameterIndex++, value)

}
