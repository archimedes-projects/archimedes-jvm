plugins {
    id("io.archimedes.build.internal.convention-library")
}

group = "io.archimedesfw.security"
description = "Archimedes security."

dependencies {
    implementation("io.micronaut.security:micronaut-security")
}
