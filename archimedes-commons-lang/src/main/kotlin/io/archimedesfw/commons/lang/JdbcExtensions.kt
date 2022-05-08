package io.archimedesfw.commons.lang

import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Types

// PreparedStatement:

@Throws(SQLException::class)
fun PreparedStatement.setBooleanOrNull(parameterIndex: Int, x: Boolean?) {
    if (x == null) setNull(parameterIndex, Types.BOOLEAN) else setBoolean(parameterIndex, x)
}

@Throws(SQLException::class)
fun PreparedStatement.setByteOrNull(parameterIndex: Int, x: Byte?) {
    if (x == null) setNull(parameterIndex, Types.OTHER) else setByte(parameterIndex, x)
}

@Throws(SQLException::class)
fun PreparedStatement.setDoubleOrNull(parameterIndex: Int, x: Double?) {
    if (x == null) setNull(parameterIndex, Types.DOUBLE) else setDouble(parameterIndex, x)
}

@Throws(SQLException::class)
fun PreparedStatement.setEnum(parameterIndex: Int, x: Enum<*>) {
    setString(parameterIndex, x.name)
}

@Throws(SQLException::class)
fun PreparedStatement.setEnumOrNull(parameterIndex: Int, x: Enum<*>?) {
    if (x == null) setNull(parameterIndex, Types.VARCHAR) else setEnum(parameterIndex, x)
}

@Throws(SQLException::class)
fun PreparedStatement.setFloatOrNull(parameterIndex: Int, x: Float?) {
    if (x == null) setNull(parameterIndex, Types.REAL) else setFloat(parameterIndex, x)
}

@Throws(SQLException::class)
fun PreparedStatement.setIntOrNull(parameterIndex: Int, x: Int?) {
    if (x == null) setNull(parameterIndex, Types.INTEGER) else setInt(parameterIndex, x)
}

@Throws(SQLException::class)
fun PreparedStatement.setLongOrNull(parameterIndex: Int, x: Long?) {
    if (x == null) setNull(parameterIndex, Types.BIGINT) else setLong(parameterIndex, x)
}

@Throws(SQLException::class)
fun PreparedStatement.setShortOrNull(parameterIndex: Int, x: Short?) {
    if (x == null) setNull(parameterIndex, Types.SMALLINT) else setShort(parameterIndex, x)
}

// ResultSet:

@Throws(SQLException::class)
fun ResultSet.getBooleanOrNull(columnIndex: Int): Boolean? {
    val boolean = getBoolean(columnIndex)
    return if (wasNull()) null else boolean
}

@Throws(SQLException::class)
fun ResultSet.getBooleanOrNull(columnLabel: String): Boolean? {
    val boolean = getBoolean(columnLabel)
    return if (wasNull()) null else boolean
}

@Throws(SQLException::class)
fun ResultSet.getByteOrNull(columnIndex: Int): Byte? {
    val byte = getByte(columnIndex)
    return if (wasNull()) null else byte
}

@Throws(SQLException::class)
fun ResultSet.getByteOrNull(columnLabel: String): Byte? {
    val byte = getByte(columnLabel)
    return if (wasNull()) null else byte
}

@Throws(SQLException::class)
fun ResultSet.getDoubleOrNull(columnIndex: Int): Double? {
    val double = getDouble(columnIndex)
    return if (wasNull()) null else double
}

@Throws(SQLException::class)
fun ResultSet.getDoubleOrNull(columnLabel: String): Double? {
    val double = getDouble(columnLabel)
    return if (wasNull()) null else double
}

@Throws(SQLException::class)
inline fun <reified E : Enum<E>> ResultSet.getEnum(columnIndex: Int): E {
    val name = getString(columnIndex)
    return enumValueOf<E>(name)
}

@Throws(SQLException::class)
inline fun <reified E : Enum<E>> ResultSet.getEnum(columnLabel: String): E {
    val name = getString(columnLabel)
    return enumValueOf<E>(name)
}

@Throws(SQLException::class)
inline fun <reified E : Enum<E>> ResultSet.getEnumOrNull(columnIndex: Int): E? {
    val name = getString(columnIndex)
    return if (name == null) null else enumValueOf<E>(name)
}

@Throws(SQLException::class)
inline fun <reified E : Enum<E>> ResultSet.getEnumOrNull(columnLabel: String): E? {
    val name = getString(columnLabel)
    return if (name == null) null else enumValueOf<E>(name)
}

@Throws(SQLException::class)
fun ResultSet.getFloatOrNull(columnIndex: Int): Float? {
    val float = getFloat(columnIndex)
    return if (wasNull()) null else float
}

@Throws(SQLException::class)
fun ResultSet.getFloatOrNull(columnLabel: String): Float? {
    val float = getFloat(columnLabel)
    return if (wasNull()) null else float
}

@Throws(SQLException::class)
fun ResultSet.getIntOrNull(columnIndex: Int): Int? {
    val int = getInt(columnIndex)
    return if (wasNull()) null else int
}

@Throws(SQLException::class)
fun ResultSet.getIntOrNull(columnLabel: String): Int? {
    val int = getInt(columnLabel)
    return if (wasNull()) null else int
}

@Throws(SQLException::class)
fun ResultSet.getLongOrNull(columnIndex: Int): Long? {
    val long = getLong(columnIndex)
    return if (wasNull()) null else long
}

@Throws(SQLException::class)
fun ResultSet.getLongOrNull(columnLabel: String): Long? {
    val long = getLong(columnLabel)
    return if (wasNull()) null else long
}

@Throws(SQLException::class)
fun ResultSet.getShortOrNull(columnIndex: Int): Short? {
    val short = getShort(columnIndex)
    return if (wasNull()) null else short
}

@Throws(SQLException::class)
fun ResultSet.getShortOrNull(columnLabel: String): Short? {
    val short = getShort(columnLabel)
    return if (wasNull()) null else short
}

@Throws(SQLException::class)
inline fun <reified T : Any> ResultSet.getAny(columnIndex: Int): T = getObject(columnIndex, T::class.java)

@Throws(SQLException::class)
inline fun <reified T : Any> ResultSet.getAny(columnLabel: String): T = getObject(columnLabel, T::class.java)

@Throws(SQLException::class)
inline fun <reified T : Any?> ResultSet.getAnyOrNull(columnIndex: Int): T? = getObject(columnIndex, T::class.java)

@Throws(SQLException::class)
inline fun <reified T : Any?> ResultSet.getAnyOrNull(columnLabel: String): T? = getObject(columnLabel, T::class.java)
