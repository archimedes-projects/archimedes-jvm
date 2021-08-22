package io.archimedesfw.data.sql.dsl.select

import io.archimedesfw.data.sql.dsl.table.Table

interface JoinStep : WhereStep {

    fun leftJoin(table: Table): OngoingJoinStep
    fun rightJoin(table: Table): OngoingJoinStep
    fun innerJoin(table: Table): OngoingJoinStep
    fun outerJoin(table: Table): OngoingJoinStep

    fun build(): WhereStep

}
