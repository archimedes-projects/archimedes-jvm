package io.archimedesfw.data.sql.dsl.select

import io.archimedesfw.data.sql.dsl.predicate.AndPredicate
import io.archimedesfw.data.sql.dsl.predicate.Predicate
import io.archimedesfw.data.sql.dsl.table.Table

internal class OngoingJoinBuilder(
    private val selectBuilder: SelectBuilder,
    private val joinType: Join.Type,
    private val joinTable: Table
) : OngoingJoinStep {

    override fun on(predicate: Predicate): JoinStep {
        selectBuilder.addJoin(Join(joinType, joinTable, predicate))
        return selectBuilder
    }

}
