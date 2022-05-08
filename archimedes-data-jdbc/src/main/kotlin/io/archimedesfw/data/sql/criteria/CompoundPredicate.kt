package io.archimedesfw.data.sql.criteria

internal abstract class CompoundPredicate(
    val concatenationOperator: String,
    val predicates: List<Predicate>
) : Predicate {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CompoundPredicate

        if (concatenationOperator != other.concatenationOperator) return false
        if (predicates != other.predicates) return false

        return true
    }

    override fun hashCode(): Int {
        var result = predicates.hashCode()
        result = 31 * result + concatenationOperator.hashCode()
        return result
    }

    override fun toString(): String =
        "${this::class.simpleName}(concatenationOperator='$concatenationOperator', predicates=$predicates)"

}
