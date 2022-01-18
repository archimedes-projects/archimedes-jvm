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

//    override val name: String?
//        get() = securityService.username().orElse(null)

    override fun getName(): String? = securityService.username().orElse(null)

    override fun isAuthenticated(): Boolean = securityService.isAuthenticated

    override fun hasRole(role: String): Boolean = securityService.hasRole(role)

}
