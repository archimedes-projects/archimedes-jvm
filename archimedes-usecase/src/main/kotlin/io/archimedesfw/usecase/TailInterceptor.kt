package io.archimedesfw.usecase

internal class TailInterceptor : Interceptor {

    override fun <R> intercept(useCase: UseCase<R>): R = useCase.execute()

}
