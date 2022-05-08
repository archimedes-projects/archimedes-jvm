package io.archimedesfw.data.sql

internal class BookTable private constructor() : Table("book") {
    val id = column<Int>("id", isGenerated = true)
    val authorId = column<Int>("author_id")
    val title = column<String>("title")
    val pagesCount = column<Int>("pages_count")

    internal companion object {
        internal val tBook = BookTable()
    }
}
