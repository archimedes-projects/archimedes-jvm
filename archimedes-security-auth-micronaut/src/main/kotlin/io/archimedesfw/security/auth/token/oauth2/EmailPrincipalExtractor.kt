package io.archimedesfw.security.auth.token.oauth2

import io.archimedesfw.security.auth.UsernamePrincipal
import io.micronaut.security.oauth2.endpoint.token.response.OpenIdClaims
import java.security.Principal

class EmailPrincipalExtractor : PrincipalExtractor {

    override fun extractPrincipal(openIdClaims: OpenIdClaims): Principal? =
        openIdClaims.email?.let { UsernamePrincipal(it) }

}
