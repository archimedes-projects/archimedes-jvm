package io.archimedesfw.data.sql.dsl

import io.archimedesfw.data.sql.dsl.insert.InsertValuesVisitor
import io.archimedesfw.data.sql.dsl.predicate.PredicateVisitor
import io.archimedesfw.data.sql.dsl.select.OrderByVisitor
import io.archimedesfw.data.sql.dsl.select.SelectVisitor

interface SqlContext {

    val toSqlSelectVisitor: SelectVisitor
    val toSqlPredicateVisitor: PredicateVisitor
    val toSqlOrderByVisitor: OrderByVisitor
    val toSqlInsertValuesVisitor: InsertValuesVisitor

}
