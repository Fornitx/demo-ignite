package com.example.demoignite.ignite

import com.example.demoignite.base.AbstractIgniteTest
import org.apache.ignite.client.ClientCache
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class GenericCacheTest : AbstractIgniteTest() {
    @Test
    fun test() {
        val cache = igniteClient.getOrCreateCache<Any, Any>("genericCache")

        cache.put("key1", "value1")
        cache.put("key2".encodeToByteArray(), "value2".encodeToByteArray())

        cache.logGet("key1")
        cache.logGet("key1".encodeToByteArray())
        cache.logGet("key2")
        cache.logGet("key2".encodeToByteArray())

//        cache.clear()
    }

    private fun ClientCache<Any, Any>.logGet(key: Any) {
        val value = this.get(key)
        println(if (value == null) null else value::class)
        println(value)
    }
}
