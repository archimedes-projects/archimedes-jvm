package io.archimedesfw.data.sql

import io.archimedesfw.data.sql.dsl.table.Table

internal class TRoom : Table("room") {
    val id = intColumn("id", isGenerated = true)
    val name = stringColumn("name")
    val capacity = intColumn("capacity")
    val buildingId = intColumn("building_id")

    companion object {
        val tRoom = TRoom()
    }
}
