package io.archimedesfw.data.sql

internal class AuthorTable private constructor() : Table("author") {
    val id = column<Int>("id", isGenerated = true)
    val name = column<String>("name")
    val height = column<Double>("height")

    internal companion object {
        internal val tAuthor = AuthorTable()
    }
}
