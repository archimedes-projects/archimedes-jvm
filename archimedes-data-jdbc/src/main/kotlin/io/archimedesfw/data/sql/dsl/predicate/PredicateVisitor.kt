package io.archimedesfw.data.sql.dsl.predicate

fun interface PredicateVisitor {
    fun visit(sb: StringBuilder, predicate: Predicate, bindings: MutableList<Any>)
}
