package com.example.demoignite.util

import io.github.oshai.kotlinlogging.KotlinLogging
import org.testcontainers.utility.TestcontainersConfiguration

private val log = KotlinLogging.logger {}

object TestcontainersHelper {
    private val testcontainersConfiguration = TestcontainersConfiguration.getInstance()

    val IGNITE_CONTAINER by lazy {
        val dockerImageName = testcontainersConfiguration.getEnvVarOrProperty("ignite.container.image", null)
        val container = IgniteContainer(dockerImageName)
        container.start()

        System.setProperty("TC_IGNITE", container.port)

        log.info("Ignite container '{}' started on port {}", dockerImageName, container.port)

        container
    }
}