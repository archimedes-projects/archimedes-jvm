package io.archimedesfw.security.auth.micronaut

import io.archimedesfw.security.auth.UsernamePrincipal
import io.archimedesfw.security.auth.password.PasswordCredential
import io.archimedesfw.security.auth.password.UsernamePasswordAuthenticator
import io.micronaut.http.HttpRequest
import io.micronaut.scheduling.TaskExecutors.BLOCKING
import io.micronaut.scheduling.annotation.ExecuteOn
import io.micronaut.security.authentication.AuthenticationException
import io.micronaut.security.authentication.AuthenticationRequest
import io.micronaut.security.authentication.AuthenticationResponse
import io.micronaut.security.authentication.provider.AuthenticationProvider
import io.micronaut.transaction.SynchronousTransactionManager
import jakarta.inject.Singleton
import org.slf4j.LoggerFactory
import java.sql.Connection

@Singleton
internal class DefaultAuthenticationProvider(
    private val usernamePasswordAuthenticator: UsernamePasswordAuthenticator,
    private val tx: SynchronousTransactionManager<Connection>,
) : AuthenticationProvider<HttpRequest<*>, String, String> {

    private val log = LoggerFactory.getLogger(DefaultAuthenticationProvider::class.java)

    @ExecuteOn(BLOCKING)
    override fun authenticate(
        requestContext: HttpRequest<*>?,
        authRequest: AuthenticationRequest<String, String>
    ): AuthenticationResponse =
        try {
            authenticateRequest(authRequest)
        } catch (ex: AuthenticationException) {
            ex.response ?: AuthenticationResponse.failure(ex.message ?: cannotAuthenticateMessage(authRequest))
        } catch (th: Throwable) {
            log.error(cannotAuthenticateMessage(authRequest), th)
            AuthenticationResponse.failure()
        }

    private fun authenticateRequest(authRequest: AuthenticationRequest<String, String>): AuthenticationResponse {
        val subject = tx.executeRead {
            usernamePasswordAuthenticator.authenticate(
                UsernamePrincipal(authRequest.identity.toString()),
                PasswordCredential(authRequest.secret.toString())
            )
        }

        return AuthenticationResponse.success(
            subject.principal.name,
            subject.roles.map { it.name },
            subject.attributes
        )
    }

    private fun cannotAuthenticateMessage(authRequest: AuthenticationRequest<String, String>) =
        "Cannot authenticate identity: ${authRequest.identity}"

}
