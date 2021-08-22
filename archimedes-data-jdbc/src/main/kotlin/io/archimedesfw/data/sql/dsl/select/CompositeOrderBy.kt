package io.archimedesfw.data.sql.dsl.select

internal class CompositeOrderBy(
    val list: List<OrderBy>
) : OrderBy {

    constructor(vararg orderBy: OrderBy) : this(orderBy.asList())

}
