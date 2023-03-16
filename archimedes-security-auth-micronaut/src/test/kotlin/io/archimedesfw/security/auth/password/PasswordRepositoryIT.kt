package io.archimedesfw.security.auth.password

import io.archimedesfw.security.auth.Subject
import io.archimedesfw.security.auth.Subject.Companion.NEW
import io.archimedesfw.security.auth.SubjectMotherPersistent
import io.archimedesfw.security.auth.SubjectRepository
import io.archimedesfw.security.auth.UsernamePrincipal
import io.micronaut.data.exceptions.DataAccessException
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.random.Random

@MicronautTest
class PasswordRepositoryIT {

    @Inject
    private lateinit var subjectMotherPersistent: SubjectMotherPersistent

    @Inject
    private lateinit var passwordRepository: PasswordRepository

    @Test
    fun `cannot save password if subject doesn't exist`() {
        val nonExitingSubjectId = -1
        val e = assertThrows<DataAccessException> {
            passwordRepository.save(nonExitingSubjectId, PasswordCredential("secret"))
        }

        assertThat(e.message).contains(
            "insert or update on table \"archimedes_security_password\" violates foreign key constraint \"archimedes_security_password_subject_id_fkey\""
        )
    }

    @Test
    fun `save password`() {
        val subject = aSubject()
        val password = PasswordCredential("secret")

        val savedPassword = passwordRepository.save(subject.id, password)

        assertEquals(password, savedPassword)
    }

    @Test
    fun `updates previous password`() {
        val subject = aSubject()
        passwordRepository.save(subject.id, PasswordCredential("secret1"))

        val updatedPassword = passwordRepository.save(subject.id, PasswordCredential("secret2"))

        val foundPassword = passwordRepository.findBySubjectId(subject.id)
        assertEquals(updatedPassword, foundPassword)
    }

    @Test
    fun `find by subject id`() {
        val subject = aSubject()
        val password = passwordRepository.save(subject.id, PasswordCredential("secret-${Random.nextInt()}"))

        val foundPassword = passwordRepository.findBySubjectId(subject.id)

        assertEquals(password, foundPassword)
    }

    private fun aSubject() = subjectMotherPersistent.subject("username")

}
