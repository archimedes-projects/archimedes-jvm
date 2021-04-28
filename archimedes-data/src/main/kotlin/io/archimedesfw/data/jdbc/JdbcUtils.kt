package io.archimedesfw.data.jdbc

import io.archimedesfw.data.GeneratedKeysHolder
import java.sql.PreparedStatement
import java.sql.ResultSetMetaData

fun lookupColumnName(rsmd: ResultSetMetaData, columnIndex: Int): String {
    var name = rsmd.getColumnLabel(columnIndex)
    if (name.isEmpty()) {
        name = rsmd.getColumnName(columnIndex)
    }
    return name
}

fun PreparedStatement.extractGeneratedKeys(keysHolder: GeneratedKeysHolder) {
    val rs = this.generatedKeys
    val rsmd = rs.metaData
    val columnsCount = rsmd.columnCount
    keysHolder.clear()

    while (rs.next()) {
        val columnValues = LinkedHashMap<String, Any>(columnsCount)

        for (i in 1..columnsCount) {
            val name = lookupColumnName(rsmd, i)
            val value = rs.getObject(i)
            columnValues.putIfAbsent(name, value)
        }

        keysHolder.addRow(columnValues)
    }
}

fun checkRows(expectedCount: Int, actualCount: Int, lazyDetail: (() -> Any)? = null) {
    check(expectedCount == actualCount) {
        val msgBuilder = StringBuilder("Expected rows affected")
            .append(expectedCount).append(" but was ").append(actualCount)
        if (lazyDetail != null) msgBuilder.append(", ").append(lazyDetail())
        msgBuilder.toString()
    }
}
