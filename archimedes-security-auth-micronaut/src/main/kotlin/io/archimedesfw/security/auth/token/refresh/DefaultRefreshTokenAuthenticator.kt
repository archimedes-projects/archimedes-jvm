package io.archimedesfw.security.auth.token.refresh

import io.archimedesfw.security.auth.Subject
import io.archimedesfw.security.auth.SubjectRepository
import io.micronaut.security.authentication.AuthenticationException
import io.micronaut.security.authentication.AuthenticationFailed
import io.micronaut.security.authentication.AuthenticationFailureReason.USER_NOT_FOUND
import io.micronaut.security.errors.IssuingAnAccessTokenErrorCode.INVALID_GRANT
import io.micronaut.security.errors.OauthErrorResponseException
import jakarta.inject.Singleton

@Singleton
internal class DefaultRefreshTokenAuthenticator(
    private val subjectRepository: SubjectRepository,
    private val refreshTokenRepository: RefreshTokenRepository
) : RefreshTokenAuthenticator {

    override fun authenticate(refreshToken: RefreshTokenCredential): Subject {
        val token = refreshTokenRepository.findByRefreshToken(refreshToken.secret)
            ?: throw OauthErrorResponseException(INVALID_GRANT)

        val subject = try {
            subjectRepository.findByPrincipal(token.principalName)
                ?: throw AuthenticationException(AuthenticationFailed(USER_NOT_FOUND))
        } catch (th: Throwable) {
            throw OauthErrorResponseException(INVALID_GRANT)
        }

        return subject
    }

}
