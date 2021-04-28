package io.archimedesfw.data.jdbc.spring

import io.micronaut.context.annotation.Factory
import org.springframework.jdbc.core.JdbcTemplate
import javax.inject.Singleton
import javax.sql.DataSource

@Factory
class SpringDataFactory {

    @Singleton
    fun jdbcTemplate(dataSource: DataSource): JdbcTemplate = JdbcTemplate(dataSource)

}
