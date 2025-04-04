plugins {
    `kotlin-dsl`
}

val jvmVersion: String by project
val kotlinVersion: String by project
val kotlinKspVersion: String by project
val micronautGradlePluginVersion: String by project

repositories {
    gradlePluginPortal()
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
    implementation("org.jetbrains.kotlin:kotlin-allopen:$kotlinVersion")
    implementation("com.google.devtools.ksp:symbol-processing-gradle-plugin:$kotlinKspVersion")

    implementation("org.jetbrains.dokka:dokka-gradle-plugin:2.0.0")

    implementation("io.micronaut.gradle:micronaut-gradle-plugin:$micronautGradlePluginVersion")
    implementation("com.github.johnrengelman:shadow:8.1.1")
}

java {
    sourceCompatibility = JavaVersion.toVersion(jvmVersion)
    targetCompatibility = JavaVersion.toVersion(jvmVersion)
    toolchain {
        languageVersion = JavaLanguageVersion.of(jvmVersion)
    }
}
