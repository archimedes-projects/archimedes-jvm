package io.archimedesfw.security.test

import io.archimedesfw.security.SecurityContext

class FakeSecurityContext(
    override val username: String = DUMMY_USER_ID,
    private val permissions: Set<String> = emptySet()
) : SecurityContext {

    override fun has(permission: String): Boolean = permissions.contains(permission)

    companion object {
        val DUMMY_USER_ID = "dummy@test.com"
    }

}
