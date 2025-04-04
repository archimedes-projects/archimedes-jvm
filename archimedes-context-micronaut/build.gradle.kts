plugins {
    id("io.archimedes.build.internal.convention-library")
}

group = "io.archimedesfw.context"
description = "Archimedes beans context."

dependencies {
    implementation(mn.micronaut.runtime)

    testImplementation(project(":archimedes-data-tx"))
}
