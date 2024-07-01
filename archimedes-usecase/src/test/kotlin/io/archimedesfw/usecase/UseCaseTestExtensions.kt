package io.archimedesfw.usecase

import org.mockito.Mockito.mockingDetails
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.doThrow
import org.mockito.kotlin.spy
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.mockito.verification.VerificationMode

fun <T : UseCase<R>, R> T.fakeRun(): R = this.execute()

private fun <T : UseCase<*>> T.spyOf(): T = if (mockingDetails(this).isSpy) this else spy(this)

fun <T : UseCase<*>> T.asSpiedRun(toBeReturned: Any = Unit): T {
    val spied = this.spyOf()
    doReturn(toBeReturned).whenever(spied).runInternal(any<UseCase<*>>())
    return spied
}

fun <T : UseCase<*>> T.asSpiedRun(dependency: UseCase<Unit>): T = this.asSpiedRun(dependency, Unit)

fun <T : UseCase<*>, R> T.asSpiedRun(vararg toBeReturned: Pair<UseCase<R>, R>): T =
    toBeReturned.fold(this) { acc, pair -> acc.asSpiedRun(pair.first, pair.second) }

fun <T : UseCase<*>, R> T.asSpiedRun(dependency: UseCase<R>, toBeReturned: R): T {
    val spied = this.spyOf()
    doReturn(toBeReturned).whenever(spied).runInternal(dependency)
    return spied
}

fun <T : UseCase<*>, R> T.asSpiedRunThrow(dependency: UseCase<R>, toBeThrown: Throwable): T {
    val spied = this.spyOf()
    doThrow(toBeThrown).whenever(spied).runInternal(dependency)
    return spied
}

fun <T : UseCase<*>, R> T.verifySpiedRun(toBeVerified: UseCase<R>): T {
    check(mockingDetails(this).isSpy) {
        "This looks like a real object and not a spied UseCase." +
                " Before verify you have to prepare the spy with the 'asSpiedRun()' method "
    }
    verify(this).runInternal(toBeVerified)
    return this
}

fun <T : UseCase<*>, R> T.verifySpiedRun(toBeVerified: UseCase<R>, mode: VerificationMode): T {
    check(mockingDetails(this).isSpy) {
        "This looks like a real object and not a spied UseCase." +
                " Before verify you have to prepare the spy with the 'asSpiedRun()' method "
    }
    verify(this, mode).runInternal(toBeVerified)
    return this
}
