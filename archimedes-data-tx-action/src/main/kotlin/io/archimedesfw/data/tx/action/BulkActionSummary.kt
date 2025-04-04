package io.archimedesfw.data.tx.action

import java.time.LocalDateTime

public open class BulkActionSummary {

    protected var _successCount: Int = 0
    protected val _errors: MutableList<ErrorDetail> = mutableListOf()

    public val errors: List<ErrorDetail> = _errors
    public val successCount: Int get() = _successCount
    public val errorCount: Int get() = _errors.size
    public val totalCount: Int get() = _successCount + _errors.size

    public override fun toString(): String =
        "BulkActionSummary(total=$totalCount, success=$successCount, error=$errorCount, errors=$errors)"

    public fun successOrThrow(): BulkActionSummary {
        if (_errors.isNotEmpty()) throw BulkActionException(this, "Multiple errors.")
        return this
    }


    public class BulkActionException(
        val summary: BulkActionSummary,
        message: String?
    ) : Exception(message)

    public data class ErrorDetail(
        val index: Int,
        val id: String,
        val exception: Exception
    ) {
        val ts: LocalDateTime = LocalDateTime.now()
    }

}
