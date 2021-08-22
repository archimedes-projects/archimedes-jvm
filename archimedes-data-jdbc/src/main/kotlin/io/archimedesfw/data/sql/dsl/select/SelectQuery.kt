package io.archimedesfw.data.sql.dsl.select

import io.archimedesfw.data.sql.dsl.SqlContext
import io.archimedesfw.data.sql.dsl.field.SelectField
import io.archimedesfw.data.sql.dsl.predicate.PredicateVisitor
import io.archimedesfw.data.sql.dsl.table.Table

class SelectQuery(
    val context: SqlContext,
    val where: WhereClause = WhereClause("", context.toSqlPredicateVisitor),
    val orderBy: OrderByClause = OrderByClause("", context.toSqlOrderByVisitor)
) {
    lateinit var table: Table

    val selects: MutableList<SelectField<*>> = mutableListOf()
    val joins: MutableList<Join> = mutableListOf()

    var selectSql: String = ""
        private set

    var whereSql: String = ""
        private set

    var orderBySql: String = ""
        private set

    val bindings: MutableList<Any> = mutableListOf()

    fun build() {
        val sb = StringBuilder(1024)
        bindings.clear()

        appendSelect(sb)
        appendFrom(sb)

        appendJoin(sb, context.toSqlPredicateVisitor, bindings)

        selectSql = sb.toString()

        sb.setLength(0)
        where.build(sb, bindings)
        whereSql = sb.toString()

        sb.setLength(0)
        orderBy.build(sb)
        orderBySql = sb.toString()
    }

    private fun appendSelect(sb: StringBuilder) {
        sb.append("SELECT ")
        val fields = resolveSelectFields()

        val lastIndex = fields.lastIndex
        for (i in 0 until lastIndex) {
            context.toSqlSelectVisitor.visit(sb, fields[i])
            sb.append(',')
        }
        context.toSqlSelectVisitor.visit(sb, fields[lastIndex])
    }

    private fun resolveSelectFields(): List<SelectField<*>> = selects.ifEmpty {
        mutableListOf<SelectField<*>>().apply {
            addAll(table.columns)
            joins.forEach {
                addAll(it.table.columns)
            }
        }
    }

    private fun appendFrom(sb: StringBuilder) {
        sb.append(" FROM ").append(table.tableName).append(' ').append(table.tableAlias)
    }

    private fun appendJoin(sb: StringBuilder, predicateVisitor: PredicateVisitor, bindings: MutableList<Any>) {
        joins.forEach {
            sb.append(' ').append(it.type.sql)
            sb.append(' ').append(it.table.tableName).append(' ').append(it.table.tableAlias)
            sb.append(" ON ")
            predicateVisitor.visit(sb, it.on, bindings)
        }
    }

    fun toSql(): String {
        build()
        val sb = StringBuilder(selectSql.length + whereSql.length + orderBySql.length + 2)
        return sb.append(selectSql).append(' ').append(whereSql).append(' ').append(orderBySql).toString()
    }

}
