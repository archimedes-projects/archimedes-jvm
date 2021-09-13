package io.archimedesfw.usecase.audit.persistence.jdbc

import io.archimedesfw.commons.lang.getAny
import io.archimedesfw.data.jdbc.JdbcTemplate
import io.archimedesfw.data.jdbc.checkRows
import io.archimedesfw.usecase.audit.AuditLog
import io.archimedesfw.usecase.audit.persistence.AuditRepository
import jakarta.inject.Singleton
import java.sql.ResultSet

@Singleton
internal class JdbcAuditRepository(
    private val jdbc: JdbcTemplate
) : AuditRepository {

    override fun save(auditLog: AuditLog) {
        val updatedRows = jdbc.update(INSERT) {
            it.setObject(1, auditLog.timestamp)
            it.setLong(2, auditLog.elapsedTime)
            it.setString(3, auditLog.userId.take(USER_ID_MAX_SIZE))
            it.setString(4, auditLog.useCase.take(USECASE_MAX_SIZE))
            it.setBoolean(5, auditLog.readOnly)
            it.setBoolean(6, auditLog.success)
            it.setString(7, auditLog.arguments.take(ARGUMENTS_MAX_SIZE))
            it.setString(8, auditLog.result.take(RESULT_MAX_SIZE))
        }
        checkRows(1, updatedRows) { "inserting $auditLog" }
    }

    fun findByUserId(userId: String): List<AuditLog> =
        jdbc.query(
            "$SELECT_BY_USER_ID order by timestamp desc",
            { ps -> ps.setString(1, userId) }
        ) { rs: ResultSet, _: Int ->
            AuditLog(
                rs.getLong(1),
                rs.getAny(2),
                rs.getLong(3),
                rs.getString(4),
                rs.getString(5),
                rs.getBoolean(6),
                rs.getBoolean(7),
                rs.getString(8),
                rs.getString(9)
            )
        }

    private companion object {
        private val INSERT: String
        private val SELECT_BY_USER_ID: String

        init {
            val table = "audit_log"
            val columnsExceptId = "timestamp, elapsed_time, user_id, use_case, read_only, success, arguments, result"
            val columnsAll = "id, $columnsExceptId"

            INSERT = "insert into $table ($columnsExceptId) values (?, ?, ?, ?, ?, ?, ?, ?)"
            SELECT_BY_USER_ID = "select $columnsAll from $table where user_id=?"
        }

        private const val USER_ID_MAX_SIZE = 50
        private const val USECASE_MAX_SIZE = 256
        private const val ARGUMENTS_MAX_SIZE = 16 * 1024
        private const val RESULT_MAX_SIZE = 16 * 1024
    }

}
