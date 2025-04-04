plugins {
    id("io.archimedes.build.internal.convention-library")
}

group = "io.archimedesfw.security"
description = "Archimedes security auth. Micronaut implementation."

dependencies {
    implementation(mn.micronaut.http)
    implementation(mn.micronaut.json.core)
    implementation(mn.micronaut.reactor)
    implementation(mn.micronaut.security.jwt)
    implementation(mn.micronaut.security.oauth2)
    implementation(mn.micronaut.data.jdbc)

    implementation(libs.spring.security.crypto)
    runtimeOnly(mn.slf4j.jcl.over.slf4j) // Needed because spring-security-crypto uses commons logging internally

    testImplementation(mn.micronaut.serde.jackson)
    testImplementation(mn.micronaut.http.server.netty)
    testImplementation(mn.micronaut.http.client)
    testImplementation(mn.micronaut.flyway)
    testImplementation(mn.flyway.postgresql)
    testImplementation(mn.micronaut.jdbc.hikari)
    testImplementation(mn.postgresql)

    testImplementation(mn.testcontainers.postgres)
}
