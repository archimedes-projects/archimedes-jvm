package io.archimedesfw.context

import io.micronaut.context.exceptions.NonUniqueBeanException
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Qualifier
import jakarta.inject.Singleton
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.annotation.AnnotationRetention.RUNTIME

@MicronautTest
internal class MicronautServiceLocatorIT {

    @Test
    fun `locate by specific type`() {
        val service = ServiceLocator.locate<WithoutQualifier>()
        assertEquals(33, service.methodToImplement())
    }

    @Test
    fun `locate by annotation`() {
        val service1 = ServiceLocator.locateBy<ServiceInterface>(FooQualifier::class.java)
        assertEquals(42, service1.methodToImplement())
    }

    @Test
    fun `fail if there are multiple candidates`() {
        val ex = assertThrows<NonUniqueBeanException> {
            ServiceLocator.locate<ServiceInterface>()
        }
        assertThat(ex.message).startsWith("Multiple possible bean candidates found: [")
        assertThat(ex.message).contains("${WithQualifier::class.simpleName}")
        assertThat(ex.message).contains("${WithoutQualifier::class.simpleName}")
    }

}

@Qualifier
@Retention(RUNTIME)
internal annotation class FooQualifier

internal interface ServiceInterface {
    fun methodToImplement(): Int
}

@Singleton
@FooQualifier
internal class WithQualifier : ServiceInterface {
    override fun methodToImplement(): Int = 42
}

@Singleton
internal class WithoutQualifier : ServiceInterface {
    override fun methodToImplement(): Int = 33
}
