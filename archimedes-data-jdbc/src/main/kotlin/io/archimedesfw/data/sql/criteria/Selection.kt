package io.archimedesfw.data.sql.criteria


interface Selection<T> {
    val sql: String
    val alias: String
}
