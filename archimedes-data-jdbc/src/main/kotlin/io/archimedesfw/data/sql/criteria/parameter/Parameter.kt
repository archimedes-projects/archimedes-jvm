package io.archimedesfw.data.sql.criteria.parameter

import java.sql.PreparedStatement

interface Parameter<T> {
    var value: T?

    fun set(ps: PreparedStatement, index: Int)
}
