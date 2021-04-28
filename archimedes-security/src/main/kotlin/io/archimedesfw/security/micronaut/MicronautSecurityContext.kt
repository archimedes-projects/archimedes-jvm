package io.archimedesfw.security.micronaut

import io.archimedesfw.security.Security
import io.archimedesfw.security.SecurityContext
import io.archimedesfw.security.ThreadLocalSecurityContextHolder
import io.micronaut.context.annotation.Context
import io.micronaut.context.annotation.Value
import io.micronaut.security.token.config.TokenConfiguration
import io.micronaut.security.token.config.TokenConfigurationProperties
import io.micronaut.security.utils.SecurityService

@Context
class MicronautSecurityContext(
    private val securityService: SecurityService,

    // We use the same rolesNames that is configured in the `application.yml`.
    // If the property `roles-name` is not set we use the same default value than Micronaut.
    @Value("\${${TokenConfigurationProperties.PREFIX}.roles-name:${TokenConfiguration.DEFAULT_ROLES_NAME}}")
    private val rolesName: String

) : SecurityContext {

    init {
        Security.initializeSecurityContextHolder(
            ThreadLocalSecurityContextHolder { this }
        )
    }

    override val username: String
        get() = securityService.username().orElseThrow { IllegalStateException("Check failed.") }

    override fun has(permission: String): Boolean = securityService.hasRole(permission, rolesName)

}
