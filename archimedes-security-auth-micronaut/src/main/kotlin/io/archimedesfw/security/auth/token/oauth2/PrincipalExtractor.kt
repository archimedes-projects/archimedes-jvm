package io.archimedesfw.security.auth.token.oauth2

import io.micronaut.security.oauth2.endpoint.token.response.OpenIdClaims
import java.security.Principal

interface PrincipalExtractor {

    fun extractPrincipal(openIdClaims: OpenIdClaims): Principal?

}
