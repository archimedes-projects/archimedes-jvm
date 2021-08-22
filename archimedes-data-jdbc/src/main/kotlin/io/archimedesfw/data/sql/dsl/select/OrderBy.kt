package io.archimedesfw.data.sql.dsl.select

interface OrderBy {

    enum class Order(val sql: String) {
        ASC(" ASC"),
        DESC(" DESC")
    }

}
