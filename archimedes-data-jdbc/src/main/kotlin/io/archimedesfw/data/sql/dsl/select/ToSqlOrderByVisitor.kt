package io.archimedesfw.data.sql.dsl.select

internal class ToSqlOrderByVisitor : OrderByVisitor {

    override fun visit(sb: StringBuilder, orderBy: OrderBy) {
        when (orderBy) {
            is CompositeOrderBy -> {
                val lastIndex = orderBy.list.lastIndex
                for (i in 0 until lastIndex) {
                    visit(sb, orderBy.list[i])
                    sb.append(',')
                }
                visit(sb, orderBy.list[lastIndex])
            }

            is FieldOrderBy -> sb.append(orderBy.field.alias).append(orderBy.order.sql)

            else -> throw UnsupportedOperationException(
                "${this::class.simpleName} doesn't support visit ${orderBy::class.simpleName} type"
            )
        }
    }

}

