package com.example.demoignite.ignite

import org.apache.ignite.IgniteClientSpringBean
import org.springframework.boot.actuate.health.Health
import org.springframework.boot.actuate.health.ReactiveHealthIndicator
import reactor.core.publisher.Mono

class IgniteHealthIndicator(
    private val cacheName: String,
    private val igniteClient: IgniteClientSpringBean,
) : ReactiveHealthIndicator {

    private val healthUnknown = Health.unknown().withCacheName(cacheName).build()
    private val healthUp = Health.up().withCacheName(cacheName).build()
    private val healthDown = Health.down().withCacheName(cacheName).build()

    override fun health(): Mono<Health> {
        return if (igniteClient.isRunning) {
            Mono.fromCompletionStage {
                igniteClient.getOrCreateCacheAsync<Any, Any>(cacheName)
            }.thenReturn(healthUp).onErrorReturn(healthDown)
        } else {
            Mono.just(healthUnknown)
        }
    }

}

class DisabledIgniteHealthIndicator(cacheName: String) : ReactiveHealthIndicator {
    private val healthUnknown = Health.unknown().withCacheName(cacheName).build()

    override fun health(): Mono<Health> = Mono.just(healthUnknown)
}

private fun Health.Builder.withCacheName(name: String): Health.Builder = this.withDetail("cacheName", name)
