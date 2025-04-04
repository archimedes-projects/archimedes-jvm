package io.archimedesfw.security.auth.micronaut

import io.archimedesfw.security.auth.SubjectService
import io.archimedesfw.security.auth.token.oauth2.PrincipalExtractor
import io.micronaut.scheduling.TaskExecutors
import io.micronaut.security.authentication.AuthenticationResponse
import io.micronaut.security.oauth2.endpoint.authorization.state.State
import io.micronaut.security.oauth2.endpoint.token.response.OpenIdAuthenticationMapper
import io.micronaut.security.oauth2.endpoint.token.response.OpenIdClaims
import io.micronaut.security.oauth2.endpoint.token.response.OpenIdTokenResponse
import io.micronaut.transaction.SynchronousTransactionManager
import jakarta.inject.Named
import jakarta.inject.Singleton
import org.reactivestreams.Publisher
import reactor.core.publisher.Mono
import reactor.core.scheduler.Scheduler
import java.sql.Connection

@Singleton
@Named("google")
internal open class GoogleOpenIdAuthenticationMapper(
    private val principalExtractor: PrincipalExtractor,
    private val subjectService: SubjectService,
    private val tx: SynchronousTransactionManager<Connection>,
    @Named(TaskExecutors.BLOCKING) private val blockingScheduler: Scheduler
) : OpenIdAuthenticationMapper {

    override fun createAuthenticationResponse(
        providerName: String,
        tokenResponse: OpenIdTokenResponse,
        openIdClaims: OpenIdClaims,
        state: State?
    ): Publisher<AuthenticationResponse> {
        return Mono.create { emitter ->
            try {
                emitter.success(createAuthenticationResponse(providerName, openIdClaims))
            } catch (ex: Throwable) {
                emitter.error(ex)
            }
        }.subscribeOn(blockingScheduler)
    }

    private fun createAuthenticationResponse(providerName: String, openIdClaims: OpenIdClaims): AuthenticationResponse {
        val principal = principalExtractor.extractPrincipal(openIdClaims)
            ?: return AuthenticationResponse.failure("Cannot extract principal from $providerName token")

        val subject = tx.executeRead {
            subjectService.find(principal)
        } ?: return AuthenticationResponse.failure("Cannot authenticate principal ${principal.name}")

        val sub = subject.attributes[SUBJECT_TOKEN_KEY] ?: subject.id

        return AuthenticationResponse.success(
            sub.toString(),
            subject.roles.map { it.name },
            subject.attributes
        )
    }

    private companion object {
        private const val SUBJECT_TOKEN_KEY = "sub"
    }

}
