package com.example.demoignite.base

import com.example.demoignite.ignite.IgniteService
import org.apache.ignite.client.IgniteClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("local")
abstract class AbstractLocalIgniteTest : AbstractTest() {
    @Autowired
    protected lateinit var igniteClient: IgniteClient

    @Autowired
    protected lateinit var igniteService: IgniteService
}
