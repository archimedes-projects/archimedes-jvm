package io.archimedesfw.security.auth.password

import io.archimedesfw.security.auth.Subject
import io.micronaut.security.authentication.AuthenticationException
import java.security.Principal

internal interface UsernamePasswordAuthenticator {

    /**
     * Authenticates the subject related with the provided credentials.
     *
     * @param principal to identify the subject
     * @param password to authenticate the subject
     * @return the authenticated subject
     * @throws [AuthenticationException] if request cannot be authenticated
     */
    fun authenticate(principal: Principal, password: PasswordCredential): Subject

}
