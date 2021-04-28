package io.archimedesfw.data.jdbc

import io.archimedesfw.commons.lang.getAny
import io.archimedesfw.commons.lang.getAnyOrNull
import io.archimedesfw.data.Version
import java.sql.PreparedStatement
import java.sql.ResultSet


fun PreparedStatement.setVersion(parameterIndex: Int, version: Version) {
    var index = parameterIndex
    this.setObject(index++, version.start)
//    this.setObject(index, version.end)
}

fun ResultSet.getVersion(columnIndex: Int): Version {
    var index = columnIndex
    return Version(
        this.getAny(index++),
        this.getAnyOrNull(index)
    )
}

const val VERSION_COLUMNS: Int = 2
