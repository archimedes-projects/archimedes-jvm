package io.archimedesfw.data.jdbc.micronaut

import io.archimedesfw.data.sql.criteria.CriteriaBuilder
import io.archimedesfw.data.sql.criteria.builder.SqlBuilder
import io.micronaut.context.annotation.Factory
import jakarta.inject.Singleton

@Factory
internal class SqlConfig {

    @Singleton
    internal fun sqlBuilder() = SqlBuilder()

    @Singleton
    internal fun criteriaBuilder() = CriteriaBuilder()

}
