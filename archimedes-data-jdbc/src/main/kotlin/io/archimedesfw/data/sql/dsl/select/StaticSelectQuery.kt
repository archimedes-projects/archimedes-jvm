package io.archimedesfw.data.sql.dsl.select

import io.archimedesfw.data.sql.dsl.SqlContext

class StaticSelectQuery(
    val staticSelect: String,
    val where: WhereClause,
    val orderBy: OrderByClause,
    val bindings: MutableList<Any>
) {

    constructor(
        context: SqlContext,
        staticSelect: String,
        staticWhere: String,
        staticOrderBy: String,
        bindings: MutableList<Any>
    ) : this(
        staticSelect,
        WhereClause(staticWhere, context.toSqlPredicateVisitor),
        OrderByClause(staticOrderBy, context.toSqlOrderByVisitor),
        bindings
    )

    fun toSql(): String {
        val sb = StringBuilder(1024).append(staticSelect)
        where.build(sb, bindings)
        orderBy.build(sb)
        return sb.toString()
    }

}
