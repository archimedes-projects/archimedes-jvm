package io.archimedesfw.security.auth.password

import io.archimedesfw.security.auth.SubjectRepository
import jakarta.inject.Singleton
import java.security.Principal

@Singleton
internal class DefaultPasswordService(
    private val subjectRepository: SubjectRepository,
    private val passwordEncoder: PasswordEncoder,
    private val passwordRepository: PasswordRepository
): PasswordService {

    override fun reset(principal: Principal, newPassword: PasswordCredential) {
        val subject = subjectRepository.findByPrincipal(principal.name)
            ?: return // If subject doesn't exist, do nothing

        val encodedCredential = encode(newPassword)
        passwordRepository.save(subject.id, encodedCredential)
    }

    private fun encode(rawCredential: PasswordCredential): PasswordCredential =
        PasswordCredential(passwordEncoder.encode(rawCredential.secret))

}
