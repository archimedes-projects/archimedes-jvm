package io.archimedesfw.commons.lang

import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Types

@Throws(SQLException::class)
fun PreparedStatement.setIntOrNull(parameterIndex: Int, x: Int?) {
    if (x == null) {
        this.setNull(parameterIndex, Types.INTEGER)
    } else {
        this.setInt(parameterIndex, x)
    }
}

inline fun <reified T : Any> ResultSet.getAny(columnIndex: Int): T = getObject(columnIndex, T::class.java)
inline fun <reified T : Any?> ResultSet.getAnyOrNull(columnIndex: Int): T? = getObject(columnIndex, T::class.java)
inline fun ResultSet.getIntOrNull(columnIndex: Int): Int? = getAnyOrNull(columnIndex)

inline fun <reified T : Any> ResultSet.getAny(columnLabel: String): T = getObject(columnLabel, T::class.java)
inline fun <reified T : Any?> ResultSet.getAnyOrNull(columnLabel: String): T? = getObject(columnLabel, T::class.java)
inline fun ResultSet.getIntOrNull(columnLabel: String): Int? = getAnyOrNull(columnLabel)
