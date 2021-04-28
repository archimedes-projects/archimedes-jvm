package io.archimedesfw.security

interface SecurityContext {

    val username: String

    fun checkUsername(username: String) {
        check(this.username == username)
    }

    fun has(permission: String): Boolean

}

