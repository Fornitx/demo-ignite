package com.example.demoignite.ignite

import com.example.demoignite.properties.IgniteProperties
import org.apache.ignite.client.IgniteClient
import org.springframework.boot.actuate.health.Health
import org.springframework.boot.actuate.health.HealthIndicator

private val HEALTH_UP = Health.up().build()
private val HEALTH_DOWN = Health.down().build()

class IgniteHealthIndicator(
    private val properties: IgniteProperties,
    private val igniteClient: IgniteClient,
) : HealthIndicator {
    override fun health(): Health {
        try {
            igniteClient.getOrCreateCache<String, String>(properties.cacheName)
        } catch (ex: Exception) {
            return HEALTH_DOWN
        }

        return HEALTH_UP
    }
}