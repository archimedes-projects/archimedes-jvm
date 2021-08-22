package io.archimedesfw.data.sql.dsl.field

internal open class ValueField<T>(
    val value: T
) : Field<T>
