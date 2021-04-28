package io.archimedesfw.security

internal class ThreadLocalSecurityContextHolder(
    initialSecurityContextSupplier: () -> SecurityContext
) : SecurityContextHolder {

    private val CONTEXT = ThreadLocal.withInitial(initialSecurityContextSupplier)

    override fun get(): SecurityContext {
        return CONTEXT.get()
    }

    override fun set(securityContext: SecurityContext) {
        CONTEXT.set(securityContext)
    }

}
