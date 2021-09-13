package io.archimedesfw.data.jdbc.spring

import io.archimedesfw.data.Criteria
import io.archimedesfw.data.Criterion
import io.archimedesfw.data.Criterion.Operator.EQ
import io.archimedesfw.data.IdGenerator
import io.archimedesfw.data.Order
import io.archimedesfw.data.Order.Direction.DESC
import io.archimedesfw.data.jdbc.JdbcRepositoryVersioned
import io.archimedesfw.data.jdbc.JdbcTemplate
import io.archimedesfw.data.jdbc.RowMapper
import io.archimedesfw.data.jdbc.Table
import io.archimedesfw.data.jdbc.getVersion
import io.archimedesfw.data.jdbc.setVersion
import jakarta.inject.Singleton
import java.sql.PreparedStatement
import java.sql.ResultSet

@Singleton
internal class JdbcFooVersionedRepository(
    jdbc: JdbcTemplate
) : JdbcRepositoryVersioned<Int, FooVersionedEntity>(jdbc) {

    override val table = TABLE
    override val rowMapper: RowMapper<FooVersionedEntity> = FooVersionedEntityMapper()
    override val idGenerator: IdGenerator<Int> = RandomIdGenerator()

    override fun identityCriteria(id: Int): Criteria<FooVersionedEntity> = ById(id)

    override fun setValuesToInsert(ps: PreparedStatement, entity: FooVersionedEntity, id: Int) {
        ps.setInt(1, id)
        ps.setVersion(2, entity.version)
        ps.setInt(3, entity.data)
    }

    override fun setValuesToUpdate(ps: PreparedStatement, entity: FooVersionedEntity) {
        setValuesToInsert(ps, entity, entity.id)
        ps.setInt(4, entity.id)
        ps.setObject(5, entity.version.start)
    }

    private class FooVersionedEntityMapper : RowMapper<FooVersionedEntity> {
        override fun mapRow(rs: ResultSet, rowIndex: Int): FooVersionedEntity {
            val id = rs.getInt(1)
            val version = rs.getVersion(2)
            val data = rs.getInt(4)
            return FooVersionedEntity(id, version, data)
        }
    }


    private companion object {
        private val TABLE = Table.Builder(
            "spring_foo_versioned_entity",
            listOf("id", "version_start", "version_end", "data")
        )
            .order(Order("version_start", DESC))
            .updateIdentityCondition("id=? and version_start=?")
            .of()
    }

    class ById(id: Int) : Criterion<FooVersionedEntity>("id", EQ, id)

}
