package io.archimedesfw.security

object Security : SecurityContext {

    private val NULL_SECURITY_CONTEXT_HOLDER = ThreadLocalSecurityContextHolder { NullSecurityContext }

    private var contextHolder: SecurityContextHolder = NULL_SECURITY_CONTEXT_HOLDER

    internal fun initializeSecurityContextHolder(holder: SecurityContextHolder) {
        check(
            contextHolder === NULL_SECURITY_CONTEXT_HOLDER ||
                    contextHolder::class.qualifiedName == holder::class.qualifiedName
        ) { "Cannot initialize security context holder twice. Already initialized $contextHolder" }
        contextHolder = holder
    }

    fun <R> runAs(securityContext: SecurityContext, block: () -> R): R {
        val previous = contextHolder.get()
        contextHolder.set(securityContext)
        try {
            return block()
        } catch (ex: Exception) {
            throw ex
        } finally {
            contextHolder.set(previous)
        }
    }

    internal val context: SecurityContext
        get() = contextHolder.get()

    @Deprecated(
        "Instead use the more generic property 'name' that mimics java.security.Principal interface.",
        replaceWith = ReplaceWith("name"),
        level = DeprecationLevel.WARNING
    )
    override val username: String
        get() = context.username

    override fun getName(): String? = context.name

    @Deprecated(
        "This kind of check should be done in the application and not in this library, because de application needs to control the produced error.",
        replaceWith = ReplaceWith("check(Security.username == username)"),
        level = DeprecationLevel.WARNING
    )
    override fun checkUsername(username: String) {
        context.checkUsername(username)
    }

    override fun isAuthenticated(): Boolean = context.isAuthenticated()

    override fun hasRole(role: String): Boolean = context.hasRole(role)

}
