package io.archimedesfw.data.jdbc

import io.archimedesfw.commons.lang.getAny
import io.archimedesfw.commons.lang.getAnyOrNull
import io.archimedesfw.commons.lang.getDoubleOrNull
import io.archimedesfw.commons.lang.getIntOrNull
import io.archimedesfw.data.sql.criteria.Selection
import java.sql.ResultSet
import java.time.LocalTime

fun ResultSet.get(selection: Selection<Double>): Double = getDouble(selection.alias)
fun ResultSet.getOrNull(selection: Selection<Double>): Double? = getDoubleOrNull(selection.alias)

inline fun <reified E : Enum<E>> ResultSet.get(selection: Selection<E>): E =
    enumValueOf(getString(selection.alias))

inline fun <reified E : Enum<E>> ResultSet.getOrNull(selection: Selection<E>): E? {
    val s = getString(selection.alias) ?: return null
    return enumValueOf<E>(s)
}

fun ResultSet.get(selection: Selection<Int>): Int = getInt(selection.alias)
fun ResultSet.getOrNull(selection: Selection<Int>): Int? = getIntOrNull(selection.alias)

fun ResultSet.get(selection: Selection<LocalTime>): LocalTime = getAny(selection.alias)
fun ResultSet.getOrNull(selection: Selection<LocalTime>): LocalTime? = getAnyOrNull(selection.alias)

fun ResultSet.get(selection: Selection<String>): String = getString(selection.alias)
fun ResultSet.getOrNull(selection: Selection<String>): String? = getString(selection.alias)
