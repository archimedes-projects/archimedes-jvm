plugins {
    id("io.archimedes.build.internal.convention-library")
}

group = "io.archimedesfw.data"
description = "Archimedes JDBC. Micronaut implementation."

dependencies {
    implementation(project(":archimedes-data-jdbc"))
    implementation(project(":archimedes-data-tx"))

    runtimeOnly(project(":archimedes-data-tx-micronaut"))

    implementation(mn.micronaut.data.jdbc)
}
