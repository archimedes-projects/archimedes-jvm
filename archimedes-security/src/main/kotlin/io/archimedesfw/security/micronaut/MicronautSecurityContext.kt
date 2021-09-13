package io.archimedesfw.security.micronaut

import io.archimedesfw.security.Security
import io.archimedesfw.security.SecurityContext
import io.archimedesfw.security.ThreadLocalSecurityContextHolder
import io.micronaut.context.annotation.Context
import io.micronaut.security.utils.SecurityService

@Context
class MicronautSecurityContext(
    private val securityService: SecurityService,
) : SecurityContext {

    init {
        Security.initializeSecurityContextHolder(
            ThreadLocalSecurityContextHolder { this }
        )
    }

    override val username: String
        get() = securityService.username().orElseThrow { IllegalStateException("Check failed.") }

    override fun has(permission: String): Boolean = securityService.hasRole(permission)

}
