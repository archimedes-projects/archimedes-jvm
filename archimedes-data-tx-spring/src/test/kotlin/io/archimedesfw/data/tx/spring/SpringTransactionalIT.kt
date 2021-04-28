package io.archimedesfw.data.tx.spring

import io.archimedesfw.data.tx.Transactional
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import org.junit.jupiter.api.assertThrows
import org.springframework.jdbc.UncategorizedSQLException
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.transaction.UnexpectedRollbackException
import java.sql.ResultSet
import javax.inject.Inject
import javax.sql.DataSource
import kotlin.random.Random

@MicronautTest(transactional = false)
@TestInstance(PER_CLASS)
internal open class SpringTransactionalIT {

    @Inject
    lateinit var tx: Transactional

    @Inject
    lateinit var dataSource: DataSource

    lateinit var jdbcTemplate: JdbcTemplate

    @BeforeAll
    internal fun beforeAll() {
        jdbcTemplate = JdbcTemplate(dataSource)
    }

    @Test
    internal fun writable_failing_outer() {
        val id = Random.nextInt()

        assertThrows<IllegalStateException> {
            tx.writable {
                insert(id, OUTER)

                tx.writable {
                    insert(id, INNER)
                }

                check(false)
            }
        }

        Assertions.assertTrue(find(id).isEmpty())
    }

    @Test
    internal fun writable_failing_inner() {
        val id = Random.nextInt()

        assertThrows<UnexpectedRollbackException> {
            tx.writable {
                insert(id, OUTER)

                assertThrows<IllegalStateException> {
                    tx.writable {
                        insert(id, INNER)
                        check(false)
                    }
                }
            }
        }

        Assertions.assertTrue(find(id).isEmpty())
    }

    @Test
    internal fun new_writable_failing_outer() {
        val id = Random.nextInt()

        assertThrows<IllegalStateException> {
            tx.writable {
                insert(id, OUTER)

                tx.newWritable {
                    insert(id, INNER)
                }

                check(false)
            }
        }

        assertEquals(TableDto(id, INNER), find(id).single())
    }

    @Test
    internal fun new_writable_failing_inner() {
        val id = Random.nextInt()

        tx.writable {
            insert(id, OUTER)

            assertThrows<IllegalStateException> {
                tx.newWritable {
                    insert(id, INNER)
                    check(false)
                }
            }
        }

        assertEquals(TableDto(id, OUTER), find(id).single())
    }

    @Test
    internal fun readonly() {
        val ex = assertThrows<UncategorizedSQLException> {
            tx.readOnly {
                insert(1, 1)
            }
        }

        assertTrue(ex.message!!.contains("ERROR: cannot execute INSERT in a read-only transaction"))
    }

    @Test
    internal fun newReadonly() {
        val ex = assertThrows<UncategorizedSQLException> {
            tx.newReadOnly {
                insert(1, 1)
            }
        }

        assertTrue(ex.message!!.contains("ERROR: cannot execute INSERT in a read-only transaction"))
    }

    private fun insert(id: Int, field: Int) {
        jdbcTemplate.update("insert into $TABLE ($ID, $FIELD) values ($id, $field)")
    }

    private fun find(id: Int): List<TableDto> {
        return tx.newReadOnly {
            jdbcTemplate.query("select $ID, $FIELD from $TABLE where $ID = $id", BulkActionRowMapper)
        }
    }

    private companion object {
        private const val TABLE = "spring_transactional"
        private const val ID = "id"
        private const val FIELD = "field"
        private const val OUTER = 1
        private const val INNER = 2
    }

    private data class TableDto(val id: Int, val field: Int)

    private object BulkActionRowMapper : RowMapper<TableDto> {
        override fun mapRow(rs: ResultSet, rowNum: Int): TableDto = TableDto(rs.getInt(1), rs.getInt(2))
    }

}
