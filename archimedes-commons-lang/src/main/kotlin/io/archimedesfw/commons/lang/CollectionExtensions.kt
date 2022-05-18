package io.archimedesfw.commons.lang

import java.math.BigDecimal
import java.util.EnumMap
import java.util.EnumSet


inline fun <reified E : Enum<E>> allOf(): EnumSet<E> = EnumSet.allOf(E::class.java)

inline fun <reified E : Enum<E>> noneOf(): EnumSet<E> = EnumSet.noneOf(E::class.java)

inline operator fun <reified T : Enum<T>> EnumSet<T>.minus(elements: EnumSet<T>): EnumSet<T> =
    if (elements.isEmpty()) this
    else this.filterNotTo(noneOf()) { it in elements }

fun <K, V> MutableMap<K, V>.putIfNotNull(key: K, value: V?) {
    if (value != null) this[key] = value
}

inline fun <reified K : Enum<K>, V> enumMapOf(): EnumMap<K, V> = EnumMap<K, V>(K::class.java)
inline fun <reified K : Enum<K>, V> enumMapOf(vararg pairs: Pair<K, V>): EnumMap<K, V> = pairs.toMap(enumMapOf())

inline fun <reified K : Enum<K>, V> Map<K, V>.toEnumMap(): EnumMap<K, V> =
    if (isEmpty()) enumMapOf()
    else EnumMap(this)

inline fun <reified K : Enum<K>, V> Iterable<Pair<K, V>>.toEnumMap(): EnumMap<K, V> = toMap(enumMapOf())

fun <K, V> Collection<Map<K, V>>.mergeBy(transform: (Map.Entry<K, List<V>>) -> V): Map<K, V> =
    if (this.isEmpty()) emptyMap()
    else reduce { acc, map -> acc.mergeBy(map, transform) }

private fun <K, V> Map<K, V>.mergeBy(map: Map<K, V>, transform: (Map.Entry<K, List<V>>) -> V): Map<K, V> =
    (this.asSequence() + map.asSequence())
        .groupBy({ it.key }, { it.value })
        .mapValues(transform)

fun <K, V> MutableMap<K, V>.remove(keys: Iterable<K>): Unit = keys.forEach { remove(it) }

fun <E> MutableList<E>.subListFrom(startIndex: Int): MutableList<E> = subList(startIndex, this.size)


fun Iterable<BigDecimal>.average(): BigDecimal {
    var sum = BigDecimal("0")
    var count = 0
    for (element in this) {
        sum += element
        count++
    }
    return if (count == 0) {
        throw IllegalArgumentException("Cannot get the average of an empty iterable.")
    } else {
        sum / count.toBigDecimal()
    }
}

fun <T> List<T>.singleOrNone(
    lazyMessage: (actual: List<T>) -> String = { actual ->
        "Expected one single element or none, but actually has ${actual.size}."
    }
): T? =
    when (size) {
        0 -> null
        1 -> this[0]
        else -> throw IllegalArgumentException(lazyMessage(this))
    }

fun <T> List<T>.requireSingle(
    lazyMessage: (actual: List<T>) -> String = { actual ->
        "Required exactly one single element, but actually has ${actual.size}."
    }
): T = if (size == 1) this[0] else throw IllegalArgumentException(lazyMessage(this))
