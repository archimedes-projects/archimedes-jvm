package io.archimedesfw.usecase.security

import io.archimedesfw.usecase.Interceptor
import io.archimedesfw.usecase.UseCase
import io.micronaut.security.utils.SecurityService

internal class SecureInterceptor(
    private val securityService: SecurityService,
    private val next: Interceptor
) : Interceptor {

    // For now just do nothing,
    // but here you can use SecurityRegistry to check permissions, impersonate with runAs...
    override fun <R> intercept(useCase: UseCase<R>): R = next.intercept(useCase)

}
