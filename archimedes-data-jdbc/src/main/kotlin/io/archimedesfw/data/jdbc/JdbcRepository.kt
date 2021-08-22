package io.archimedesfw.data.jdbc

import io.archimedesfw.data.AbstractRepository
import io.archimedesfw.data.Criteria
import io.archimedesfw.data.Entity
import io.archimedesfw.data.GeneratedKeys
import io.archimedesfw.data.GeneratedKeysHolder
import java.sql.PreparedStatement

abstract class JdbcRepository<K, E : Entity<K>>(
    protected val jdbc: JdbcTemplate
) : AbstractRepository<K, E>() {

    protected abstract val table: Table
    protected abstract val rowMapper: RowMapper<E>

    override fun find(criteria: Criteria<E>, limit: Int): List<E> {
        val qry = QueryBuilder(table.select)
            .where(criteria)
            .order(table.order)
            .limit(limit)

        val sql = qry.toSql()

        return jdbc.query(sql, ArgumentPreparedStatementSetter(qry.bindings), rowMapper)
    }

    override fun insertRow(entity: E, id: K): GeneratedKeys {
        val keysHolder = GeneratedKeysHolder(table.generatedColumns)
        val updatedRows = jdbc.update(table.insert, keysHolder) {
            setValuesToInsert(it, entity, id)
        }
        checkRows(1, updatedRows) { "inserting $entity" }
        return keysHolder
    }

    override fun updateRow(entity: E) {
        val updatedRows = jdbc.update(table.update) { setValuesToUpdate(it, entity) }
        checkRows(1, updatedRows) { "updating $entity" }
    }

    protected abstract fun setValuesToInsert(ps: PreparedStatement, entity: E, id: K)
    protected abstract fun setValuesToUpdate(ps: PreparedStatement, entity: E)

}
