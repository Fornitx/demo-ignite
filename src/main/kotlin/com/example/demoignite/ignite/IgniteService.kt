package com.example.demoignite.ignite

import com.example.demoignite.properties.IgniteProperties
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.future.await
import org.apache.ignite.client.ClientCache
import org.apache.ignite.client.IgniteClient
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener

private val log = KotlinLogging.logger { }

class IgniteService(
    private val properties: IgniteProperties,
    private val igniteClient: IgniteClient,
) {
    private var clientCache: ClientCache<String, String>? = null

    @EventListener(ApplicationReadyEvent::class)
    fun getOrCreateCache() {
        clientCache = igniteClient.getOrCreateCache<String, String>(properties.cacheName)
    }

    suspend fun getAsync(key: String): String? {
        val value = clientCache!!.getAsync(key).await()
        log.info("got by key '{}' value '{}'", key, value)
        return value
    }

    suspend fun putAsync(key: String, value: String) {
        clientCache!!.putAsync(key, value).await()
        log.info("put by key '{}' value '{}'", key, value)
    }

    suspend fun removeAsync(key: String) {
        clientCache!!.removeAsync(key).await()
        log.info("remove by key '{}'", key)
    }
}
