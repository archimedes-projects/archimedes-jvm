package io.archimedesfw.context

import io.micronaut.context.annotation.Factory
import org.junit.jupiter.api.extension.ExtendWith
import java.lang.annotation.Inherited

@MustBeDocumented
@Retention
@Target(
    AnnotationTarget.ANNOTATION_CLASS,
    AnnotationTarget.CLASS,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@Inherited
@ExtendWith(ServiceLocatorJUnitExtension::class)
@Factory
annotation class ServiceLocatorTest
