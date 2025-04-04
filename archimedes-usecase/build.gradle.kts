plugins {
    id("io.archimedes.build.internal.convention-library")
    id("io.micronaut.test-resources")
}

group = "io.archimedesfw.usecase"
description = "Archimedes simple use case and CQRS."

dependencies {
    implementation(project(":archimedes-commons-lang"))
    implementation(project(":archimedes-commons-logging-slf4j"))
    implementation(project(":archimedes-data-tx"))
    implementation(project(":archimedes-data-jdbc"))
    implementation(project(":archimedes-security"))

    implementation(mn.kotlin.reflect) // Just needed by Kassava

    testImplementation(project(":archimedes-context-micronaut"))
    testImplementation(project(":archimedes-data-jdbc-micronaut"))

    testImplementation(mn.micronaut.http.server.netty)
    testImplementation(mn.micronaut.flyway)
    testImplementation(mn.micronaut.jdbc.hikari)
    testImplementation(mn.flyway.postgresql)
    testImplementation(mn.postgresql)

    testImplementation(mn.testcontainers.postgres)

}
