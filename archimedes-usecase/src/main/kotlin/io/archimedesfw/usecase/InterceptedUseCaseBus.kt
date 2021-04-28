package io.archimedesfw.usecase

internal class InterceptedUseCaseBus(
    private val chain: Interceptor
) : UseCaseBus {

    override fun <R> invoke(useCase: UseCase<R>): R = chain.intercept(useCase)

}
