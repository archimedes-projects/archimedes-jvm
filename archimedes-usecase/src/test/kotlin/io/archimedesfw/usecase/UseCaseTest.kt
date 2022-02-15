package io.archimedesfw.usecase

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

internal class UseCaseTest {

    @Test
    internal fun are_equals_if_public_properties_are_equals() {
        val repository = mock<EntityRepository>()

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
        val repository = mock<EntityRepository> {
            on { findByName("entity to find") } doReturn listOf(entityOf("inside repository"))
        }

        val useCase = EntityFindQry("entity to find", repository)
        val entity = useCase.fakeRun().single()

        assertEquals("inside repository", entity.name)
    }

//    @Test
//    internal fun usecase_that_run_other_usecase() {
//        val repository = mock<EntityRepository>()
//        val entity = entityOf(name = "entity to find")
//
//        val useCase = EntitySaveCmd(entity, repository)
//            .asSpiedRun(
//                EntityFindQry("entity to find"), listOf(entityOf("inside the dependency use case"))
//            )
//
//        val result = useCase.fakeRun()
//
//        verify(repository).save(entity)
//        assertEquals("inside the dependency use case", result)
//    }

    internal companion object {
        private const val NAME = "dummy"

        internal fun entityOf(name: String = NAME): Entity = Entity(42, name)
    }

}
