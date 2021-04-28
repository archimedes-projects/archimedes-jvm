package io.archimedesfw.data.jdbc.spring

import io.archimedesfw.data.Criterion
import io.archimedesfw.data.EntityInt.Companion.NEW
import io.archimedesfw.data.Version
import io.archimedesfw.data.jdbc.spring.JdbcFooVersionedRepository.ById
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestMethodOrder
import org.junit.jupiter.api.assertThrows
import java.time.LocalDateTime
import javax.inject.Inject
import javax.sql.DataSource

@MicronautTest(rollback = false)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
internal class JdbcRepositoryVersionedIT {

    @Inject
    private lateinit var repository: JdbcFooVersionedRepository

    private lateinit var firstVersion: FooVersionedEntity
    private lateinit var newVersion: FooVersionedEntity
    private lateinit var insertedVersion: FooVersionedEntity
    private lateinit var byId: Criterion<FooVersionedEntity>

    @Inject
    private lateinit var dataSources: List<DataSource>


    @Test
    @Order(10)
    internal fun fail_if_not_current() {
        val list = repository.findVersion(TS, ById(-1))

        assertTrue(list.isEmpty())
    }

    @Test
    @Order(20)
    internal fun save_first_version() {
        firstVersion = FooVersionedEntity(TS, 1)

        val entityId = repository.save(firstVersion)
        assertNotEquals(NEW, entityId)

        firstVersion = FooVersionedEntity(entityId, firstVersion.version, firstVersion.data)
        byId = ById(entityId)
    }

    @Test
    @Order(30)
    internal fun save_new_version() {
        newVersion = firstVersion.copy(version = Version(TS_NEW), data = 3)

        repository.save(newVersion)

        firstVersion = firstVersion.copy(version = Version(TS, TS_NEW))
    }

    @Test
    @Order(40)
    internal fun update_new_version() {
        val loadedNewVersion = repository.findVersion(TS_NEW.plusSeconds(1), byId).single()

        assertEquals(newVersion, loadedNewVersion)

        newVersion = loadedNewVersion.copy(data = 30)
        repository.save(newVersion)
    }

    @Test
    @Order(50)
    internal fun update_first_version() {
        val loadedFirstVersion = repository.findVersion(TS.plusSeconds(1), byId).single()

        assertEquals(firstVersion, loadedFirstVersion)

        firstVersion = loadedFirstVersion.copy(data = 10)
        repository.save(firstVersion)
    }

    @Test
    @Order(60)
    internal fun insert_version_in_the_middle() {
        insertedVersion = firstVersion.copy(version = Version(TS_INSERTED), data = 2)

        repository.save(insertedVersion)

        firstVersion = firstVersion.copy(version = Version(TS, TS_INSERTED))
        insertedVersion = insertedVersion.copy(version = Version(TS_INSERTED, TS_NEW))
    }

    @Test
    @Order(70)
    internal fun update_inserted_version() {
        val loadedInsertedVersion = repository.findVersion(TS_INSERTED.plusSeconds(1), byId).single()

        assertEquals(insertedVersion, loadedInsertedVersion)

        insertedVersion = loadedInsertedVersion.copy(data = 20)
        repository.save(insertedVersion)
    }

    @Test
    @Order(75)
    internal fun update_without_load_from_database() {
        val insertedNotLoadedFromDatabase = insertedVersion.copy(Version(insertedVersion.version.start), data = 30)

        repository.save(insertedNotLoadedFromDatabase)

        insertedVersion = insertedVersion.copy(data = 30)
    }

    @Test
    @Order(80)
    internal fun find_version() {
        val loadedFirstVersion = repository.findVersion(TS.plusSeconds(1), byId).single()
        val loadedInsertedVersion = repository.findVersion(TS_INSERTED.plusSeconds(1), byId).single()
        val loadedNewVersion = repository.findVersion(TS_NEW.plusSeconds(1), byId).single()

        assertEquals(firstVersion, loadedFirstVersion)
        assertEquals(insertedVersion, loadedInsertedVersion)
        assertEquals(newVersion, loadedNewVersion)
    }

    @Test
    @Order(90)
    internal fun fail_if_insert_informing_expiration_date() {
        assertThrows<IllegalArgumentException>() {
            val entity = newVersion.copy(version = Version(TS_NEW.plusSeconds(10), TS_NEW.plusSeconds(20)))
            repository.save(entity)
        }
    }

    @Test
    @Order(100)
    internal fun fail_if_update_changing_expiration_date() {
        assertThrows<IllegalArgumentException>() {
            val entity = newVersion.copy(version = Version(TS_NEW, TS_NEW.plusSeconds(9)))
            repository.save(entity)
        }
    }

    //    @Test
//    @Order(110)
    internal fun find_interval() {

    }


    private companion object {
        private val TS = LocalDateTime.now().withYear(1900)
        private val TS_NEW = TS.plusSeconds(4)
        private val TS_INSERTED = TS.plusSeconds(2)
    }

}
