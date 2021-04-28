package io.archimedesfw.data

internal abstract class AbstractCriteria<T>(
    private val operator: String,
    vararg criteria: Criteria<T>
) : Criteria<T> {

    protected val criterias: Array<out Criteria<T>> = criteria

    override fun toSql(sb: StringBuilder, args: MutableList<Any?>) {
        sb.append("(")
        var criteria = criterias[0]
        criteria.toSql(sb, args)
        for (i in 1 until criterias.size) {
            sb.append(operator)
            criteria = criterias[i]
            criteria.toSql(sb, args)
        }
        sb.append(")")
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is AbstractCriteria<*>) return false

        if (operator != other.operator) return false
        if (!criterias.contentEquals(other.criterias)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = operator.hashCode()
        result = 31 * result + criterias.contentHashCode()
        return result
    }

    override fun toString(): String = "AbstractCriteria(operator='$operator', criterias=$criterias)"

}
