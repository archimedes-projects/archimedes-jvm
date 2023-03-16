package io.archimedesfw.security.auth

import java.security.Principal

interface SubjectService {

    fun create(
        principal: Principal,
        roles: Set<Role>,
        attributes: Map<String, Any>
    ): Int

    fun find(principal: Principal): Subject?

    fun exists(principal: Principal): Boolean = find(principal) != null

}
