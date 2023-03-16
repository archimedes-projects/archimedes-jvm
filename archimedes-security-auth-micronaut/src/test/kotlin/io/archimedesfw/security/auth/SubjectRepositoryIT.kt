package io.archimedesfw.security.auth

import io.micronaut.data.exceptions.DataAccessException
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import io.micronaut.transaction.SynchronousTransactionManager
import jakarta.inject.Inject
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import org.junit.jupiter.api.assertThrows
import java.sql.Connection
import kotlin.random.Random

@MicronautTest
@TestInstance(PER_CLASS)
internal class SubjectRepositoryIT {

    @Inject
    private lateinit var roleRepository: RoleRepository

    @Inject
    private lateinit var subjectRepository: SubjectRepository

    @Inject
    private lateinit var tx: SynchronousTransactionManager<Connection>

    private lateinit var roles: Set<Role>
    private lateinit var newSubject: Subject

    private val subjectMother = SubjectMother()

    @BeforeAll
    internal fun initDatabase() {
        val random = Random.nextInt()
        val newRoles = listOf(
            Role("ROLE_${this::class.simpleName}_${random}_1"),
            Role("ROLE_${this::class.simpleName}_${random}_2")
        )
        tx.executeWrite {
            roles = setOf(
                roleRepository.save(newRoles[0]),
                roleRepository.save(newRoles[1])
            )
        }

        newSubject = subjectMother.subject("username", roles)
    }

    @Test
    fun `save new`() {
        val savedIdentity = subjectRepository.save(newSubject)

        val expectedIdentity = newSubject.copy(id = savedIdentity.id)
        assertNotNull(savedIdentity.id)
        assertEquals(expectedIdentity, savedIdentity)
    }

    @Test
    fun `update existing`() {
        var saved = subjectRepository.save(newSubject)

        val modified = saved.copy(
            roles = saved.roles.drop(1).toSet()
        )
        saved = subjectRepository.save(modified)

        assertEquals(modified, saved)
    }

    @Test
    fun `cannot save two subjects with the same principal`() {
        subjectRepository.save(newSubject)

        val duplicateSubject = subjectMother.subject("username")
        val e = assertThrows<DataAccessException> {
            subjectRepository.save(duplicateSubject)
        }

        assertThat(e.message).contains(
            "duplicate key value violates unique constraint \"archimedes_security_subject_principal_name_key\""
        )
    }

    @Test
    fun `find by principal`() {
        val savedIdentity = subjectRepository.save(newSubject)

        val foundIdentity = subjectRepository.findByPrincipal(newSubject.principal.name)

        assertEquals(savedIdentity, foundIdentity)
    }

}
