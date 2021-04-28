package io.archimedesfw.data

data class Order(
    val by: String,
    val direction: Direction
) {

    enum class Direction { ASC, DESC }
}
