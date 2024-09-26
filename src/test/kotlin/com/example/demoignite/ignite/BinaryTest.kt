package com.example.demoignite.ignite

import com.example.demoignite.base.AbstractIgniteTest
import com.example.demoignite.utils.toBase64
import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import kotlin.random.Random
import kotlin.test.assertContentEquals
import kotlin.test.assertNull

@SpringBootTest
class BinaryTest : AbstractIgniteTest() {
    @Test
    fun test() {
        val cache = igniteClient.getOrCreateCache<ByteArray, ByteArray>("binaryCacheName")

        val key = Random.nextBytes(16)
        val value = Random.nextBytes(128)
        log.info { "key = ${key.toHexString()}" }
        log.info { "key = ${key.toBase64()}" }
        log.info { "value = ${value.toHexString()}" }
        log.info { "value = ${value.toBase64()}" }

        cache.put(key, value)

        assertArrayEquals(value.copyOf(), cache.get(key.copyOf()))
        assertContentEquals(value.copyOf(), cache.get(key.copyOf()))

        cache.remove(key)

        assertNull(cache.get(key))
    }
}
