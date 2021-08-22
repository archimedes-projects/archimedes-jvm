package io.archimedesfw.data.sql.dsl.predicate

import io.archimedesfw.data.sql.dsl.field.Field
import io.archimedesfw.data.sql.dsl.field.ValueField
import io.archimedesfw.data.sql.dsl.field.SelectField
import io.archimedesfw.data.sql.dsl.predicate.PredicateOperator.Companion.NE

internal class ToSqlPredicateVisitor : PredicateVisitor {

    override fun visit(sb: StringBuilder, predicate: Predicate, bindings: MutableList<Any>) {
        when (predicate) {
            is CompositePredicate -> {
                sb.append('(')
                visit(sb, predicate.predicates[0], bindings)
                for (i in 1 until predicate.predicates.size) {
                    sb.append(predicate.concatenationOperator)
                    visit(sb, predicate.predicates[i], bindings)
                }
                sb.append(')')
            }

            is SqlPredicate -> sb.append(predicate.sql)

            is OperatorPredicate<*> -> {
                visit(sb, predicate.left, bindings)

                if (predicate.right is ValueField && predicate.right.value == null) {
                    sb.append(" IS ")
                    if (predicate.operator == NE) sb.append("NOT ")
                    sb.append("null")
                } else {
                    sb.append(predicate.operator.sql)
                    visit(sb, predicate.right, bindings)
                }
            }

            else -> throw UnsupportedOperationException(
                "${this::class.simpleName} doesn't support visit ${predicate::class.simpleName} type"
            )
        }
    }

    private fun visit(sb: StringBuilder, field: Field<*>, bindings: MutableList<Any>) {
        when (field) {
            is SelectField -> sb.append(field.sql)

            is ValueField -> {
                val value = field.value ?: error("Field value cannot be null")
                sb.append('?')
                bindings.add(value)
                // if (type != null) sb.append("::").append(type)
            }

            else -> throw UnsupportedOperationException(
                "${this::class.simpleName} doesn't support visit ${field::class.simpleName} type"
            )
        }
    }

}
