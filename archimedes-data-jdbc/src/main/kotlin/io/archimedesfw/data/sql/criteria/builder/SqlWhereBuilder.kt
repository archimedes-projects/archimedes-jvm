package io.archimedesfw.data.sql.criteria.builder

import io.archimedesfw.data.sql.criteria.AndPredicate
import io.archimedesfw.data.sql.criteria.BinaryPredicate
import io.archimedesfw.data.sql.criteria.BinaryOperator
import io.archimedesfw.data.sql.criteria.UnaryPredicate
import io.archimedesfw.data.sql.criteria.UnaryOperator
import io.archimedesfw.data.sql.criteria.CompoundPredicate
import io.archimedesfw.data.sql.criteria.Expression
import io.archimedesfw.data.sql.criteria.Predicate
import io.archimedesfw.data.sql.criteria.SqlPredicate
import io.archimedesfw.data.sql.criteria.parameter.ArrayParameter
import io.archimedesfw.data.sql.criteria.parameter.Parameter
import io.archimedesfw.data.sql.criteria.parameter.ParameterExpression

class SqlWhereBuilder {

    fun appendWhere(sb: StringBuilder, where: WhereClause, parameters: MutableList<Parameter<*>>) {
        val staticWhere = where.staticWhere
        val predicates = where.predicates

        if (staticWhere.isNotEmpty()) {
            sb.append(staticWhere)
            if (predicates.isEmpty()) return // Nothing more to add
            sb.append(" AND ")
        } else {
            if (predicates.isEmpty()) return // Nothing more to add
            sb.append(" WHERE ")
        }

        val predicate = when (predicates.size) {
            1 -> predicates[0]
            else -> AndPredicate(predicates)
        }
        appendPredicate(sb, predicate, parameters)
    }

    fun appendPredicate(sb: StringBuilder, predicate: Predicate, parameters: MutableList<Parameter<*>>) {
        when (predicate) {
            is CompoundPredicate -> {
                sb.append('(')
                appendPredicate(sb, predicate.predicates[0], parameters)
                for (i in 1 until predicate.predicates.size) {
                    sb.append(predicate.concatenationOperator)
                    appendPredicate(sb, predicate.predicates[i], parameters)
                }
                sb.append(')')
            }

            is BinaryPredicate<*> -> {
                val isNullRight = predicate.right is ParameterExpression && predicate.right.value == null

                if (isNullRight && predicate.operator == BinaryOperator.EQ) {
                    appendPredicate(sb, UnaryPredicate(predicate.left, UnaryOperator.IS_NULL), parameters)
                } else if (isNullRight && predicate.operator == BinaryOperator.NE) {
                    appendPredicate(sb, UnaryPredicate(predicate.left, UnaryOperator.IS_NOT_NULL), parameters)
                } else {
                    appendPredicate(sb, predicate.left, parameters)
                    sb.append(predicate.operator.sql)
                    appendPredicate(sb, predicate.right, parameters)
                }
            }

            is UnaryPredicate<*> -> {
                appendPredicate(sb, predicate.value, parameters)
                sb.append(predicate.operator.sql)
            }

            is SqlPredicate -> sb.append(predicate.sql)

            else -> throw UnsupportedOperationException(
                "${this::class.simpleName} doesn't support visit ${predicate::class.simpleName} type"
            )
        }
    }

    private fun appendPredicate(sb: StringBuilder, expression: Expression<*>, parameters: MutableList<Parameter<*>>) {
        sb.append(expression.sql)
        when (expression) {
            is ArrayParameter -> {
                parameters.addAll(expression.parameters)
            }
            is ParameterExpression -> {
                parameters.add(expression)
            }
        }
    }

}
