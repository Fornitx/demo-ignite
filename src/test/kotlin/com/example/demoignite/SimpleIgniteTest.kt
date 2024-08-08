package com.example.demoignite

import io.github.oshai.kotlinlogging.KotlinLogging
import org.apache.ignite.Ignition
import org.apache.ignite.configuration.ClientConfiguration
import org.junit.jupiter.api.Test

private val log = KotlinLogging.logger {}

class SimpleIgniteTest {
    @Test
    fun test() {
        log.info("START")
        try {
            val cfg = ClientConfiguration()
                .setAddresses("127.0.0.1:32771")
                .setTimeout(2000)
                .setClusterDiscoveryEnabled(false)

            Ignition.startClient(cfg).use { client ->
                println(client.cacheNames())
            }
        } finally {
            log.info("END")
        }
    }
}
