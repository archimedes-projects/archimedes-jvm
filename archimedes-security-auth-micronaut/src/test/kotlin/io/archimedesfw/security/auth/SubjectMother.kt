package io.archimedesfw.security.auth

internal class SubjectMother {

    internal fun subject(username: String = "username", roles: Set<Role> = emptySet()): Subject =
        Subject(
            Subject.NEW,
            UsernamePrincipal(username),
            roles,
            mapOf("sub" to 1)
        )

}

