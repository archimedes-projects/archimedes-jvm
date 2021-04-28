package io.archimedesfw.security

import io.archimedesfw.security.test.FakeSecurityContext
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Test

internal class SecurityTest {

    private val fakeSecurityContext = FakeSecurityContext()

    @Test
    internal fun run_as_set_specific_security_context_and_recover_previous_one_when_finished() {
        assertSame(NullSecurityContext, Security.context)

        Security.runAs(fakeSecurityContext) {
            assertSame(fakeSecurityContext, Security.context)
        }

        assertSame(NullSecurityContext, Security.context)
    }

    @Test
    internal fun run_as_can_return_something() {
        val result = Security.runAs(fakeSecurityContext) {
            42
        }

        assertEquals(42, result)
    }

}
