package io.archimedesfw.usecase

import io.archimedesfw.usecase.thirdparty.kassava.SupportsMixedTypeEquality
import io.archimedesfw.usecase.thirdparty.kassava.kotlinEquals
import io.archimedesfw.usecase.thirdparty.kassava.kotlinHashCode
import io.archimedesfw.usecase.thirdparty.kassava.kotlinToString
import kotlin.reflect.KProperty1

sealed class UseCase<R> : SupportsMixedTypeEquality {

    internal val name: String = this.javaClass.name
    internal abstract val isReadOnly: Boolean

    /**
     * To be implemented by each specific use case.
     * It is not visible by anyone except the use case itself. No one can call it.
     */
    protected abstract fun run(): R

    /** Just to be called by the Executor inside this module */
    internal inline fun execute(): R = run()

    /**
     * A bridge to call another use case inside a use case.
     * This is needed just to unit testing, so you can spy the call to the other use case and dont run it really.
     */
    internal fun <R2> runInternal(otherUseCase: UseCase<R2>): R2 = otherUseCase.run()

    //
    // Generic implementation of equals, hashCode and toString using Kassava
    //
    private val properties: Array<KProperty1<UseCase<R>, Any?>> by lazy(LazyThreadSafetyMode.NONE) {
        @Suppress("UNCHECKED_CAST")
        PropertiesCache.propertiesOf(this) as Array<KProperty1<UseCase<R>, Any?>>
    }

    override fun canEqual(other: Any?): Boolean = other != null && this::class == other::class
    override fun equals(other: Any?): Boolean = this.kotlinEquals(other, properties)
    override fun hashCode(): Int = this.kotlinHashCode(properties)
    override fun toString(): String = this.kotlinToString(properties)
}

abstract class Command<R> : UseCase<R>() {

    override val isReadOnly = false

    /**
     * To run another use case inside a command.
     * Look that is possible to run a any use case, so you can run any query or even command.
     * Uses the `runInternal` bridge to be able to spy this call in unit testing.
     */
    protected fun <R2> run(otherUseCase: UseCase<R2>): R2 = runInternal(otherUseCase)

}

abstract class Query<R> : UseCase<R>() {

    override val isReadOnly = true

    /**
     * To run another query inside a query.
     * Look that is not possible to run a command, just another query.
     * Uses the `runInternal` bridge to be able to spy this call in unit testing.
     */
    protected fun <R2> run(otherQuery: Query<R2>): R2 = runInternal(otherQuery)

}
