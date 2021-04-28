package io.archimedesfw.context

import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.ExtensionContext

class ServiceLocatorJUnitExtension : BeforeAllCallback
//    , AfterAllCallback
{
    override fun beforeAll(context: ExtensionContext?) {
        ServiceLocator.reset()
    }

//    override fun afterAll(context: ExtensionContext?) {
//        ServiceLocator.reset()
//    }

}
