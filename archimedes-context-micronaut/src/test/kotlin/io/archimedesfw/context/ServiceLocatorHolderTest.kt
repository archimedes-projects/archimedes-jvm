package io.archimedesfw.context

import io.micronaut.context.ApplicationContext
import io.micronaut.context.env.Environment
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.reset

@TestInstance(PER_CLASS)
internal class ServiceLocatorHolderTest {

    private val environment = mock<Environment>()
    private val applicationContext = mock<ApplicationContext> { on { environment } doReturn environment }

    init {
        if (ServiceLocator.isInitialized()) ServiceLocator.reset()
    }

    @BeforeEach()
    internal fun setup() {
        ServiceLocator.reset()
        reset(environment)
    }

    @Test
    internal fun initialize_micronaut_in_production() {
        simulateProductionEnv()

        val serviceLocator = MicronautServiceLocator(applicationContext)

        assertSame(serviceLocator, ServiceLocator.holder)
    }

    private fun simulateProductionEnv() {
        ServiceLocator.holder = UndefinedServiceLocator
    }

    @Test
    internal fun fail_if_in_production_initialize_but_another_service_locator_is_already_configured() {
        val ex = assertThrows<IllegalStateException> {
            MicronautServiceLocator(applicationContext)
        }

        assertEquals(
            "ServiceLocator already set! Looks like the Micronaut ApplicationContext was built twice" +
                    " or other one has faked the ServiceLocator." +
                    " The already set value is: ${FakeServiceLocator::class.qualifiedName}",
            ex.message!!.substringBefore('@')
        )
    }

    @Test
    internal fun fail_if_in_production_initialize_micronaut_twice() {
        simulateProductionEnv()

        MicronautServiceLocator(applicationContext)

        val ex = assertThrows<IllegalStateException> {
            MicronautServiceLocator(applicationContext)
        }

        assertEquals(
            "ServiceLocator already set! Looks like the Micronaut ApplicationContext was built twice" +
                    " or other one has faked the ServiceLocator." +
                    " The already set value is: ${MicronautServiceLocator::class.qualifiedName}",
            ex.message!!.substringBefore('@')
        )
    }

    @Test
    internal fun initialize_fake_several_times() {
        FakeServiceLocator.of("first")
        assertEquals("first", ServiceLocator.locate<String>())

        FakeServiceLocator.of("second")
        assertEquals("second", ServiceLocator.locate<String>())

        FakeServiceLocator.of("third")
        assertEquals("third", ServiceLocator.locate<String>())
    }

    @Test
    internal fun fail_if_initialize_fake_and_is_already_initialized_with_other_type() {
        ServiceLocator.holder = mock()

        val ex = assertThrows<IllegalStateException> {
            FakeServiceLocator.of()
        }

        assertEquals(
            "ServiceLocator already set! Cannot set ${FakeServiceLocator::class.simpleName} if there is" +
                    " already set a ServiceLocator of another type. The already set value is: ",
            ex.message!!.substringBefore("Mock for ${ServiceLocatorHolder::class.simpleName}")
        )
    }

}
