package io.archimedesfw.data.sql.criteria.builder

import io.archimedesfw.data.sql.Sql
import io.archimedesfw.data.sql.criteria.CriteriaDelete
import io.archimedesfw.data.sql.criteria.CriteriaInsert
import io.archimedesfw.data.sql.criteria.CriteriaQuery
import io.archimedesfw.data.sql.criteria.CriteriaUpdate

class SqlBuilder {

    private val whereBuilder = SqlWhereBuilder()
    private val orderByBuilder = SqlOrderByBuilder()
    private val selectBuilder = SqlSelectBuilder(whereBuilder, orderByBuilder)
    private val insertBuilder = SqlInsertBuilder()
    private val updateBuilder = SqlUpdateBuilder(whereBuilder)
    private val deleteBuilder = SqlDeleteBuilder(whereBuilder)

    fun toPredicateQueryFactory(query: CriteriaQuery): PredicateQueryFactory =
        selectBuilder.toPredicateQueryFactory(query)

    fun toSql(query: CriteriaQuery): Sql =
        selectBuilder.toSql(query)

    fun toSql(query: StaticPredicateQuery): Sql =
        selectBuilder.toSql(query)

    fun toSql(query: CriteriaInsert): Sql =
        insertBuilder.toSql(query)

    fun toSql(query: CriteriaUpdate): Sql =
        updateBuilder.toSql(query)

    fun toSql(query: CriteriaDelete): Sql =
        deleteBuilder.toSql(query)

}
