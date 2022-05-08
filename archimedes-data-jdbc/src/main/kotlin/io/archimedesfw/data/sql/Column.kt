package io.archimedesfw.data.sql

import io.archimedesfw.data.sql.criteria.SqlExpression

open class Column<C>(
    val name: String,
    path: String,
    alias: String = "_$name",
    val isGenerated: Boolean = false
) : SqlExpression<C>(path, alias) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false

        other as Column<*>

        if (isGenerated != other.isGenerated) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + isGenerated.hashCode()
        return result
    }

    override fun toString(): String =
        "${this::class.simpleName}(name='$name', alias='$alias', isGenerated=$isGenerated)"

}
