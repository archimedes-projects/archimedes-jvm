plugins {
    id("io.archimedes.build.internal.convention-library")
}

group = "io.archimedesfw.commons"
description = "Simple common slf4j utils."

dependencies {
    api(mn.slf4j.api)
}
