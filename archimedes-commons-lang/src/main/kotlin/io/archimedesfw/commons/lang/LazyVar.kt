package io.archimedesfw.commons.lang

import kotlin.reflect.KProperty

class LazyVar<T>(
    private val initializer: () -> T
) {
    private var value: T? = null

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        if (value == null) value = initializer()
        return value!!
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        this.value = value
    }

}
