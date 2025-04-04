plugins {
    id("io.archimedes.build.internal.convention-library")
}

group = "io.archimedesfw.data"
description = "Archimedes JDBC."

dependencies {
    implementation(project(":archimedes-commons-lang"))
}
