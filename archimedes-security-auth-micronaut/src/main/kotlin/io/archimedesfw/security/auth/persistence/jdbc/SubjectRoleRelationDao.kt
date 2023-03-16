package io.archimedesfw.security.auth.persistence.jdbc

import io.archimedesfw.security.auth.Role
import io.archimedesfw.security.auth.Subject
import io.micronaut.data.jdbc.runtime.JdbcOperations
import jakarta.inject.Singleton
import java.sql.ResultSet

@Singleton
internal class SubjectRoleRelationDao(
    private val jdbcOperations: JdbcOperations
) {

    private val table = "archimedes_security_subject_role_relation"
    private val insert = "INSERT INTO $table(subject_id, role_name) VALUES "
    private val select = "SELECT subject_id, role_name FROM $table"
    private val findBySubjectId = "$select WHERE subject_id = ? ORDER BY role_name"
    private val deleteBySubjectId = "DELETE FROM $table WHERE subject_id = ?"

    internal fun save(subject: Subject) {
        deleteBySubjectId(subject.id)
        insertRelations(subject)
    }

    internal fun deleteBySubjectId(subjectId: Int) {
        jdbcOperations.prepareStatement(deleteBySubjectId) {
            it.setInt(1, subjectId)
            it.executeUpdate()
        }
    }

    internal fun insertRelations(subject: Subject) {
        val relations = subject.roles.map { SubjectRoleRelation(subject.id, it.name) }
        insertAll(relations)
    }

    private fun insertAll(relations: List<SubjectRoleRelation>) {
        if (relations.isEmpty()) return
        val insertAll = buildString {
            append(insert)
            repeat(relations.size - 1) { append("(?, ?), ") }
            append("(?, ?)")
        }
        jdbcOperations.prepareStatement(insertAll) {
            var parameterIndex = 1
            relations.forEach { relation ->
                it.setInt(parameterIndex++, relation.subjectId)
                it.setString(parameterIndex++, relation.role)
            }
            it.executeUpdate()
        }
    }

    internal fun findRolesBySubjectId(subjectId: Int) : Set<Role> =
        jdbcOperations.prepareStatement(findBySubjectId) {
            it.setInt(1, subjectId)
            val rs = it.executeQuery()
            extractData(rs)
        }

    private fun extractData(rs: ResultSet): Set<Role> {
        val roles = mutableSetOf<Role>()
        while (rs.next()) {
            roles.add(Role(rs.getString(2)))
        }
        return roles
    }

}
