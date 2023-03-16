package io.archimedesfw.security.auth

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class UsernamePrincipalTest {

    @Test
    fun `identity cannot be blank`() {
        assertThrows<IllegalArgumentException> {
            UsernamePrincipal("")
        }
    }

}
