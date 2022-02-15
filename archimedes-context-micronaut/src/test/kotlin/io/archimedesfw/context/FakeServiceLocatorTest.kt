package io.archimedesfw.context

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class FakeServiceLocatorTest {

    @Test
    internal fun return_mock_if_bean_is_not_registered() {
        FakeServiceLocator.of()

        val bean = ServiceLocator.locate<SomeParentBean>()

        val resultOfMockedFun = bean.foo()
        assertNull(resultOfMockedFun)
    }

    @Test
    internal fun always_return_the_same_mock() {
        FakeServiceLocator.of()

        val bean1 = ServiceLocator.locate<SomeParentBean>()
        val bean2 = ServiceLocator.locate<SomeParentBean>()

        assertSame(bean1, bean2)
    }

    @Test
    internal fun return_child_compatible_type_if_requested_type_is_not_registered() {
        val someBean = SomeBean()
        FakeServiceLocator.of(someBean)

        val parentBean = ServiceLocator.locate<SomeParentBean>()

        assertSame(someBean, parentBean)
        assertEquals("Real SomeBean method!", parentBean.foo())
    }

    @Test
    internal fun fail_if_there_are_several_beans_compatibles_with_requested_type() {
        FakeServiceLocator.of(
            SomeBean(),
            OtherBean()
        )

        val ex = assertThrows<IllegalArgumentException> {
            ServiceLocator.locate<SomeParentBean>()
        }

        assertEquals(
            "Cannot found a unique bean of type ${FakeServiceLocatorTest::class.qualifiedName}\$SomeParentBean." +
                    " Found 2 possible candidates.",
            ex.message
        )

    }

    private interface SomeParentBean {
        fun foo(): String
    }

    private class SomeBean : SomeParentBean {
        override fun foo() = "Real SomeBean method!"
    }

    private class OtherBean : SomeParentBean {
        override fun foo() = "Real OtherBean method!"
    }

}
