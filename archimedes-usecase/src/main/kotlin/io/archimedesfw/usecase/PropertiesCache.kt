package io.archimedesfw.usecase

import kotlin.reflect.KClass
import kotlin.reflect.KProperty1
import kotlin.reflect.KVisibility
import kotlin.reflect.full.memberProperties

internal object PropertiesCache {
    private val propertiesCache = mutableMapOf<KClass<*>, Array<out KProperty1<*, *>>>()

    internal inline fun <reified T : Any> propertiesOf(any: T): Array<out KProperty1<T, Any?>> {
        val kClass = any::class
        val cached = propertiesCache.getOrElse(kClass) {
            val props = kClass.memberProperties.filter { it.visibility == KVisibility.PUBLIC }
            val toArray = props.toTypedArray()
            propertiesCache[kClass] = toArray
            toArray
        }
        return cached as Array<out KProperty1<T, Any?>>
    }
}
