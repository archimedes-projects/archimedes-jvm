plugins {
    id("io.archimedes.build.internal.convention-library")
}

group = "io.archimedesfw.cqrs"
description = "Archimedes CQRS. Micronaut implementation."

dependencies {
    api(project(":archimedes-cqrs"))
    implementation(project(":archimedes-data-tx-micronaut"))

    implementation(mn.micronaut.inject)
}
