plugins {
    id("io.archimedes.build.internal.convention-library")
}

group = "io.archimedesfw.data"
description = "Execute a different transactional action over each element of a collection."

dependencies {
    api(project(":archimedes-data-tx"))
    implementation(project(":archimedes-context-micronaut"))

    testImplementation(project(":archimedes-data-tx-micronaut"))
    testImplementation("io.micronaut.data:micronaut-data-jdbc")
    testImplementation("io.micronaut.serde:micronaut-serde-jackson")
    testImplementation("io.micronaut.flyway:micronaut-flyway")
    testImplementation("org.flywaydb:flyway-database-postgresql")
    testImplementation("io.micronaut.sql:micronaut-jdbc-hikari")
    testImplementation("org.postgresql:postgresql")

    testImplementation("org.testcontainers:postgresql")
}
