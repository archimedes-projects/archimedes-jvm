package io.archimedesfw.context

import io.archimedesfw.data.tx.NoOpTransactional
import io.archimedesfw.data.tx.Transactional
import org.mockito.Mockito
import kotlin.reflect.KClass

public class FakeServiceLocator private constructor(
    private val beans: MutableMap<Class<*>, Any>
) : ServiceLocatorHolder {

    /** To be called by JVM ServiceLoader */
    internal constructor() : this(DEFAULT_BEANS.toMutableMap())

    override fun <T> locate(clazz: Class<T>, stereotype: Class<out Annotation>): T = locate(clazz)

    override fun <T> locate(clazz: Class<T>): T {
        var any = beans[clazz]
        if (any == null) {
            val typeCompatibles = beans.values.filter { clazz.isInstance(it) }
            require(typeCompatibles.size <= 1) {
                "Cannot found a unique bean of type ${clazz.name}. Found ${typeCompatibles.size} possible candidates."
            }
            any = typeCompatibles.singleOrNull()
        }

        if (any == null) {
            any = Mockito.mock(clazz) as Any
            beans[clazz] = any
        }

        @Suppress("UNCHECKED_CAST")
        return any as T
    }

    companion object {
        private val EMPTY_MAP = emptyMap<Class<*>, Any>()
        private val DEFAULT_BEANS: Map<Class<*>, Any> = mapOf(
            Transactional::class.java to NoOpTransactional()
        )

        fun of(vararg anys: Any): FakeServiceLocator = of(anys.associateBy { it::class })
        fun of(vararg pairs: Pair<KClass<*>, Any>): FakeServiceLocator = of(pairs.toMap())
        fun of(beans: Map<KClass<*>, Any>): FakeServiceLocator = ofJ(beans.mapKeys { it.key.java })
        fun of(): FakeServiceLocator = ofJ(EMPTY_MAP)

        fun ofJ(beans: Map<Class<*>, Any>): FakeServiceLocator {
            if (ServiceLocator.isInitialized()) {
                check(ServiceLocator.holder is FakeServiceLocator) {
                    "ServiceLocator already set! Cannot set ${FakeServiceLocator::class.simpleName} if there is" +
                            " already set a ServiceLocator of another type." +
                            " The already set value is: ${ServiceLocator.holder}"
                }
            }

            val beanWithDefaults = DEFAULT_BEANS.toMutableMap()
            beanWithDefaults.putAll(beans)
            val fakeServiceLocator = FakeServiceLocator(beanWithDefaults)

            ServiceLocator.holder = fakeServiceLocator

            return fakeServiceLocator
        }
    }

}
