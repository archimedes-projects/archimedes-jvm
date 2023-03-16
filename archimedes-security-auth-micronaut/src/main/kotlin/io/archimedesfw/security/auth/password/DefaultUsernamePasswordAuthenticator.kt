package io.archimedesfw.security.auth.password

import io.archimedesfw.security.auth.Subject
import io.archimedesfw.security.auth.SubjectRepository
import io.micronaut.security.authentication.AuthenticationException
import io.micronaut.security.authentication.AuthenticationFailed
import io.micronaut.security.authentication.AuthenticationFailureReason.CREDENTIALS_DO_NOT_MATCH
import io.micronaut.security.authentication.AuthenticationFailureReason.USER_NOT_FOUND
import jakarta.inject.Singleton
import java.security.Principal

@Singleton
internal class DefaultUsernamePasswordAuthenticator(
    private val subjectRepository: SubjectRepository,
    private val passwordRepository: PasswordRepository,
    private val passwordEncoder: PasswordEncoder,
) : UsernamePasswordAuthenticator {

    override fun authenticate(principal: Principal, password: PasswordCredential): Subject {
        val subject = subjectRepository.findByPrincipal(principal.name)
            ?: throw AuthenticationException(AuthenticationFailed(USER_NOT_FOUND))

        val passwordCredential = passwordRepository.findBySubjectId(subject.id)
            ?: throw AuthenticationException(AuthenticationFailed(CREDENTIALS_DO_NOT_MATCH))

        if (!passwordEncoder.matches(password.secret, passwordCredential.secret))
            throw AuthenticationException(AuthenticationFailed(CREDENTIALS_DO_NOT_MATCH))

        return subject
    }

}
