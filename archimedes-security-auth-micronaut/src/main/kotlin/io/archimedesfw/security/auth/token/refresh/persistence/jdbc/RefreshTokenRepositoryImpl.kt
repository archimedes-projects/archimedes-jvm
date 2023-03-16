package io.archimedesfw.security.auth.token.refresh.persistence.jdbc

import io.archimedesfw.security.auth.token.refresh.RefreshToken
import io.archimedesfw.security.auth.token.refresh.RefreshTokenRepository
import io.micronaut.data.jdbc.runtime.JdbcOperations
import jakarta.inject.Singleton
import java.sql.ResultSet

@Singleton
internal class RefreshTokenRepositoryImpl(
    private val jdbcOperations: JdbcOperations
) : RefreshTokenRepository {

    private val table = "archimedes_security_refresh_token"
    private val insert = "INSERT INTO $table(refresh_token, principal_name) VALUES (?, ?)"
    private val deleteByPrincipalName = "DELETE FROM $table WHERE principal_name = ?"
    private val select = "SELECT refresh_token, principal_name FROM $table"
    private val findByRefreshToken = "$select WHERE refresh_token = ?"

    override fun save(refreshToken: RefreshToken): RefreshToken {
        deleteByPrincipalName(refreshToken.principalName)
        return jdbcOperations.prepareStatement(insert) {
            it.setString(1, refreshToken.refreshToken)
            it.setString(2, refreshToken.principalName)
            it.executeUpdate()
            refreshToken
        }
    }

    override fun deleteByPrincipalName(name: String) {
        jdbcOperations.prepareStatement(deleteByPrincipalName) {
            it.setString(1, name)
            it.executeUpdate()
        }
    }

    override fun findByRefreshToken(refreshToken: String): RefreshToken? =
        jdbcOperations.prepareStatement(findByRefreshToken) {
            it.setString(1, refreshToken)
            val rs = it.executeQuery()
            extractData(rs)
        }.singleOrNull()

    private fun extractData(rs: ResultSet): List<RefreshToken> {
        val refreshTokens = mutableListOf<RefreshToken>()
        while (rs.next()) {
            refreshTokens.add(RefreshToken(rs.getString(1), rs.getString(2)))
        }
        return refreshTokens
    }

}
