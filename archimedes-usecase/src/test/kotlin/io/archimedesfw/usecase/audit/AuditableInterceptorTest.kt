package io.archimedesfw.usecase.audit

import io.archimedesfw.data.tx.NoOpTransactional
import io.archimedesfw.security.Security
import io.archimedesfw.security.test.FakeSecurityContext
import io.archimedesfw.usecase.Command
import io.archimedesfw.usecase.Query
import io.archimedesfw.usecase.TailInterceptor
import io.archimedesfw.usecase.UseCase
import io.archimedesfw.usecase.audit.persistence.AuditRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.mockito.Mockito.verify
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.mock
import org.mockito.kotlin.reset

@TestInstance(PER_CLASS)
internal class AuditableInterceptorTest {

    private val auditRepository = mock<AuditRepository>()
    private val auditableInterceptor = AuditableInterceptor(NoOpTransactional(), auditRepository, TailInterceptor())

    @BeforeEach
    internal fun beforeEach() = reset(auditRepository)

    private fun useCasesProvider() = arrayOf(
        arrayOf(FooCommand()),
        arrayOf(FooCommandWithParameters()),
        arrayOf(FooQuery()),
        arrayOf(FooQueryReturnNull()),
        arrayOf(ExplodingQuery())
    )

    @ParameterizedTest
    @MethodSource("useCasesProvider")
    internal fun intercept_command(useCase: UseCase<*>) {
        Security.runAs(FakeSecurityContext()) {
            try {
                auditableInterceptor.intercept(useCase)
            } catch (ex: IllegalArgumentException) {
                assertEquals("Boom!", ex.message) // If some exception is thrown, it should be a expected one
            }
        }

        argumentCaptor<AuditLog> {
            verify(auditRepository).save(capture())
            val actual = allValues[0]
            val expected = (useCase as MockUseCase).expectedOf(actual)
            assertEquals(expected, actual)
        }
    }

    private companion object {
        private fun auditLogOf(
            actual: AuditLog,
            useCase: UseCase<*>,
            success: Boolean,
            arguments: String,
            result: String
        ) = AuditLog(
            actual.timestamp,
            actual.elapsedTime,
            FakeSecurityContext.DUMMY_USER_ID,
            useCase.javaClass.name,
            useCase.isReadOnly,
            success,
            arguments,
            result
        )
    }

    private interface MockUseCase {
        fun expectedOf(actual: AuditLog): AuditLog
    }

    private class FooCommand : Command<Int>(),
        MockUseCase {
        override fun run(): Int = 42
        override fun expectedOf(actual: AuditLog) = auditLogOf(actual, this, true, "", "42")
    }

    internal class FooCommandWithParameters(val arg1: String = "A1", val arg2: Int = 2) : Command<Unit>(),
        MockUseCase {
        override fun run() {}
        override fun expectedOf(actual: AuditLog) = auditLogOf(actual, this, true, "arg1=A1,arg2=2", "void")
    }

    private class FooQuery : Query<List<Int>>(),
        MockUseCase {
        override fun run(): List<Int> = listOf(1, 2, 3)
        override fun expectedOf(actual: AuditLog) = auditLogOf(actual, this, true, "", "[1, 2, 3]")
    }

    private class FooQueryReturnNull : Query<String?>(),
        MockUseCase {
        override fun run(): String? = null
        override fun expectedOf(actual: AuditLog) = auditLogOf(actual, this, true, "", "null")
    }

    private class ExplodingQuery :
        Query<List<Int>>(), MockUseCase {
        override fun run(): List<Int> = throw IllegalArgumentException("Boom!")
        override fun expectedOf(actual: AuditLog) =
            auditLogOf(actual, this, false, "", IllegalArgumentException("Boom!").toString())
    }

}
