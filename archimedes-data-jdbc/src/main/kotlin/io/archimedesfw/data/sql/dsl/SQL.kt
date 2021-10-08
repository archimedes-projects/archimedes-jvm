package io.archimedesfw.data.sql.dsl

import io.archimedesfw.data.sql.dsl.insert.InsertValuesVisitor
import io.archimedesfw.data.sql.dsl.insert.ToSqlInsertValuesVisitor
import io.archimedesfw.data.sql.dsl.predicate.PredicateVisitor
import io.archimedesfw.data.sql.dsl.predicate.ToSqlPredicateVisitor
import io.archimedesfw.data.sql.dsl.select.OrderByVisitor
import io.archimedesfw.data.sql.dsl.select.SelectVisitor
import io.archimedesfw.data.sql.dsl.select.ToSqlOrderByVisitor
import io.archimedesfw.data.sql.dsl.select.ToSqlSelectVisitor

class SQL {

    companion object {
        val ANSI: SqlContext = object : SqlContext {
            override val toSqlSelectVisitor: SelectVisitor = ToSqlSelectVisitor()
            override val toSqlPredicateVisitor: PredicateVisitor = ToSqlPredicateVisitor()
            override val toSqlOrderByVisitor: OrderByVisitor = ToSqlOrderByVisitor()
            override val toSqlInsertValuesVisitor: InsertValuesVisitor = ToSqlInsertValuesVisitor()
        }
    }

}
