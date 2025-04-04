plugins {
    id("io.archimedes.build.internal.convention-library")
}

group = "io.archimedesfw.data"
description = "Archimedes database transaction. Micronaut implementation."

dependencies {
    api(project(":archimedes-data-tx"))

    implementation(mn.micronaut.data.jdbc)
    implementation(mn.micronaut.data.tx)

    testImplementation(mn.micronaut.flyway)
    testImplementation(mn.flyway.postgresql)
    testImplementation(mn.micronaut.jdbc.hikari)
    testImplementation(mn.postgresql)

    testImplementation("org.testcontainers:postgresql")
}
