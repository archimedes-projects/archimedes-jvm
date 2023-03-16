package io.archimedesfw.security.auth.micronaut

import io.archimedesfw.security.auth.SubjectService
import io.archimedesfw.security.auth.token.oauth2.PrincipalExtractor
import io.micronaut.security.authentication.AuthenticationResponse
import io.micronaut.security.oauth2.endpoint.authorization.state.State
import io.micronaut.security.oauth2.endpoint.token.response.OpenIdAuthenticationMapper
import io.micronaut.security.oauth2.endpoint.token.response.OpenIdClaims
import io.micronaut.security.oauth2.endpoint.token.response.OpenIdTokenResponse
import io.micronaut.transaction.SynchronousTransactionManager
import jakarta.inject.Named
import jakarta.inject.Singleton
import java.sql.Connection

@Singleton
@Named("google")
internal open class GoogleOpenIdAuthenticationMapper(
    private val principalExtractor: PrincipalExtractor,
    private val subjectService: SubjectService,
    private val tx: SynchronousTransactionManager<Connection>
) : OpenIdAuthenticationMapper {

    override fun createAuthenticationResponse(
        providerName: String,
        tokenResponse: OpenIdTokenResponse,
        openIdClaims: OpenIdClaims,
        state: State?
    ): AuthenticationResponse {
        val principal = principalExtractor.extractPrincipal(openIdClaims)
            ?: return AuthenticationResponse.failure("Cannot extract principal from $providerName token")

        val subject = tx.executeRead {
            subjectService.find(principal)
        } ?: return AuthenticationResponse.failure("Cannot authenticate principal ${principal.name}")

        val sub = subject.attributes[SUBJECT_TOKEN_KEY]?.toString()
            ?: subject.id.toString()
        
        return AuthenticationResponse.success(
            sub,
            subject.roles.map { it.name },
            subject.attributes
        )
    }

    private companion object {
        private const val SUBJECT_TOKEN_KEY = "sub"
    }

}
