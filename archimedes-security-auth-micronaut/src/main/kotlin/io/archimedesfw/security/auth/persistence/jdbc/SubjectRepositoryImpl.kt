package io.archimedesfw.security.auth.persistence.jdbc

import io.archimedesfw.security.auth.Subject
import io.archimedesfw.security.auth.SubjectRepository
import io.archimedesfw.security.auth.UsernamePrincipal
import io.micronaut.data.jdbc.runtime.JdbcOperations
import jakarta.inject.Singleton
import java.sql.PreparedStatement
import java.sql.ResultSet

@Singleton
internal class SubjectRepositoryImpl(
    private val jdbcOperations: JdbcOperations,
    private val mapToJsonConverter: MapToJsonConverter,
    private val subjectRoleRelationDao: SubjectRoleRelationDao
) : SubjectRepository {

    private val table = "archimedes_security_subject"
    private val insert = "INSERT INTO $table(principal_name, attributes) VALUES (?, ?)"
    private val update = "UPDATE $table SET principal_name = ?, attributes = ? WHERE id = ?"
    private val select = "SELECT id, principal_name, attributes FROM $table"
    private val findById = "$select WHERE id = ?"
    private val findByPrincipal = "$select WHERE principal_name = ?"

    override fun save(subject: Subject): Subject {
        val savedSubject =
            if (subject.id == Subject.NEW) insert(subject)
            else update(subject)

        subjectRoleRelationDao.save(savedSubject)
        return savedSubject
    }

    private fun insert(subject: Subject): Subject =
        jdbcOperations.execute {
            val ps = it.prepareStatement(insert, arrayOf("id"))
            setUpdateValues(ps, subject)
            ps.executeUpdate()
            subject.copy(id = extractGeneratedId(ps))
        }

    private fun setUpdateValues(ps: PreparedStatement, subject: Subject) {
        ps.setString(1, subject.principal.name)
        ps.setString(2, mapToJsonConverter.toJson(subject.attributes))
    }

    private fun extractGeneratedId(ps: PreparedStatement): Int {
        val generatedKeysResultSet = ps.generatedKeys
        generatedKeysResultSet.next()
        return generatedKeysResultSet.getInt(1)
    }

    private fun update(subject: Subject): Subject =
        jdbcOperations.prepareStatement(update) {
            setUpdateValues(it, subject)
            it.setInt(3, subject.id)
            it.executeUpdate()
            subject
        }

    override fun findById(id: Int): Subject? =
        jdbcOperations.prepareStatement(findById) {
            it.setInt(1, id)
            val rs = it.executeQuery()
            extractData(rs)
        }.singleOrNull()

    private fun extractData(rs: ResultSet): List<Subject> {
        val subjects = mutableListOf<Subject>()
        while (rs.next()) {
            subjects.add(extractSubject(rs))
        }
        return subjects
    }

    private fun extractSubject(rs: ResultSet): Subject {
        val id = rs.getInt(1)
        val roles = subjectRoleRelationDao.findRolesBySubjectId(id)
        return Subject(
            id,
            UsernamePrincipal(rs.getString(2)),
            roles,
            mapToJsonConverter.toMap(rs.getString(3))
        )
    }

    override fun findByPrincipal(name: String): Subject? =
        jdbcOperations.prepareStatement(findByPrincipal) {
            it.setString(1, name)
            val rs = it.executeQuery()
            extractData(rs)
        }.singleOrNull()

}

