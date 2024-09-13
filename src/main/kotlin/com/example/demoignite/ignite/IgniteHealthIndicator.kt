package com.example.demoignite.ignite

import com.example.demoignite.properties.IgniteProperties
import org.apache.ignite.client.IgniteClient
import org.springframework.boot.actuate.health.Health
import org.springframework.boot.actuate.health.ReactiveHealthIndicator
import reactor.core.publisher.Mono

class IgniteHealthIndicator(
    properties: IgniteProperties,
    private val igniteClient: IgniteClient,
) : ReactiveHealthIndicator {
    private val cacheName = properties.cacheName

    private val healthUp = Health.up().withDetail("cacheName", cacheName).build()
    private val healthDown = Health.down().withDetail("cacheName", cacheName).build()

    override fun health(): Mono<Health> = Mono.fromCompletionStage {
        igniteClient.getOrCreateCacheAsync<String, String>(cacheName)
    }.map { healthUp }.onErrorReturn(healthDown)
}
