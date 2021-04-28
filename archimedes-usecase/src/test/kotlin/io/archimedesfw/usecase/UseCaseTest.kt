package io.archimedesfw.usecase

import io.archimedesfw.usecase.audit.AuditLog
import io.archimedesfw.usecase.audit.persistence.jdbc.JdbcAuditRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import java.time.LocalDateTime

internal class UseCaseTest {

    @Test
    internal fun are_equals_if_public_properties_are_equals() {
        val repository = mock<JdbcAuditRepository>()

        assertEquals(
            EntityFindQry("foo", repository),
            EntityFindQry("foo", mock())
        )
        assertNotEquals(
            EntityFindQry("foo", repository),
            EntityFindQry("bar", repository)
        )
    }

    @Test
    internal fun usecase_with_dependency() {
        val repository = mock<JdbcAuditRepository> {
            on { findByUserId("entity to find") } doReturn listOf(entityOf("inside repository"))
        }

        val useCase = EntityFindQry("entity to find", repository)
        val entity = useCase.fakeRun().single()

        assertEquals("inside repository", entity.result)
    }

    @Test
    internal fun usecase_that_run_other_usecase() {
        val repository = mock<JdbcAuditRepository>()
        val entity = entityOf(userId = "entity to find")

        val useCase = EntitySaveCmd(entity, repository)
            .asSpiedRun(
                EntityFindQry("entity to find"), listOf(entityOf("inside the dependency use case"))
            )

        val result = useCase.fakeRun()

        verify(repository).save(entity)
        assertEquals("inside the dependency use case", result)
    }

    internal companion object {
        private const val USER_ID = "dummy"

        internal fun entityOf(
            result: String = "dummy result",
            userId: String = USER_ID
        ): AuditLog =
            AuditLog(
                LocalDateTime.now(),
                42,
                userId,
                "dummy-dummy-dummy",
                false,
                true,
                "dummyOne=1, dummyTwo=2",
                result
            )
    }

}
