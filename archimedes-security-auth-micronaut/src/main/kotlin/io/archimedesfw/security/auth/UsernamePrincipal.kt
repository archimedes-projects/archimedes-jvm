package io.archimedesfw.security.auth

import java.security.Principal

data class UsernamePrincipal(
    private val name: String
) : Principal {

    init {
        require(name.isNotBlank()) { "username cannot be blank" }
    }

    override fun getName(): String = name

}
