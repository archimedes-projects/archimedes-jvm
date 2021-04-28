package io.archimedesfw.usecase.audit.persistence.jdbc

import io.archimedesfw.usecase.audit.AuditLog
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import java.time.LocalDateTime
import javax.inject.Inject

@MicronautTest(rollback = false)
@TestMethodOrder(OrderAnnotation::class)
internal class JdbcAuditRepositoryIT {

    @Inject
    private lateinit var repository: JdbcAuditRepository

    @Test
    @Order(1)
    internal fun save() {
        repository.save(AUDIT_LOG)
    }

    @Test
    @Order(2)
    internal fun find() {
        val actualAuditLogs = repository.findByUserId(USER_ID)

        assertEquals(1, actualAuditLogs.size)
        assertEquals(AUDIT_LOG.copy(id = actualAuditLogs[0].id), actualAuditLogs[0])
    }

    private companion object {
        private val RANDOM = LocalDateTime.now()
        private val USER_ID = "User ID $RANDOM"
        private val AUDIT_LOG = AuditLog(RANDOM, 100L, USER_ID, "Use case", true, false, "Arguments", "Result")
    }

}
