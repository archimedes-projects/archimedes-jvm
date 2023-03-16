package io.archimedesfw.security.auth.token.refresh

import io.archimedesfw.security.auth.Subject
import io.micronaut.security.errors.OauthErrorResponseException

internal interface RefreshTokenAuthenticator {

    /**
     * Authenticates the subject related with the provided credentials.
     *
     * @param refreshToken to authenticate the subject
     * @return the authenticated subject
     * @throws [OauthErrorResponseException] if refreshToken cannot be authenticated
     */
    fun authenticate(refreshToken: RefreshTokenCredential): Subject

}
