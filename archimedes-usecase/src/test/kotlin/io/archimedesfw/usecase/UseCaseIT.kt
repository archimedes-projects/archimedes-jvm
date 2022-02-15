package io.archimedesfw.usecase

import io.archimedesfw.data.tx.Transactional
import io.archimedesfw.usecase.UseCaseTest.Companion.entityOf
import io.archimedesfw.usecase.transaction.TransactionalInterceptor
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import java.time.LocalDateTime

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

//    @Test
//    @Order(10)
    internal fun usecase_that_run_other_usecase() {
        var result = bus(EntitySaveCmd(entityOf("1")))
        assertEquals("1", result)

        result = bus(EntitySaveCmd(entityOf("2")))
        assertEquals("1", result)

        result = bus(EntitySaveCmd(entityOf("3")))
        assertEquals("2", result)
    }

//    @Test
//    @Order(20)
    internal fun simple_usecase() {
        val entities = bus(EntityFindQry("$TS"))

        assertEquals(3, entities.size)
        assertEquals("3", entities[0].name)
        assertEquals("2", entities[1].name)
        assertEquals("1", entities[2].name)
    }

    private companion object {
        private val TS = LocalDateTime.now()
    }

}
