package io.archimedesfw.data.jdbc

import io.archimedesfw.commons.lang.getDoubleOrNull
import io.archimedesfw.commons.lang.getIntOrNull
import io.archimedesfw.data.sql.dsl.field.SelectField
import java.sql.ResultSet

inline fun ResultSet.get(selectField: SelectField<Int>): Int = getInt(selectField.alias)
inline fun ResultSet.getOrNull(selectField: SelectField<Int>): Int? = getIntOrNull(selectField.alias)

inline fun ResultSet.get(selectField: SelectField<Double>): Double = getDouble(selectField.alias)
inline fun ResultSet.getOrNull(selectField: SelectField<Double>): Double? = getDoubleOrNull(selectField.alias)

inline fun ResultSet.get(selectField: SelectField<String>): String = getString(selectField.alias)
inline fun ResultSet.getOrNull(selectField: SelectField<String>): String? = getString(selectField.alias)
