package io.archimedesfw.data.tx.micronaut

import io.archimedesfw.data.tx.Transactional
import io.micronaut.core.annotation.Introspected
import io.micronaut.data.exceptions.DataAccessException
import io.micronaut.data.jdbc.runtime.JdbcOperations
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import io.micronaut.transaction.exceptions.UnexpectedRollbackException
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.fail
import kotlin.random.Random

@MicronautTest(startApplication = false, transactional = false)
@TestInstance(PER_CLASS)
internal class MicronautTransactionalIT {

    @Inject
    lateinit var tx: Transactional

    @Inject
    lateinit var jdbc: JdbcOperations

    @Test
    fun `writable failing outer`() {
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

        assertTrue(find(id).isEmpty())
    }

    @Test
    fun `writable failing inner`() {
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

        assertTrue(find(id).isEmpty())
    }

    @Test
    fun `new writable failing outer`() {
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
    fun `new writable failing inner`() {
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
    fun readonly() {
        val ex = assertThrows<DataAccessException> {
            tx.readOnly {
                insert(1, 1)
            }
        }

        assertEquals(
            "Error preparing SQL statement: ERROR: cannot execute INSERT in a read-only transaction",
            ex.message
        )
    }

    @Test
    fun `new readonly`() {
        val ex = assertThrows<DataAccessException> {
            tx.newReadOnly {
                insert(1, 1)
            }
        }

        assertEquals(
            "Error preparing SQL statement: ERROR: cannot execute INSERT in a read-only transaction",
            ex.message
        )
    }

    private fun insert(id: Int, field: Int) {
        jdbc.prepareStatement("insert into $TABLE ($ID, $FIELD) values ($id, $field)") { it.executeUpdate() }
        jdbc.prepareStatement("") { it.addBatch() }
    }

    private fun find(id: Int): List<TableDto> = tx.newReadOnly {
        jdbc.prepareStatement("select $ID, $FIELD from $TABLE where $ID = $id") {
            val rs = it.executeQuery()
            jdbc.entityStream(rs, TableDto::class.java).toList()
        }
    }

    private companion object {
        private const val TABLE = "micronaut_transactional"
        private const val ID = "id"
        private const val FIELD = "field"
        private const val OUTER = 1
        private const val INNER = 2
    }

}
