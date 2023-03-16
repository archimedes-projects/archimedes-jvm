package io.archimedesfw.security.auth.micronaut

import io.archimedesfw.security.auth.UsernamePrincipal
import io.archimedesfw.security.auth.password.PasswordCredential
import io.archimedesfw.security.auth.password.UsernamePasswordAuthenticator
import io.micronaut.http.HttpRequest
import io.micronaut.scheduling.TaskExecutors
import io.micronaut.security.authentication.Authentication
import io.micronaut.security.authentication.AuthenticationException
import io.micronaut.security.authentication.AuthenticationProvider
import io.micronaut.security.authentication.AuthenticationRequest
import io.micronaut.security.authentication.AuthenticationResponse
import io.micronaut.transaction.SynchronousTransactionManager
import jakarta.inject.Named
import jakarta.inject.Singleton
import org.reactivestreams.Publisher
import org.slf4j.LoggerFactory
import reactor.core.publisher.Mono
import reactor.core.scheduler.Scheduler
import java.sql.Connection
import java.util.Optional

@Singleton
internal class DefaultAuthenticationProvider(
    private val usernamePasswordAuthenticator: UsernamePasswordAuthenticator,
    private val tx: SynchronousTransactionManager<Connection>,
    @Named(TaskExecutors.IO) private val ioScheduler: Scheduler
) : AuthenticationProvider {

    private val log = LoggerFactory.getLogger(DefaultAuthenticationProvider::class.java)

    override fun authenticate(
        httpRequest: HttpRequest<*>?,
        authenticationRequest: AuthenticationRequest<*, *>
    ): Publisher<AuthenticationResponse> =
        Mono.create<AuthenticationResponse> { emitter ->
            try {
                val subject = tx.executeRead {
                    usernamePasswordAuthenticator.authenticate(
                        UsernamePrincipal(authenticationRequest.identity.toString()),
                        PasswordCredential(authenticationRequest.secret.toString())
                    )
                }

                val authentication = Authentication.build(
                    subject.principal.name,
                    subject.roles.map { it.name },
                    subject.attributes
                )
                emitter.success { Optional.of(authentication) }

            } catch (ex: AuthenticationException) {
                emitter.error(ex)
            } catch (th: Throwable) {
                log.error("Cannot authenticate identity: ${authenticationRequest.identity}", th)
                emitter.error(AuthenticationResponse.exception())
            }

        }.subscribeOn(ioScheduler)

}
