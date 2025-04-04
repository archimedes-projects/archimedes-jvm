package io.archimedesfw.security.test

import io.archimedesfw.security.SecurityContext

class FakeSecurityContext(
    private val name: String = DUMMY_USER_ID,
    private val permissions: Set<String> = emptySet()
) : SecurityContext {

    @Deprecated(
        "Instead use the more generic property 'name' that mimics java.security.Principal interface.",
        replaceWith = ReplaceWith("name"),
        level = DeprecationLevel.WARNING
    )
    override val username: String = name

    override fun getName(): String = name

    override fun isAuthenticated(): Boolean = true

    override fun hasRole(role: String): Boolean = permissions.contains(role)

    companion object {
        const val DUMMY_USER_ID: String = "dummy@test.com"
    }

}
