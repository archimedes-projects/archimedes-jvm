package io.archimedesfw.security.auth

import jakarta.inject.Singleton

@Singleton
internal class DefaultRoleService(
    private val roleRepository: RoleRepository
) : RoleService {

    override fun require(roleNames: Collection<String>): List<Role> {
        val roles = roleRepository.findByRoleIn(roleNames)
        if (roles.size == roleNames.size) return roles

        val rolesNotFound = roleNames.toMutableList()
        roles.forEach { rolesNotFound.remove(it.name) }
        throw IllegalArgumentException("Unknown roles: ${rolesNotFound.joinToString()}")
    }

}
