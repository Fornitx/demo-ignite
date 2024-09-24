package com.example.demoignite.ignite

import com.example.demoignite.properties.IgniteProperties
import org.apache.ignite.IgniteClientSpringBean
import org.springframework.boot.actuate.health.Health
import org.springframework.boot.actuate.health.ReactiveHealthIndicator
import reactor.core.publisher.Mono

class IgniteHealthIndicator(
    properties: IgniteProperties,
    private val igniteClient: IgniteClientSpringBean,
) : ReactiveHealthIndicator {
    private val cacheName = properties.cacheName

    private val healthUnknown = Health.unknown().withCacheName(cacheName).build()
    private val healthUp = Health.up().withCacheName(cacheName).build()
    private val healthDown = Health.down().withCacheName(cacheName).build()

    override fun health(): Mono<Health> {
        return if (igniteClient.isRunning) {
            Mono.fromCompletionStage {
                igniteClient.getOrCreateCacheAsync<String, String>(cacheName)
            }.map { healthUp }.onErrorReturn(healthDown)
        } else {
            Mono.just(healthUnknown)
        }
    }

    private fun Health.Builder.withCacheName(name: String): Health.Builder = this.withDetail("cacheName", name)
}
