package io.archimedesfw.data

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class GeneratedKeysHolderTest {
    @Test
    fun `override equals`() {
        val gkh1 = GeneratedKeysHolder(arrayOf("colum name"), mutableListOf(mapOf("key" to "value")))
        val gkh2 = GeneratedKeysHolder(arrayOf("colum name"), mutableListOf(mapOf("key" to "value")))

        assertEquals(gkh1, gkh2)
    }
}
