package io.archimedesfw.security.auth.password

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import org.junit.jupiter.api.assertThrows

@TestInstance(PER_CLASS)
internal class PasswordCredentialTest {

    @Test
    fun `credentials cannot be blank`() {
        assertThrows<IllegalArgumentException> {
            PasswordCredential("")
        }
    }

}
