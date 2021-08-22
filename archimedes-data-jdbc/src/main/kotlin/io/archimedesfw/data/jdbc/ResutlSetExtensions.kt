package io.archimedesfw.data.sql

import io.archimedesfw.data.sql.dsl.field.SelectField
import java.sql.ResultSet

inline fun <reified T> ResultSet.get(selectField: SelectField<T>): T = selectField.get(this)
