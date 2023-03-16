package io.archimedesfw.security.auth.token.refresh

import io.archimedesfw.security.auth.SubjectMotherPersistent
import io.micronaut.data.exceptions.DataAccessException
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

@MicronautTest
internal class RefreshTokenRepositoryIT {

    @Inject
    private lateinit var refreshTokenRepository: RefreshTokenRepository

    @Inject
    private lateinit var subjectMother: SubjectMotherPersistent

    @Test
    fun `cannot save refresh token if subject doesn't exist`() {
        val e = assertThrows<DataAccessException> {
            refreshTokenRepository.save(RefreshToken("refresh-token", "principal doesn't exist"))
        }

        assertThat(e.message).contains(
            "insert or update on table \"archimedes_security_refresh_token\" violates foreign key constraint \"archimedes_security_refresh_token_principal_name_fkey\""
        )
    }

    @Test
    fun `updates previous refresh token`() {
        subjectMother.subject(USERNAME)
        refreshTokenRepository.save(RefreshToken("refresh-token1", USERNAME))

        val savedToken = refreshTokenRepository.save(RefreshToken("refresh-token2", USERNAME))

        val token1 = refreshTokenRepository.findByRefreshToken("refresh-token1")
        val token2 = refreshTokenRepository.findByRefreshToken("refresh-token2")
        assertNull(token1)
        assertEquals(savedToken, token2)
    }

    @Test
    fun `find by refresh token`() {
        subjectMother.subject(USERNAME)
        val savedToken = refreshTokenRepository.save(RefreshToken("refresh-token", USERNAME))

        val token = refreshTokenRepository.findByRefreshToken("refresh-token")

        assertEquals(savedToken, token)
    }

    private companion object {
        private const val USERNAME = "username"
    }

}
