package io.archimedesfw.security.auth.token.refresh

import io.archimedesfw.security.auth.Role
import io.archimedesfw.security.auth.Subject
import io.archimedesfw.security.auth.SubjectRepository
import io.archimedesfw.security.auth.UsernamePrincipal
import io.micronaut.security.errors.IssuingAnAccessTokenErrorCode.INVALID_GRANT
import io.micronaut.security.errors.OauthErrorResponseException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

internal class RefreshTokenAuthenticatorTest {

    private val subjectRepository = mock<SubjectRepository>()
    private val refreshTokenRepository = mock<RefreshTokenRepository>()
    private val authenticator = DefaultRefreshTokenAuthenticator(subjectRepository, refreshTokenRepository)

    @Test
    fun `authenticate with refresh token`() {
        val subject = Subject(
            1,
            UsernamePrincipal("principal"),
            setOf(Role("ROLE_1")),
            mapOf("ACCOUNT_ID" to 1)
        )
        doReturn(RefreshToken("refresh-token", "principal"))
            .whenever(refreshTokenRepository).findByRefreshToken("refresh-token")
        doReturn(subject)
            .whenever(subjectRepository).findByPrincipal("principal")

        val authenticatedSubject = authenticator.authenticate(RefreshTokenCredential("refresh-token"))

        assertEquals(subject, authenticatedSubject)
    }

    @Test
    fun `fail if authenticate with invalid refresh token`() {
        doReturn(null).whenever(refreshTokenRepository).findByRefreshToken("unknown-refresh-token")

        val ex = assertThrows<OauthErrorResponseException> {
            authenticator.authenticate(RefreshTokenCredential("unknown-refresh-token"))
        }

        assertEquals(INVALID_GRANT, ex.error)
    }

}
