package io.archimedesfw.usecase.transaction

import io.archimedesfw.data.tx.Transactional
import io.archimedesfw.security.Security
import io.archimedesfw.security.test.FakeSecurityContext
import io.archimedesfw.usecase.InterceptedUseCaseBus
import io.archimedesfw.usecase.LambdaCmd
import io.archimedesfw.usecase.LambdaQry
import io.archimedesfw.usecase.TailInterceptor
import io.archimedesfw.usecase.UseCaseBus
import io.archimedesfw.usecase.UseCaseTest
import io.archimedesfw.usecase.audit.AuditableInterceptor
import io.archimedesfw.usecase.audit.persistence.jdbc.JdbcAuditRepository
import io.micronaut.data.exceptions.DataAccessException
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import org.junit.jupiter.api.TestMethodOrder
import org.junit.jupiter.api.assertThrows
import java.time.LocalDateTime
import javax.inject.Inject

@MicronautTest(transactional = false)
@TestMethodOrder(OrderAnnotation::class)
@TestInstance(PER_CLASS)
internal class TransactionalInterceptorIT {

    private lateinit var bus: UseCaseBus

    @Inject
    private lateinit var transactional: Transactional

    @Inject
    private lateinit var repository: JdbcAuditRepository

    @Inject
    lateinit var tx: Transactional

    @BeforeAll
    internal fun beforeAll() {
        bus = InterceptedUseCaseBus(
            AuditableInterceptor(
                transactional,
                repository,
                TransactionalInterceptor(
                    transactional,
                    TailInterceptor()
                )
            )
        )
    }

    @Test
    @Order(30)
    internal fun if_fail_inside_usecase_not_commit_transaction() {
        val userId = "fail-usecase-$TS"

        // We use two commands, the outer use the Executor to exec the inner command.
        // With this we also assert that all the use cases executed by the same Executer in the same thread
        // use the same transaction, so if the inner is rollbacked, also the outer one.
        val innerUseCase = LambdaCmd {
            repository.save(entityOf("inner never saved"))
            throw UnsupportedOperationException("Cannot run inner command")
        }
        val outerUseCase = LambdaCmd {
            repository.save(entityOf("outer never saved"))
            bus(innerUseCase)
        }

        val ex = assertThrows<UnsupportedOperationException> {
            Security.runAs(FakeSecurityContext(userId)) {
                bus(outerUseCase)
            }
        }

        assertEquals("Cannot run inner command", ex.message)

        val savedEntities = tx.readOnly { repository.findByUserId(DUMMY_ID) }
        assertTrue(savedEntities.isEmpty())

        // Assert that the fail is in the audit log.
        // This means that the audit log is saved in a different transaction than the rollbacked one.

        // Right now nested calls to use case bus are not supported,
        // so in the audit logs we can found two entries of the fail
        val auditLogs = tx.readOnly { repository.findByUserId(userId) }
        var auditLog = auditLogs[0]
        assertEquals(userId, auditLog.userId)
        assertEquals(LambdaCmd::class.qualifiedName, auditLog.useCase)
        assertEquals(false, auditLog.readOnly)
        assertEquals(false, auditLog.success)
        assertEquals("block=() -> kotlin.Any", auditLog.arguments)
        assertEquals("java.lang.UnsupportedOperationException: Cannot run inner command", auditLog.result)

        auditLog = auditLogs[1]
        assertEquals(userId, auditLog.userId)
        assertEquals(LambdaCmd::class.qualifiedName, auditLog.useCase)
        assertEquals(false, auditLog.readOnly)
        assertEquals(false, auditLog.success)
        assertEquals("block=() -> kotlin.Any", auditLog.arguments)
        assertEquals("java.lang.UnsupportedOperationException: Cannot run inner command", auditLog.result)
    }

    @Test
    @Order(40)
    internal fun fail_if_try_to_update_ddbb_in_query() {
        val userId = "fail-readonly-transaction-$TS"
        val ex = assertThrows<DataAccessException> {
            Security.runAs(FakeSecurityContext(userId)) {
                bus(LambdaQry { repository.save(entityOf("never saved")) })
            }
        }

        assertTrue(ex.message!!.contains("ERROR: cannot execute INSERT in a read-only transaction"))
    }

    private companion object {
        private val TS = LocalDateTime.now()
        private val DUMMY_ID = "dummy ${TransactionalInterceptorIT::class.simpleName} $TS"
        private fun entityOf(result: String) = UseCaseTest.entityOf(result, DUMMY_ID)
    }

}
