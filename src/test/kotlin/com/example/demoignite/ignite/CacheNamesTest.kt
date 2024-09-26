package com.example.demoignite.ignite

import com.example.demoignite.base.AbstractIgniteTest
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class CacheNamesTest : AbstractIgniteTest() {
    @Test
    fun test() {
        println(igniteClient.cacheNames())
    }

    @Test
    fun test2() {
        println(igniteClient.cacheNames())
    }
}
