package com.example.demoignite.ignite

import com.example.demoignite.properties.DemoProperties
import io.github.oshai.kotlinlogging.KotlinLogging
import org.apache.ignite.IgniteClientSpringBean
import org.apache.ignite.client.IgniteClient
import org.apache.ignite.configuration.ClientConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

private val log = KotlinLogging.logger {}

@Configuration
class IgniteConfig(private val properties: DemoProperties) {
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

    @Bean
    fun igniteHealthIndicator(igniteClient: IgniteClientSpringBean): IgniteHealthIndicator =
        IgniteHealthIndicator(properties.ignite, igniteClient)

    @Bean
    fun igniteService(igniteClient: IgniteClient): IgniteService =
        IgniteService(properties.ignite, igniteClient)
}
