package io.archimedesfw.security.auth.token.refresh

internal data class RefreshToken(
    val refreshToken: String,
    val principalName: String
) {

    init {
        require(refreshToken.isNotBlank()) { "refreshToken cannot be blank" }
        require(principalName.isNotBlank()) { "principalName cannot be blank" }
    }

}
