package io.archimedesfw.security.auth.token.refresh

import jakarta.inject.Singleton
import java.security.Principal

@Singleton
internal class DefaultRefreshTokenService internal constructor(
    private val refreshTokenRepository: RefreshTokenRepository
) : RefreshTokenService {

    override fun reset(principal: Principal, newRefreshToken: RefreshTokenCredential) {
        refreshTokenRepository.save(RefreshToken(newRefreshToken.secret, principal.name))
    }

}
