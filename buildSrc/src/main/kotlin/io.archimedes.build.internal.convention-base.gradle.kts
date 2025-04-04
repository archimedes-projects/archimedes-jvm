plugins {
    id("org.jetbrains.kotlin.jvm")
    id("org.jetbrains.kotlin.plugin.allopen")
    id("com.google.devtools.ksp")
    id("org.jetbrains.dokka")
}

val jvmVersion: String by project
val kotlinVersion: String by project

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:${kotlinVersion}")
    implementation("io.micronaut.kotlin:micronaut-kotlin-extension-functions")

    runtimeOnly("ch.qos.logback:logback-classic")

    testImplementation("io.micronaut.test:micronaut-test-junit5")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.junit.jupiter:junit-jupiter-params")
    testImplementation("org.assertj:assertj-core")
    testImplementation("org.mockito:mockito-core")
    testImplementation("org.mockito.kotlin:mockito-kotlin:5.4.0")
}

java {
    sourceCompatibility = JavaVersion.toVersion(jvmVersion)
    targetCompatibility = JavaVersion.toVersion(jvmVersion)
    toolchain {
        languageVersion = JavaLanguageVersion.of(jvmVersion)
    }
    withSourcesJar()
    withJavadocJar()
}

tasks.named<Jar>("javadocJar") {
    from(tasks.named("dokkaJavadoc"))
}

tasks.dokkaJavadoc.configure {
    dokkaSourceSets {
        named("main") {
            moduleName.set(project.name)
            includeNonPublic.set(false)
            skipEmptyPackages.set(true)
            reportUndocumented.set(false)
        }
    }
}
