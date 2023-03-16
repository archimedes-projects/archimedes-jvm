package io.archimedesfw.security.auth.test

import io.micronaut.runtime.Micronaut.build

fun main(args: Array<String>) {
    build()
        .args(*args)
        .packages("io.archimedes.security.auth")
        .start()
}
