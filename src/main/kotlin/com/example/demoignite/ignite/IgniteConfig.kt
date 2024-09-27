package com.example.demoignite.ignite

import com.example.demoignite.properties.DemoProperties
import com.example.demoignite.properties.PREFIX
import io.github.oshai.kotlinlogging.KotlinLogging
import org.apache.ignite.IgniteClientSpringBean
import org.apache.ignite.client.IgniteClient
import org.apache.ignite.configuration.ClientConfiguration
import org.springframework.boot.actuate.health.ReactiveHealthIndicator
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

private val log = KotlinLogging.logger {}

@Configuration
class IgniteConfig(private val properties: DemoProperties) {
    @ConditionalOnProperty("$PREFIX.ignite.enabled", havingValue = "true", matchIfMissing = true)
    @Bean
    fun igniteClient(): IgniteClientSpringBean {
        val addresses = properties.ignite.addresses
        val cfg = ClientConfiguration()
            .setAddresses(*addresses.toTypedArray())
            .setTimeout(2000)
            .setClusterDiscoveryEnabled(false)
        log.info("Ignite client created for {}", addresses)
        return IgniteClientSpringBean().setClientConfiguration(cfg)
    }

    @ConditionalOnProperty("$PREFIX.ignite.enabled", havingValue = "true", matchIfMissing = true)
    @Bean
    fun igniteService(igniteClient: IgniteClient): IgniteService =
        IgniteService(properties.ignite, igniteClient)

    @ConditionalOnProperty("$PREFIX.ignite.healthEnabled", havingValue = "true", matchIfMissing = true)
    @Bean
    fun igniteHealthIndicator(igniteClient: IgniteClientSpringBean?): ReactiveHealthIndicator {
        return if (igniteClient == null) {
            DisabledIgniteHealthIndicator(properties.ignite.cacheName)
        } else {
            IgniteHealthIndicator(properties.ignite.cacheName, igniteClient)
        }
    }
}

