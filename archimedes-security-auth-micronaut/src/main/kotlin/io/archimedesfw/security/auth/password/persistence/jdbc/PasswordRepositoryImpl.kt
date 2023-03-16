package io.archimedesfw.security.auth.password.persistence.jdbc

import io.archimedesfw.security.auth.password.PasswordCredential
import io.archimedesfw.security.auth.password.PasswordRepository
import io.micronaut.data.jdbc.runtime.JdbcOperations
import jakarta.inject.Singleton
import java.sql.ResultSet

@Singleton
internal class PasswordRepositoryImpl(
    private val jdbcOperations: JdbcOperations,
) : PasswordRepository {

    private val table = "archimedes_security_password"
    private val insert = "INSERT INTO $table(subject_id, secret) VALUES (?, ?)"
    private val deleteBySubjectId = "DELETE FROM $table WHERE subject_id = ?"
    private val select = "SELECT secret FROM $table"
    private val findBySubjectId = "$select WHERE subject_id = ?"

    override fun save(subjectId: Int, password: PasswordCredential): PasswordCredential {
        deleteBySubjectId(subjectId)
        return jdbcOperations.prepareStatement(insert) {
            it.setInt(1, subjectId)
            it.setString(2, password.secret)
            it.executeUpdate()
            password
        }
    }

    fun deleteBySubjectId(subjectId: Int) {
        jdbcOperations.prepareStatement(deleteBySubjectId) {
            it.setInt(1, subjectId)
            it.executeUpdate()
        }
    }

    override fun findBySubjectId(subjectId: Int): PasswordCredential? =
        jdbcOperations.prepareStatement(findBySubjectId) {
            it.setInt(1, subjectId)
            val rs = it.executeQuery()
            extractData(rs)
        }.singleOrNull()

    private fun extractData(rs: ResultSet): List<PasswordCredential> {
        val passwords = mutableListOf<PasswordCredential>()
        while (rs.next()) {
            passwords.add(PasswordCredential(rs.getString(1)))
        }
        return passwords
    }

}
