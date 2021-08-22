package io.archimedesfw.data.sql.dsl.predicate

open class PredicateOperator(
    val sql: String
) {

    /**
     * Define generic operators
     */
    companion object {
        val EQ: PredicateOperator = PredicateOperator("=")
        val NE: PredicateOperator = PredicateOperator("!=")
        val LT: PredicateOperator = PredicateOperator("<")
        val LE: PredicateOperator = PredicateOperator("<=")
        val GT: PredicateOperator = PredicateOperator(">")
        val GE: PredicateOperator = PredicateOperator(">=")
    }

}
