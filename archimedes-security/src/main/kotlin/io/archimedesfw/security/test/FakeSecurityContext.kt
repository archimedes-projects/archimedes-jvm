package io.archimedesfw.security.test

import io.archimedesfw.security.SecurityContext

class FakeSecurityContext(
    private val name: String = DUMMY_USER_ID,
    private val permissions: Set<String> = emptySet()
) : SecurityContext {

    override val username: String = name

    override fun getName(): String = name

    override fun isAuthenticated(): Boolean = true

    override fun hasRole(role: String): Boolean = permissions.contains(role)

    companion object {
        val DUMMY_USER_ID: String = "dummy@test.com"
    }

}
