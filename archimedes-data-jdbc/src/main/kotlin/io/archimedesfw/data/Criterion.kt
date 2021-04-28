package io.archimedesfw.data

import io.archimedesfw.data.Criterion.Operator.EQ
import io.archimedesfw.data.Criterion.Operator.NE

open class Criterion<T>(
    private val field: String,
    private val operator: Operator,
    private val value: Any?,
    private val type: String? = null
) : Criteria<T> {

    init {
        require(value != null || (operator == EQ || operator == NE)) {
            "Cannot use operator $operator with null value. With null value only operator $EQ and $NE are allowed."
        }
    }

    override fun and(criteria: Criteria<T>): Criteria<T> = AndCriteria(this).and(criteria)

    override fun or(criteria: Criteria<T>): Criteria<T> = OrCriteria(this).or(criteria)

    override fun toSql(sb: StringBuilder, args: MutableList<Any?>) {
        sb.append(field).append(' ')
        if (value == null) {
            sb.append("is ")
            if (operator == NE) sb.append("not ")
            sb.append("null")
        } else {
            sb.append(operator).append(" ?")
            if (type != null) sb.append("::").append(type)
            args.add(value)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Criterion<*>) return false

        if (field != other.field) return false
        if (operator != other.operator) return false
        if (value != other.value) return false
        if (type != other.type) return false

        return true
    }

    override fun hashCode(): Int {
        var result = field.hashCode()
        result = 31 * result + operator.hashCode()
        result = 31 * result + (value?.hashCode() ?: 0)
        result = 31 * result + (type?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String = "$field $operator $value ${type?.let { "::$it" } ?: ""}"

    enum class Operator(private val asString: String) {
        EQ("="),
        NE("!="),
        LT("<"),
        LE("<="),
        GT(">"),
        GE(">="),
        LIKE("like");

        override fun toString(): String = asString
    }

}
