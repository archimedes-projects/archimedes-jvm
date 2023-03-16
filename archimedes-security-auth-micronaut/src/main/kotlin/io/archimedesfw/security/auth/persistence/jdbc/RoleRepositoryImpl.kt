package io.archimedesfw.security.auth.persistence.jdbc

import io.archimedesfw.security.auth.Role
import io.archimedesfw.security.auth.RoleRepository
import io.micronaut.data.jdbc.runtime.JdbcOperations
import jakarta.inject.Singleton
import java.sql.ResultSet

@Singleton
internal class RoleRepositoryImpl(
    private val jdbcOperations: JdbcOperations,
) : RoleRepository {

    private val table = "archimedes_security_role"
    private val insert = "INSERT INTO $table(name) VALUES (?)"
    private val select = "SELECT name FROM $table"
    private val findAll = "$select ORDER BY name"
    private val findByRole = "$select WHERE name = ?"

    override fun save(role: Role): Role =
        jdbcOperations.prepareStatement(insert) {
            it.setString(1, role.name)
            it.executeUpdate()
            role
        }

    override fun findAll(): List<Role> =
        jdbcOperations.prepareStatement(findAll) {
            val rs = it.executeQuery()
            extractData(rs)
        }

    override fun findByRole(role: String): Role? =
        jdbcOperations.prepareStatement(findByRole) {
            it.setString(1, role)
            val rs = it.executeQuery()
            extractData(rs)
        }.singleOrNull()

    override fun findByRoleIn(roles: Collection<String>): List<Role> {
        val findByRoleIn = buildString {
            append(select)
            append(" WHERE name IN (")
            repeat(roles.size - 1) { append("?,") }
            append("?) ORDER BY name")
        }

        return jdbcOperations.prepareStatement(findByRoleIn) {
            var parameterIndex = 1
            roles.forEach { role ->
                it.setString(parameterIndex++, role)
            }
            val rs = it.executeQuery()
            extractData(rs)
        }
    }

    private fun extractData(rs: ResultSet): List<Role> {
        val roles = mutableListOf<Role>()
        while (rs.next()) {
            roles.add(Role(rs.getString(1)))
        }
        return roles
    }

}
