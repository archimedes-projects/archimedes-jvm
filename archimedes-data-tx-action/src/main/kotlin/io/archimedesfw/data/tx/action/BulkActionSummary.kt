package io.archimedesfw.data.tx.action

import java.time.LocalDateTime

open class BulkActionSummary {

    protected var _successCount: Int = 0
    protected val _errors: MutableList<ErrorDetail> = mutableListOf()

    val errors: List<ErrorDetail> = _errors
    val successCount: Int get() = _successCount
    val errorCount: Int get() = _errors.size
    val totalCount: Int get() = _successCount + _errors.size

    override fun toString(): String =
        "BulkActionSummary(total=$totalCount, success=$successCount, error=$errorCount, errors=$errors)"

    fun successOrThrow(): BulkActionSummary {
        if (_errors.size > 0) throw BulkActionException(this, "Multiple errors")
        return this
    }


    class BulkActionException(
        val summary: BulkActionSummary,
        message: String?
    ) : Exception(message)

    data class ErrorDetail(
        val index: Int,
        val id: String,
        val exception: Exception
    ) {
        val ts: LocalDateTime = LocalDateTime.now()
    }

}
