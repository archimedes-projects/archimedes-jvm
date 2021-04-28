package io.archimedesfw.usecase

import io.archimedesfw.data.tx.Transactional
import io.archimedesfw.usecase.transaction.TransactionalInterceptor
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import org.junit.jupiter.api.TestMethodOrder
import java.time.LocalDateTime
import javax.inject.Inject

@MicronautTest(transactional = false)
@TestMethodOrder(OrderAnnotation::class)
@TestInstance(PER_CLASS)
internal class UseCaseIT {

    private lateinit var bus: UseCaseBus

    @Inject
    private lateinit var transactional: Transactional

    @BeforeAll
    internal fun beforeAll() {
        bus = InterceptedUseCaseBus(TransactionalInterceptor(transactional, TailInterceptor()))
    }

    @Test
    @Order(10)
    internal fun usecase_that_run_other_usecase() {
        var result = bus(EntitySaveCmd(entityOf("1")))
        assertEquals("1", result)

        result = bus(EntitySaveCmd(entityOf("2")))
        assertEquals("1", result)

        result = bus(EntitySaveCmd(entityOf("3")))
        assertEquals("2", result)
    }

    @Test
    @Order(20)
    internal fun simple_usecase() {
        val entities = bus(EntityFindQry("$TS"))

        assertEquals(3, entities.size)
        assertEquals("3", entities[0].result)
        assertEquals("2", entities[1].result)
        assertEquals("1", entities[2].result)
    }

    private companion object {
        private val TS = LocalDateTime.now()
        private fun entityOf(result: String) = UseCaseTest.entityOf(result, "$TS")
    }

}
