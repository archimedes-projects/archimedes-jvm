package io.archimedesfw.cqrs.interceptor.audit

import java.time.LocalDateTime

data class AuditLog internal constructor(
    val id: Long,
    val timestamp: LocalDateTime,
    val elapsedTime: Long,
    val principalName: String,
    val action: String,
    val readOnly: Boolean,
    val success: Boolean,
    val arguments: String,
    val result: String
) {

    constructor(
        timestamp: LocalDateTime,
        elapsedTime: Long,
        principalName: String,
        action: String,
        readOnly: Boolean,
        success: Boolean,
        arguments: String,
        result: String
    ) : this(NEW, timestamp, elapsedTime, principalName, action, readOnly, success, arguments, result)

    private companion object {
        private const val NEW = -1L
    }

}
