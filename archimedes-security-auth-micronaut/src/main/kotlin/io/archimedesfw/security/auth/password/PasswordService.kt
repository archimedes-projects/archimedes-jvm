package io.archimedesfw.security.auth.password

import java.security.Principal

interface PasswordService {

    fun reset(principal: Principal, newPassword: PasswordCredential)

}
