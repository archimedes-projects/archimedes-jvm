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
        val result = block()
        contextHolder.set(previous)
        return result
    }

    internal val context: SecurityContext
        get() = contextHolder.get()

    override val username: String
        get() = context.username

    override fun checkUsername(username: String) {
        context.checkUsername(username)
    }

    override fun has(permission: String): Boolean = context.has(permission)

}
