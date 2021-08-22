package io.archimedesfw.data.sql

import io.archimedesfw.data.sql.dsl.table.Table

internal class TBuilding : Table("building") {
    val id = intColumn("id", isGenerated = true)
    val name = stringColumn("name")
    val coordsLatitude = doubleColumn("coords_latitude")
    val coordsLongitude = doubleColumn("coords_longitude")

    companion object {
        val tBuilding = TBuilding()
    }
}
