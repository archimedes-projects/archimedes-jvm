package io.archimedesfw.context

import java.util.ServiceLoader
import java.util.stream.Collectors

/**
 * Locate beans inside objects which lifecycle is outside the control of application context.
 *
 * The preferred way to use bean dependencies is by injecting it, but this is only possible if the object lifecycle
 * is managed by the application context. In some cases this is not possible, for example when instances are created
 * calling directly the constructor (the class is not `@Singleton` neither `@Prototype` nor something like these).
 *
 * In theses cases if inside this instances you need to use a bean you can use this class to locate manually the bean.
 */
object ServiceLocator {

    internal var holder: ServiceLocatorHolder = BootstrapServiceLocator.initialHolder

    internal fun isInitialized(): Boolean = holder !== UndefinedServiceLocator
    internal fun reset() {
        holder = BootstrapServiceLocator.initialHolder
    }

    fun <T> locate(clazz: Class<T>): T = holder.locate(clazz)
    fun <T> locate(clazz: Class<T>, stereotype: Class<out Annotation>): T = holder.locate(clazz, stereotype)

    inline fun <reified T> locate(): T = locate(T::class.java)
    inline fun <reified T> locateBy(stereotype: Class<out Annotation>): T = locate(T::class.java, stereotype)
}

private object BootstrapServiceLocator {
    internal val initialHolder: ServiceLocatorHolder

    init {
        val loader: ServiceLoader<ServiceLocatorHolder> = ServiceLoader.load(ServiceLocatorHolder::class.java)
        val fakes = loader.stream()
            .filter { it.type().canonicalName == "io.archimedesfw.context.FakeServiceLocator" }
            .collect(Collectors.toList())
        initialHolder = if (fakes.size == 1) fakes[0].get() else UndefinedServiceLocator
    }
}

internal object UndefinedServiceLocator : ServiceLocatorHolder {
    override fun <T> locate(clazz: Class<T>, stereotype: Class<out Annotation>): T = locate(clazz)
    override fun <T> locate(clazz: Class<T>): T = throw UnsupportedOperationException(
        "Undefined ServiceLocator, some ServiceLocator should be configured"
    )
}
