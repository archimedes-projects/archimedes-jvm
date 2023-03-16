package io.archimedesfw.security.auth.test

import io.micronaut.core.annotation.NonBlocking
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import java.security.Principal

@Controller("/")
internal class HelloController {

    @NonBlocking
    @Get("hello")
    fun index(principal: Principal): Map<String, String> {
        return mapOf("principal" to principal.name)
    }

}
