package com.example.demoignite.base

import com.example.demoignite.ignite.IgniteService
import com.example.demoignite.util.TestcontainersHelper
import org.apache.ignite.client.IgniteClient
import org.springframework.beans.factory.annotation.Autowired

abstract class AbstractIgniteTest : AbstractTest() {
    @Autowired
    protected lateinit var igniteClient: IgniteClient

    @Autowired
    protected lateinit var igniteService: IgniteService

    companion object {
        @JvmStatic
        protected val igniteContainer = TestcontainersHelper.IGNITE_CONTAINER
    }
}
