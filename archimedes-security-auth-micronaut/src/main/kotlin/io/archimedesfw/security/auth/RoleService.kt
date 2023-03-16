package io.archimedesfw.security.auth

interface RoleService {

    fun require(roleNames: Collection<String>): List<Role>

}
