package io.archimedesfw.data.sql.dsl.select

fun interface OrderByVisitor {
    fun visit(sb: StringBuilder, orderBy: OrderBy)
}
