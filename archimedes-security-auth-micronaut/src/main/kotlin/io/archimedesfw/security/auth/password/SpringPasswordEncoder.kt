package io.archimedesfw.security.auth.password

import jakarta.inject.Singleton
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Singleton
internal class SpringPasswordEncoder : PasswordEncoder {

    private val delegate = BCryptPasswordEncoder()

    override fun encode(rawPassword: String): String =
        delegate.encode(rawPassword)

    override fun matches(rawPassword: String, encodedPassword: String): Boolean =
        delegate.matches(rawPassword, encodedPassword)

}
