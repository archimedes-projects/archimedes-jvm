package io.archimedesfw.security.auth.token.refresh

import io.archimedesfw.security.auth.UsernamePrincipal
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class DefaultRefreshTokenServiceTest {

    private val refreshTokenRepository = mock<RefreshTokenRepository>()
    private val refreshTokenService = DefaultRefreshTokenService(refreshTokenRepository)

    @Test
    fun `save refresh token`() {
        refreshTokenService.reset(UsernamePrincipal("principal"), RefreshTokenCredential("refresh-token"))

        verify(refreshTokenRepository).save(RefreshToken("refresh-token", "principal"))
    }

}
