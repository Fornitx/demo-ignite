package com.example.demoignite.util

import io.github.oshai.kotlinlogging.KotlinLogging
import org.testcontainers.utility.TestcontainersConfiguration

private val log = KotlinLogging.logger {}

object TestcontainersHelper {
    private val testcontainersConfiguration = TestcontainersConfiguration.getInstance()

    val IGNITE_CONTAINER_IMAGE = testcontainersConfiguration.getEnvVarOrProperty("ignite.container.image", null)
}
