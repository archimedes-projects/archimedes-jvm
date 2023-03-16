package io.archimedesfw.security.auth.token.refresh

import io.archimedesfw.security.auth.SecretCredential

internal data class RefreshTokenCredential(
    override val secret: String
) : SecretCredential {

    init {
        require(secret.isNotBlank()) { "refreshToken cannot be blank" }
    }

}
