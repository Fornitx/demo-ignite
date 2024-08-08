package com.example.demoignite

import com.example.demoignite.base.AbstractIgniteTest
import org.apache.ignite.client.IgniteClient
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class IgniteTest : AbstractIgniteTest() {
    @Autowired
    private lateinit var igniteClient: IgniteClient

    @Test
    fun test() {
        println(igniteClient.cacheNames())
    }
}