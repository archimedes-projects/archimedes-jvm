package io.archimedesfw.security.auth

internal interface RoleRepository {

    fun save(role: Role): Role

    fun findAll(): List<Role>

    fun findByRole(role: String): Role?

    fun findByRoleIn(roles: Collection<String>): List<Role>

}
