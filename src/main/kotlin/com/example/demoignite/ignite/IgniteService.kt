package com.example.demoignite.ignite

import com.example.demoignite.properties.IgniteProperties
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.CoroutineStart.LAZY
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.future.await
import org.apache.ignite.client.ClientCache
import org.apache.ignite.client.IgniteClient

private val log = KotlinLogging.logger {}

class IgniteService(
    private val properties: IgniteProperties,
    private val igniteClient: IgniteClient,
) {
    private val clientCache: Deferred<ClientCache<String, String>> =
        GlobalScope.async(Dispatchers.Unconfined, start = LAZY) {
            igniteClient.getOrCreateCacheAsync<String, String>(properties.cacheName).await()
        }

    suspend fun getAsync(key: String): String? {
        return try {
            val value = clientCache.await().getAsync(key).await()
            log.info("got by key '{}' value '{}'", key, value)
            value
        } catch (ex: Exception) {
            log.error { ex.message }
            null
        }
    }

    suspend fun putAsync(key: String, value: String) {
        try {
            clientCache.await().putAsync(key, value).await()
            log.info("put by key '{}' value '{}'", key, value)
        } catch (ex: Exception) {
            log.error { ex.message }
        }
    }

    suspend fun removeAsync(key: String) {
        clientCache.await().removeAsync(key).await()
        log.info("remove by key '{}'", key)
    }
}
