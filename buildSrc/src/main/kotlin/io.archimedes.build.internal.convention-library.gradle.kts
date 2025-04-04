plugins {
    id("io.archimedes.build.internal.convention-base")
    id("io.micronaut.library")
    `maven-publish`
    signing
}

graalvmNative.toolchainDetection = false

micronaut {
    runtime("netty")
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("io.archimedesfw.*")
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])

            pom {
                name = project.name
                description = project.provider { requireNotNull(project.description) }
                url = "https://github.com/archimedes-projects/archimedes-jvm"

                licenses {
                    license {
                        name = "The Apache Software License, Version 2.0"
                        url = "https://www.apache.org/licenses/LICENSE-2.0.txt"
                    }
                }
                developers {
                    developer {
                        id = "alejandropg"
                        name = "Alejandro Pérez"
                    }
                }
                scm {
                    connection = "scm:git:https://github.com/archimedes-projects/archimedes-jvm.git"
                    developerConnection = "scm:git:https://github.com/archimedes-projects/archimedes-jvm.git"
                    tag = "main"
                    url = "https://github.com/archimedes-projects/archimedes-jvm"
                }
            }
        }
    }
    repositories {
        mavenLocal()
    }
}

signing {
    val signingKey: String? by project
    val signingPassword: String? by project
    useInMemoryPgpKeys(signingKey, signingPassword)
    sign(publishing.publications["mavenJava"])
}
