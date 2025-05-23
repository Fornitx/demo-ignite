package com.example.demoignite.ignite

import com.example.demoignite.properties.DemoProperties
import com.example.demoignite.properties.PREFIX
import io.github.oshai.kotlinlogging.KotlinLogging
import org.apache.ignite.IgniteClientSpringBean
import org.apache.ignite.client.IgniteClient
import org.apache.ignite.client.events.ConnectionClosedEvent
import org.apache.ignite.client.events.ConnectionEventListener
import org.apache.ignite.client.events.HandshakeFailEvent
import org.apache.ignite.client.events.HandshakeStartEvent
import org.apache.ignite.client.events.HandshakeSuccessEvent
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
        val cfg = properties.ignite.clientConfiguration
            .setEventListeners(object : ConnectionEventListener {
                override fun onHandshakeStart(event: HandshakeStartEvent?) {
                    log.info { "onHandshakeStart $event" }
                    super.onHandshakeStart(event)
                }

                override fun onHandshakeSuccess(event: HandshakeSuccessEvent?) {
                    log.info { "onHandshakeSuccess $event" }
                    super.onHandshakeSuccess(event)
                }

                override fun onHandshakeFail(event: HandshakeFailEvent?) {
                    log.info { "onHandshakeFail $event" }
                    super.onHandshakeFail(event)
                }

                override fun onConnectionClosed(event: ConnectionClosedEvent?) {
                    log.info { "onConnectionClosed $event" }
                    super.onConnectionClosed(event)
                }
            })
        log.info { "Ignite client created for ${cfg.addresses}" }
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

