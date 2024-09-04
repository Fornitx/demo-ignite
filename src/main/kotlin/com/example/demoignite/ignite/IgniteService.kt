package com.example.demoignite.ignite

import com.example.demoignite.properties.IgniteProperties
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.future.await
import org.apache.ignite.client.ClientCache
import org.apache.ignite.client.IgniteClient

private val log = KotlinLogging.logger { }

class IgniteService(
    private val properties: IgniteProperties,
    private val igniteClient: IgniteClient,
) {
    private val clientCache: ClientCache<String, String> by lazy {
        igniteClient.getOrCreateCache(properties.cacheName)
    }

    fun get(key: String): String? {
        val value = clientCache.get(key)
        log.info("got by key '{}' value '{}'", key, value)
        return value
    }

    suspend fun getAsync(key: String): String? {
        val value = clientCache.getAsync(key).await()
        log.info("got by key '{}' value '{}'", key, value)
        return value
    }

    suspend fun putAsync(key: String, value: String) {
        clientCache.putAsync(key, value).await()
        log.info("put by key '{}' value '{}'", key, value)
    }

    suspend fun removeAsync(key: String) {
        clientCache.removeAsync(key).await()
        log.info("remove by key '{}'", key)
    }
}
