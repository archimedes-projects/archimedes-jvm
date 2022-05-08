package io.archimedesfw.data.sql.criteria

data class Order(
    val selection: Selection<*>,
    val direction: Direction
) {

    enum class Direction(val sql: String) {
        ASC(" ASC"),
        DESC(" DESC")
    }

}
