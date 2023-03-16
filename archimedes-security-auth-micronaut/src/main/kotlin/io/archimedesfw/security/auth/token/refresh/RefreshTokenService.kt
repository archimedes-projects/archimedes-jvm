package io.archimedesfw.security.auth.token.refresh

import java.security.Principal

internal interface RefreshTokenService {

    fun reset(principal: Principal, newRefreshToken: RefreshTokenCredential)

}
