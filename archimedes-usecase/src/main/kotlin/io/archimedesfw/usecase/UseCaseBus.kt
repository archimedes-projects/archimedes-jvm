package io.archimedesfw.usecase

interface UseCaseBus {

    operator fun <R> invoke(useCase: UseCase<R>): R

}
