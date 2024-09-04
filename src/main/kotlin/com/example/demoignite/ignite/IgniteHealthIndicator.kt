package com.example.demoignite.ignite

import com.example.demoignite.properties.IgniteProperties
import org.apache.ignite.client.IgniteClient
import org.springframework.boot.actuate.health.Health
import org.springframework.boot.actuate.health.ReactiveHealthIndicator
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

private val HEALTH_UP = Health.up().build()
private val HEALTH_DOWN = Health.down().build()

class IgniteHealthIndicator(
    private val properties: IgniteProperties,
    private val igniteClient: IgniteClient,
) : ReactiveHealthIndicator {
    override fun health(): Mono<Health> = igniteClient.getOrCreateCacheAsync<String, String>(properties.cacheName)
        .toCompletableFuture()
        .toMono()
        .map { HEALTH_UP }
        .onErrorReturn(HEALTH_DOWN)
}