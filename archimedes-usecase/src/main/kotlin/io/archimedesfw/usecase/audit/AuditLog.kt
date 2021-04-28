package io.archimedesfw.usecase.audit

import java.time.LocalDateTime

internal data class AuditLog internal constructor(
    val id: Long,
    val timestamp: LocalDateTime,
    val elapsedTime: Long,
    val userId: String,
    val useCase: String,
    val readOnly: Boolean,
    val success: Boolean,
    val arguments: String,
    val result: String
) {

    internal constructor(
        timestamp: LocalDateTime,
        elapsedTime: Long,
        userId: String,
        useCase: String,
        readOnly: Boolean,
        success: Boolean,
        arguments: String,
        result: String
    ) : this(NEW, timestamp, elapsedTime, userId, useCase, readOnly, success, arguments, result)

    private companion object {
        private const val NEW = Long.MIN_VALUE
    }

}
