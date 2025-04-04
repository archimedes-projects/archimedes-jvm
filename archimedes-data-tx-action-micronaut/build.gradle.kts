plugins {
    id("io.archimedes.build.internal.convention-library")
}

group = "io.archimedesfw.data"
description = "Archimedes data-tx-action for Micronaut."

dependencies {
    api(project(":archimedes-data-tx-action"))
    api(project(":archimedes-data-tx-micronaut"))
}
