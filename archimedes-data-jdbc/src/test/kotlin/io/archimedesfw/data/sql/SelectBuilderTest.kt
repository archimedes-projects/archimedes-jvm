package io.archimedesfw.data.sql

import io.archimedesfw.data.sql.TBuilding.Companion.tBuilding
import io.archimedesfw.data.sql.TRoom.Companion.tRoom
import io.archimedesfw.data.sql.dsl.SqlCreate
import io.archimedesfw.data.sql.dsl.field.Fields
import io.archimedesfw.data.sql.dsl.predicate.Predicates
import org.junit.jupiter.api.Test
import kotlin.system.measureTimeMillis

internal class SelectBuilderTest {

    private val create = SqlCreate()

    @Test
    fun `build sql`() {
        val fDistance = Fields.int(
            "earth_distance(ll_to_earth(?, ?), ll_to_earth(coords_latitude, coords_longitude))::INTEGER",
            "distance"
        )

        val iterations = 1_000_000
        val t1 = measureTimeMillis {
            for (i in 0..iterations) {
                val sql = create
                    .select(fDistance).select(tBuilding.columns).select(tRoom.columns)
                    .from(tBuilding)
                    .innerJoin(tRoom)
                    .on(
                        tBuilding.id.eq(tRoom.buildingId),
                        Predicates.predicate("earth_box(ll_to_earth (?, ?), ?) @> ll_to_earth (${tBuilding.coordsLatitude.sql}, ${tBuilding.coordsLongitude.sql})"),
                        Predicates.predicate("earth_distance(ll_to_earth (?, ?), ll_to_earth (${tBuilding.coordsLatitude.sql}, ${tBuilding.coordsLongitude.sql})) < ?")
                    )
                    .where(tRoom.name.eq("ccc"))
                    .orderBy(fDistance.asc())
                    .toSql()
            }
        }
        println(t1)

        val sql2 = create
            .select(fDistance).select(tBuilding.columns).select(tRoom.columns)
            .from(tBuilding)
            .innerJoin(tRoom)
            .on(
                tBuilding.id.eq(tRoom.buildingId),
                Predicates.predicate("earth_box(ll_to_earth (?, ?), ?) @> ll_to_earth (${tBuilding.coordsLatitude.sql}, ${tBuilding.coordsLongitude.sql})"),
                Predicates.predicate("earth_distance(ll_to_earth (?, ?), ll_to_earth (${tBuilding.coordsLatitude.sql}, ${tBuilding.coordsLongitude.sql})) < ?")
            )
            .build()

        val t2 = measureTimeMillis {
            for (i in 0..iterations) {
                sql2
                    .where(tRoom.name.eq("ccc"))
                    .orderBy(fDistance.asc())
                    .toSql()
            }
        }
        println(t2)

//        assertEquals(SQL, sql)
    }

    private companion object {
        private const val SQL = "SELECT " +
                "earth_distance(ll_to_earth(?, ?), ll_to_earth(coords_latitude, coords_longitude))::INTEGER AS distance," +
                "building_.id AS building_id," +
                "building_.name AS building_name," +
                "building_.coords_latitude AS building_coords_latitude," +
                "building_.coords_longitude AS building_coords_longitude," +
                "room_.id AS room_id," +
                "room_.name AS room_name," +
                "room_.capacity AS room_capacity," +
                "room_.building_id AS room_building_id " +
                "FROM building building_ " +
                "INNER JOIN room room_ ON (building_.id=room_.building_id " +
                "AND earth_box(ll_to_earth (?, ?), ?) @> ll_to_earth (building_.coords_latitude, building_.coords_longitude) " +
                "AND earth_distance(ll_to_earth (?, ?), ll_to_earth (building_.coords_latitude, building_.coords_longitude)) < ?) " +
                "ORDER BY distance ASC"
    }

}

