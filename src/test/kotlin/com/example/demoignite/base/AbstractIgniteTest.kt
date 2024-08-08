package com.example.demoignite.base

import com.example.demoignite.util.TestcontainersHelper

abstract class AbstractIgniteTest : AbstractTest() {
    companion object {
        @JvmStatic
        protected val igniteContainer = TestcontainersHelper.IGNITE_CONTAINER
    }
}