package io.archimedesfw.commons.lang

import io.archimedesfw.commons.lang.CollectionExtensionsTest.StubEnum.BAR
import io.archimedesfw.commons.lang.CollectionExtensionsTest.StubEnum.FOO
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.math.BigDecimal
import java.util.EnumMap

internal class CollectionExtensionsTest {

    @Test
    internal fun put_if_not_null() {
        val map = mutableMapOf<Int, Int>()

        map.putIfNotNull(1, null)

        assertTrue(map.isEmpty())
    }

    @Test
    internal fun enumMap_create() {
        val enumMap = enumMapOf<StubEnum, String>()

        assertTrue(enumMap is EnumMap)
        assertTrue(enumMap.isEmpty())
    }

    @Test
    internal fun enumMap_from_map() {
        val enumMap = mapOf(FOO to "foo", BAR to "bar").toEnumMap()

        assertTrue(enumMap is EnumMap)
        assertEquals("foo", enumMap[FOO])
        assertEquals("bar", enumMap[BAR])
    }

    @Test
    internal fun enumMap_from_iterable_pair() {
        val enumMap = listOf(FOO to "foo", BAR to "bar").toEnumMap()

        assertTrue(enumMap is EnumMap)
        assertEquals("foo", enumMap[FOO])
        assertEquals("bar", enumMap[BAR])
    }

    @Test
    internal fun merge_maps_by() {
        val map1 = mapOf("A" to 1, "B" to 2, "C" to 3)
        val map2 = mapOf<String, Int>()
        val map3 = mapOf("A" to 1, "B" to 2)
        val map4 = mapOf("D" to 4, "E" to 5)
        val maps = listOf(map1, map2, map3, map4)

        val actual = maps.mergeBy { it.value.sum() }

        val expected = mapOf("A" to 2, "B" to 4, "C" to 3, "D" to 4, "E" to 5)
        assertEquals(expected, actual)
    }

    @Test
    internal fun merge_maps_by__one_map() {
        val map1 = mapOf("A" to 1, "B" to 2, "C" to 3)
        val maps = listOf(map1)

        val actual = maps.mergeBy { it.value.sum() }

        assertEquals(map1, actual)
    }

    @Test
    internal fun merge_maps_by__empty() {
        val maps = emptyList<Map<String, Int>>()

        val actual = maps.mergeBy { it.value.sum() }

        val expected = emptyMap<String, Int>()
        assertEquals(expected, actual)
    }

    @Test
    internal fun remove_keys() {
        assertEquals(
            mutableMapOf("C" to 3),
            mutableMapOf("A" to 1, "B" to 2, "C" to 3).apply { remove(listOf("A", "B")) }
        )
    }

    @Test
    internal fun sub_list_from() {
        assertEquals(
            mutableListOf(3, 4, 5),
            mutableListOf(1, 2, 3, 4, 5).subListFrom(2)
        )
    }

    @Test
    internal fun bigdecimal_average() {
        val iterable = listOf(BigDecimal("-1.00").setScale(6), BigDecimal("0.00"), BigDecimal("2.00"))

        val actual = iterable.average()

        assertEquals(BigDecimal("0.333333"), actual)
    }

    @Test
    internal fun bigdecimal_average__exception_when_is_empty() {
        val iterable: Iterable<BigDecimal> = listOf()

        assertThrows<IllegalArgumentException> { iterable.average() }
    }

    private enum class StubEnum {
        FOO, BAR
    }
}
