package io.archimedesfw.security.auth.token.refresh

internal interface RefreshTokenRepository {

    fun save(refreshToken: RefreshToken): RefreshToken

    fun findByRefreshToken(refreshToken: String): RefreshToken?

    fun deleteByPrincipalName(name: String)

}
