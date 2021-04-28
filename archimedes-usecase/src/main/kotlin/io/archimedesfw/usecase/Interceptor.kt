package io.archimedesfw.usecase

internal interface Interceptor {

    fun <R> intercept(useCase: UseCase<R>): R

}
