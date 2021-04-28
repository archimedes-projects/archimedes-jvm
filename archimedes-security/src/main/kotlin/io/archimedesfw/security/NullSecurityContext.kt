package io.archimedesfw.security

internal object NullSecurityContext : SecurityContext {

    override val username: String
        get() = throw IllegalStateException("No security context has been set")

    override fun checkUsername(username: String) = throw IllegalStateException("No security context has been set")

    override fun has(permission: String): Boolean = throw IllegalStateException("No security context has been set")

}
