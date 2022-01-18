package io.archimedesfw.security

import java.security.Principal
import kotlin.DeprecationLevel.WARNING

interface SecurityContext : Principal {

    /**
     * The name of the current principal.
     *
     * @return the name of the current principal
     * @throws an exception if there is not any principal authenticated
     */
    @Deprecated(
        message = "Instead use the more generic property 'name' that mimics java.security.Principal interface.",
        replaceWith = ReplaceWith("name"),
        level = WARNING
    )
    val username: String

    @Deprecated(
        message = "This kind of check should be done in the application and not in this library, because de application needs to control the produced error.",
        replaceWith = ReplaceWith("check(Security.username == username)"),
        level = WARNING
    )
    fun checkUsername(username: String) {
        check(this.username == username)
    }

    /**
     * Check if a principal is authenticated.
     *
     * @return true if a principal is authenticated, false otherwise
     */
    fun isAuthenticated(): Boolean

    @Deprecated(
        message = "Instead use function 'hasRole()' with more specific name.",
        replaceWith = ReplaceWith("hasRole(permission"),
        level = WARNING
    )
    fun has(permission: String): Boolean = hasRole(permission)

    /**
     * If the current principal has a specific role.
     *
     * @param role the role to check
     * @return true if the current principal has the role, false otherwise
     */
    fun hasRole(role: String): Boolean

}
