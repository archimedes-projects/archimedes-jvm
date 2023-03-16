package io.archimedesfw.security.auth.token.refresh

import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

@TestInstance(PER_CLASS)
internal class RefreshTokenTest {

    private fun blankPropertiesProvider() = arrayOf(
        arrayOf("", "principalName"),
        arrayOf("refreshToken", "")
    )

    @ParameterizedTest
    @MethodSource("blankPropertiesProvider")
    fun `refresh token cannot be blank`(refreshToken: String, principalName: String) {
        assertThrows<IllegalArgumentException> {
            RefreshToken(refreshToken, principalName)
        }
    }

}
