package io.archimedesfw.context

internal interface ServiceLocatorHolder {

    fun <T> locate(clazz: Class<T>): T
    fun <T> locate(clazz: Class<T>, stereotype: Class<out Annotation>): T

}
