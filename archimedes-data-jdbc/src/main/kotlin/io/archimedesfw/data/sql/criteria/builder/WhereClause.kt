package io.archimedesfw.data.sql.criteria.builder

import io.archimedesfw.data.sql.criteria.Predicate

data class WhereClause(
    val staticWhere: String = "",
    val predicates: MutableList<Predicate> = mutableListOf()
)
