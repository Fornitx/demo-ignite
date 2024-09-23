package com.example.demoignite.ignite

import com.example.demoignite.base.AbstractIgniteTest
import org.apache.ignite.client.IgniteClient
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class CacheNamesTest : AbstractIgniteTest() {
    @Autowired
    private lateinit var igniteClient: IgniteClient

    @Test
    fun test() {
        println(igniteClient.cacheNames())
    }

    @Test
    fun test2() {
        println(igniteClient.cacheNames())
    }
}