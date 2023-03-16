package io.archimedesfw.security.auth.persistence.jdbc

internal data class SubjectRoleRelation(
    val subjectId: Int,
    val role: String
)
