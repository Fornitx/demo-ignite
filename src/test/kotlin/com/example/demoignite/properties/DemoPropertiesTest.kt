package com.example.demoignite.properties

import com.example.demoignite.base.AbstractContextTest
import org.junit.jupiter.api.Test
import kotlin.test.assertNull

class DemoPropertiesTest : AbstractContextTest() {
    @Test
    fun test() = contextRunner().runLogging { context ->
        assertNull(context.startupFailure)
    }
}