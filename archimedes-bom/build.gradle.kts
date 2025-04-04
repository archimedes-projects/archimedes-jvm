plugins {
    `java-platform`
    `maven-publish`
    signing
}

javaPlatform {
    allowDependencies()
}

group = "io.archimedesfw"
description = "Archimedes Maven BOM. Import in your project to manage all Archimedes dependencies."

dependencies {
    constraints {
        listOf(
            ":archimedes-commons-lang",
            ":archimedes-commons-logging-slf4j",
            ":archimedes-context-micronaut",
            ":archimedes-cqrs",
            ":archimedes-cqrs-micronaut",
            ":archimedes-data-jdbc",
            ":archimedes-data-jdbc-micronaut",
            ":archimedes-data-tx",
            ":archimedes-data-tx-action",
            ":archimedes-data-tx-action-micronaut",
            ":archimedes-security",
            ":archimedes-security-auth-micronaut",
            ":archimedes-usecase"
        ).forEach {
            api(project(it))
        }
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenBOM") {
            from(components["javaPlatform"])

            versionMapping {
                allVariants {
                    fromResolutionResult()
                }
            }

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
    sign(publishing.publications["mavenBOM"])
}
