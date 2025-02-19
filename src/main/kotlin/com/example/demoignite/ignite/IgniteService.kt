package com.example.demoignite.ignite

import arrow.core.Either
import arrow.core.flatMap
import com.example.demoignite.properties.IgniteProperties
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.future.await
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.apache.ignite.client.ClientCache
import org.apache.ignite.client.IgniteClient
import java.util.concurrent.ConcurrentHashMap

private val log = KotlinLogging.logger {}

private typealias ThinClientCache = ClientCache<String, String>
private typealias MaybeThinClientCache = Either<Throwable, ThinClientCache>

class IgniteService(
    private val properties: IgniteProperties,
    private val igniteClient: IgniteClient,
) {
    private val clientCacheMap: ConcurrentHashMap<String, MaybeThinClientCache> = ConcurrentHashMap()
    private val mutex = Mutex()

    private suspend fun clientCache(cacheName: String): MaybeThinClientCache =
        clientCacheMap[cacheName] ?: mutex.withLock {
            clientCacheMap[cacheName] ?: Either.catch<ThinClientCache> {
                igniteClient.getOrCreateCacheAsync<String, String>(cacheName).await()
            }.onRight { clientCacheMap[cacheName] = Either.Right(it) }
        }

    suspend fun getAsync(key: String, cacheName: String = properties.cacheName): String? {
        return clientCache(cacheName).flatMap {
            Either.catch { it.getAsync(key).await() }
        }.fold({ th ->
            log.error(th) {}
            null
        }, { value ->
            log.info { "got by key '$key' value '$value'" }
            value
        })
    }

    suspend fun putAsync(key: String, value: String, cacheName: String = properties.cacheName) {
        clientCache(cacheName).flatMap {
            Either.catch { it.putAsync(key, value).await() }
        }.fold({ th ->
            log.error(th) {}
        }, {
            log.info { "put by key '$key' value '$value'" }
        })
    }

    suspend fun removeAsync(key: String, cacheName: String = properties.cacheName) {
        clientCache(cacheName).flatMap {
            Either.catch { it.removeAsync(key).await() }
        }.fold({ th ->
            log.error(th) {}
        }, {
            log.info { "remove by key '$key'" }
        })
    }
}
