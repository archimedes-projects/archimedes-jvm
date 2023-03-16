package io.archimedesfw.security.auth

import io.micronaut.data.exceptions.DataAccessException
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.fail
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import org.junit.jupiter.api.assertThrows

@MicronautTest
@TestInstance(PER_CLASS)
internal class RoleRepositoryIT {

    @Inject
    private lateinit var roleRepository: RoleRepository

    @Test
    fun `save new roles`() {
        val role = Role("ROLE_1")

        val savedRole = roleRepository.save(role)

        assertEquals(role, savedRole)
    }

    @Test
    fun `cannot save more than one role with the same name`() {
        roleRepository.save(Role("ROLE_1"))

        val e = assertThrows<DataAccessException> {
            roleRepository.save(Role("ROLE_1"))
        }

        assertThat(e.message).contains(
            "duplicate key value violates unique constraint \"archimedes_security_role_pkey\""
        )
    }

    @Test
    fun `find role by name`() {
        val savedRole = roleRepository.save(Role("ROLE_1"))

        val foundRole = roleRepository.findByRole("ROLE_1")
            ?: fail("Cannot found role")

        assertEquals(savedRole, foundRole)
    }

    @Test
    fun `find role by name in`() {
        val expectedRoles = listOf(
            roleRepository.save(Role("ROLE_1")),
            roleRepository.save(Role("ROLE_2"))
        )
        roleRepository.save(Role("ROLE_3"))

        val foundRoles = roleRepository.findByRoleIn(listOf("ROLE_1", "UNKNOWN_ROLE", "ROLE_2"))

        assertEquals(expectedRoles, foundRoles)
    }

}
