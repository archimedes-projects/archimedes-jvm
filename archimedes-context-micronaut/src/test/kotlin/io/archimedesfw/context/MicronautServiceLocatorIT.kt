package io.archimedesfw.context

import io.micronaut.context.exceptions.NonUniqueBeanException
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Qualifier
import jakarta.inject.Singleton
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

@MicronautTest
internal class MicronautServiceLocatorIT {

    @Test
    fun locate_by_specific_type() {
        val service = ServiceLocator.locate<WithoutQualifier>()
        assertEquals(33, service.methodToImplement())
    }

    @Test
    fun locate_by_annotation() {
        val service1 = ServiceLocator.locateBy<ServiceInterface>(FooQualifier::class.java)
        assertEquals(42, service1.methodToImplement())
    }

    @Test
    fun fail_if_there_are_multiple_candidates() {
        val ex = assertThrows<NonUniqueBeanException> {
            ServiceLocator.locate<ServiceInterface>()
        }
        assertTrue(ex.message!!.startsWith("Multiple possible bean candidates found: ["))
        assertTrue(ex.message!!.contains("${WithoutQualifier::class.qualifiedName}"))
        assertTrue(ex.message!!.contains("${WithQualifier::class.qualifiedName}"))
    }

}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
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
