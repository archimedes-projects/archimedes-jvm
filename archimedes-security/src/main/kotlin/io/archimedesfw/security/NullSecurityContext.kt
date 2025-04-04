package io.archimedesfw.security

internal object NullSecurityContext : SecurityContext {

    @Deprecated(
        "Instead use the more generic property 'name' that mimics java.security.Principal interface.",
        replaceWith = ReplaceWith("name"),
        level = DeprecationLevel.WARNING
    )
    override val username: String
        get() = throw IllegalStateException("No security context has been set.")

    override fun getName(): String? = throw IllegalStateException("No security context has been set.")

    @Deprecated(
        "This kind of check should be done in the application and not in this library, because de application needs to control the produced error.",
        replaceWith = ReplaceWith("check(Security.name == username)"),
        level = DeprecationLevel.WARNING
    )
    override fun checkUsername(username: String) = throw IllegalStateException("No security context has been set.")

    override fun isAuthenticated(): Boolean = throw IllegalStateException("No security context has been set.")

    override fun hasRole(role: String): Boolean = throw IllegalStateException("No security context has been set.")

}
