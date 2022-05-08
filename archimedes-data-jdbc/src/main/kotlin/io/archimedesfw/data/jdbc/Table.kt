package io.archimedesfw.data.jdbc

import io.archimedesfw.commons.lang.LazyVar
import io.archimedesfw.data.Order
import io.archimedesfw.data.Order.Direction.ASC

@Deprecated("Use classes from package `io.archimedes.data.sql`")
data class Table(
    val table: String,
    val alias: String,
    val select: String,
    val order: Order,
    val insert: String,
    val update: String,
    val updateableColumns: Array<String>,
    val generatedColumns: Array<String>
) {

    val selectColumns: Array<String> by LazyVar { generatedColumns + updateableColumns }

    open class Builder(
        private val table: String,
        private val updateableColumns: List<String>
    ) {
        protected var order = Order(updateableColumns.first(), ASC)
        protected var generatedColumns: List<String> = emptyList()
        protected var updateIdentityCondition = "id=?"
        protected var upsertConstraint: String? = null
        protected val columnTypes = mutableMapOf<Int, String>()
        protected var updateWithNulls: Boolean = true

        fun order(by: Order): Builder {
            order = by
            return this
        }

        fun generatedColumns(vararg columns: String): Builder = generatedColumns(columns.toList())
        fun generatedColumns(columns: List<String>): Builder {
            generatedColumns = columns
            return this
        }


        fun updateIdentityCondition(condition: String): Builder {
            updateIdentityCondition = condition
            return this
        }

        fun upsertConstraint(constraint: String, allowNullsOnUpdate: Boolean): Builder {
            upsertConstraint = constraint
            updateWithNulls = allowNullsOnUpdate
            return this
        }

        fun column(index: Int, type: String): Builder {
            columnTypes[index] = type
            return this
        }

        fun of(): Table {
            val alias = generateAlias(table)
            val selectColumns = (generatedColumns + updateableColumns).joinToString()
            val upsertColumns = updateableColumns.filterNot { it == "version_end" }.joinToString()
            val upsertParameters = generateUpsertParams()

            val select = "select $selectColumns from $table"
            val insert: String
            val update: String
            if (upsertConstraint == null) {
                insert = "insert into $table ($upsertColumns) values ($upsertParameters)"
                update = "update $table set ($upsertColumns) = ($upsertParameters) where $updateIdentityCondition"
            } else {
                val updatesOnConflict = if (updateWithNulls) {
                    updateableColumns.joinToString { "$it = excluded.$it" }
                } else {
                    updateableColumns.joinToString { "$it = coalesce(excluded.$it, $alias.$it)" }
                }
                insert = "insert into $table as $alias ($upsertColumns) values ($upsertParameters) " +
                        "on conflict $upsertConstraint do update set " + updatesOnConflict
                update = insert
            }
            return Table(
                table,
                alias,
                select,
                order,
                insert,
                update,
                updateableColumns.toTypedArray(),
                generatedColumns.toTypedArray()
            )
        }

        private fun generateUpsertParams(): String {
            val params = MutableList(updateableColumns.size) { "?" }
            for ((columnIndex, columnType) in columnTypes) {
                params[columnIndex] = "?::$columnType"
            }

            val versionEndIndex = updateableColumns.indexOf("version_end")
            if (versionEndIndex != -1) params.removeAt(versionEndIndex)

            return params.joinToString()
        }

        private fun generateAlias(table: String): String = table.replace("[aeiou]", "").take(4)

    }

}
