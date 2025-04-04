plugins {
    id("io.archimedes.build.internal.convention-library")
}

group = "io.archimedesfw.cqrs"
description = "Archimedes CQRS."

dependencies {
    implementation(project(":archimedes-commons-lang"))
    implementation(project(":archimedes-commons-logging-slf4j"))
    implementation(project(":archimedes-security"))
    implementation(project(":archimedes-data-tx"))
    implementation(project(":archimedes-data-jdbc"))

    testImplementation(project(":archimedes-data-tx"))
}
