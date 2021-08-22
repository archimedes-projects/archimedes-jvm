package io.archimedesfw.data.jdbc

import io.archimedesfw.data.AbstractRepositoryVersioned
import io.archimedesfw.data.Criteria
import io.archimedesfw.data.Criterion.Operator.EQ
import io.archimedesfw.data.EntityVersioned
import io.archimedesfw.data.FinderVersioned.ByStart
import io.archimedesfw.data.GeneratedKeys
import io.archimedesfw.data.GeneratedKeysHolder
import java.sql.PreparedStatement

abstract class JdbcRepositoryVersioned<K, E : EntityVersioned<K>>(
    protected val jdbc: JdbcTemplate
) : AbstractRepositoryVersioned<K, E>() {

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

    override fun expireVersionRow(current: E, next: E, byIdentity: Criteria<E>) {
        val byPrevious = byIdentity.and(ByStart(EQ, current.version.start))
        val expire = QueryBuilder("update ${table.table} set version_end=?").where(byPrevious)
        val sql = expire.toSql()

        val bindings = ArrayList<Any?>(expire.bindings.size + 1)
        bindings.add(next.version.start)
        bindings.addAll(expire.bindings)
        jdbc.update(sql, ArgumentPreparedStatementSetter(bindings))
    }

    protected abstract fun setValuesToInsert(ps: PreparedStatement, entity: E, id: K)
    protected abstract fun setValuesToUpdate(ps: PreparedStatement, entity: E)

}
