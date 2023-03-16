package io.archimedesfw.security.auth

import java.security.Principal

data class Subject(
    val id: Int,
    val principal: Principal,
    val roles: Set<Role>,
    val attributes: Map<String, Any>
) {

    internal companion object {
        internal const val NEW = -1
    }

}
