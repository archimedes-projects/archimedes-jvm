package io.archimedesfw.cqrs.interceptor.audit.persistence.jdbc

import io.archimedesfw.commons.lang.getAny
import io.archimedesfw.cqrs.interceptor.audit.AuditLog
import io.archimedesfw.cqrs.interceptor.audit.persistence.AuditRepository
import io.archimedesfw.data.jdbc.JdbcTemplate
import io.archimedesfw.data.jdbc.checkRows
import java.sql.ResultSet

class JdbcAuditRepository(
    private val jdbc: JdbcTemplate,
    tableName: String = "audit_log",
    private val insert: String =
        "insert into $tableName (timestamp, elapsed_time, user_id, action, read_only, success, arguments, result) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?)",
    private val selectByUserId: String =
        "select id, timestamp, elapsed_time, user_id, action, read_only, success, arguments, result " +
                "from $tableName where user_id=? order by timestamp desc",
    private val actionNameMaxSize: Int = 256,
    private val argumentsMaxSize: Int = 16 * 1024,
    private val resultMaxSize: Int = 16 * 1024
) : AuditRepository {

    override fun save(auditLog: AuditLog) {
        val updatedRows = jdbc.update(insert) { ps ->
            ps.setObject(1, auditLog.timestamp)
            ps.setLong(2, auditLog.elapsedTime)
            ps.setString(3, auditLog.userId)
            ps.setString(4, auditLog.action.takeLast(actionNameMaxSize))
            ps.setBoolean(5, auditLog.readOnly)
            ps.setBoolean(6, auditLog.success)
            ps.setString(7, auditLog.arguments.take(argumentsMaxSize))
            ps.setString(8, auditLog.result.take(resultMaxSize))
        }
        checkRows(1, updatedRows) { "inserting $auditLog" }
    }

    fun findByUserId(userId: String): List<AuditLog> =
        jdbc.query(
            selectByUserId,
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
}
