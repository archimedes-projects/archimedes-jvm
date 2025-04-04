pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}

plugins {
    id("io.micronaut.platform.catalog") version "4.5.1"
}

rootProject.name="archimedes-jvm"

include(
    "archimedes-bom",
    "archimedes-commons-lang",
    "archimedes-commons-logging-slf4j",
    "archimedes-context-micronaut",
    "archimedes-cqrs",
    "archimedes-cqrs-micronaut",
    "archimedes-data-jdbc",
    "archimedes-data-jdbc-micronaut",
    "archimedes-data-tx",
    "archimedes-data-tx-micronaut",
    "archimedes-data-tx-action",
    "archimedes-data-tx-action-micronaut",
    "archimedes-security",
    "archimedes-security-auth-micronaut",
    "archimedes-usecase"
)
