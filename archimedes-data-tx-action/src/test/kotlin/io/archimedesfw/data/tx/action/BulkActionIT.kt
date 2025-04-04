package io.archimedesfw.data.tx.action

import io.micronaut.data.jdbc.runtime.JdbcOperations
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import javax.sql.DataSource

// We want to test opening several transactions in the same test,
// so we use `transactional = false` to Micronaut doesn't isolate each test into a single transaction
@MicronautTest(transactional = false)
@TestInstance(PER_CLASS)
internal open class BulkActionIT {

    @Inject
    lateinit var jdbc: JdbcOperations

    @Inject
    lateinit var dataSource: DataSource

    @Inject
    lateinit var tx: io.archimedesfw.data.tx.Transactional

    lateinit var bulkActionFactory: BulkActionFactory

    @BeforeAll
    internal fun beforeAll() {
        bulkActionFactory = DefaultBulkActionFactory(tx)
    }

    @Test
    internal fun `execute each bulk action in a different transaction`() {
        val bulkAction = bulkActionFactory.ofWritableTx<Int>()

        bulkAction.forEach((1..2).toList()) {
            val con = dataSource.connection
            val stmt = con.createStatement()
            stmt.executeUpdate("insert into $TABLE ($FIELD) values ($it)")
            stmt.close()
            con.close()

            check(it == 1) { "$it is not the number 1" }
        }

        val actual = findRecords().single()
        assertEquals(1, actual)
    }

    internal open fun findRecords(): List<Int> {
        val results = mutableListOf<Int>()
        tx.newReadOnly {
            jdbc.prepareStatement("select $FIELD from $TABLE") { pstmt ->
                val rs = pstmt.executeQuery()
                while (rs.next()) {
                    results.add(rs.getInt(1))
                }
            }
        }
        return results
    }

    private companion object {
        private const val TABLE = "bulk_action"
        private const val FIELD = "field"
    }

}
