package io.archimedesfw.context

import io.micronaut.context.ApplicationContext
import io.micronaut.context.annotation.Context
import io.micronaut.inject.qualifiers.Qualifiers

@Context
internal class MicronautServiceLocator internal constructor(
    private val applicationContext: ApplicationContext
) : ServiceLocatorHolder {

    init {
        if (ServiceLocator.isInitialized()) {
            check(applicationContext.environment.activeNames.contains("test")) {
                "ServiceLocator already set! Looks like the Micronaut ApplicationContext was built twice " +
                        "or other one has faked the ServiceLocator. The already set value is: ${ServiceLocator.holder}"
            }
        }
        ServiceLocator.holder = this
    }

    override fun <T> locate(clazz: Class<T>): T = applicationContext.getBean(clazz)
    override fun <T> locate(clazz: Class<T>, stereotype: Class<out Annotation>): T {
        val micronautQualifier = Qualifiers.byStereotype<T>(stereotype)
        return applicationContext.getBean(clazz, micronautQualifier)
    }

}
