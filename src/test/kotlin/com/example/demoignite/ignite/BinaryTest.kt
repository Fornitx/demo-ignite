package com.example.demoignite.ignite

import com.example.demoignite.base.AbstractIgniteTest
import org.apache.ignite.client.IgniteClient
import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import kotlin.io.encoding.Base64
import kotlin.random.Random
import kotlin.test.assertContentEquals
import kotlin.test.assertNull

@SpringBootTest
class BinaryTest : AbstractIgniteTest() {
    @Autowired
    private lateinit var client: IgniteClient

    @Test
    fun test() {
        val cache = client.getOrCreateCache<ByteArray, ByteArray>("binaryCacheName")

        val key = Random.nextBytes(16)
        val value = Random.nextBytes(128)
        log.info { "key = ${key.toHexString()}" }
        log.info { "key = ${Base64.encode(key)}" }
        log.info { "value = ${value.toHexString()}" }
        log.info { "value = ${Base64.encode(value)}" }

        cache.put(key, value)

        assertArrayEquals(value.copyOf(), cache.get(key.copyOf()))
        assertContentEquals(value.copyOf(), cache.get(key.copyOf()))

        cache.remove(key)

        assertNull(cache.get(key))
    }
}
