package io.archimedesfw.security.auth.password

import io.archimedesfw.security.auth.SecretCredential

data class PasswordCredential(
    override val secret: String
) : SecretCredential {

    init {
        require(secret.isNotBlank()) { "password cannot be blank" }
    }

}
