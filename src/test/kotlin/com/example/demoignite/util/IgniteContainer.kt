package com.example.demoignite.util

import com.github.dockerjava.api.model.ExposedPort
import com.github.dockerjava.api.model.PortBinding
import com.github.dockerjava.api.model.Ports
import io.github.oshai.kotlinlogging.KotlinLogging
import org.testcontainers.containers.GenericContainer

private val log = KotlinLogging.logger {}

object IgniteContainer : GenericContainer<IgniteContainer>(TestcontainersHelper.IGNITE_CONTAINER_IMAGE) {
    init {
        withReuse(true)
        withCreateContainerCmdModifier { createContainerCmd ->
            createContainerCmd.hostConfig!!.withPortBindings(
                PortBinding(
                    Ports.Binding.bindPort(0), ExposedPort(10800)
                ),
            )
        }
    }

    val port: String
        get() {
            return containerInfo.networkSettings.ports.bindings[ExposedPort.tcp(10800)]!![0].hostPortSpec
        }

    override fun start() {
        super.start()

        System.setProperty("TC_IGNITE", port)

        log.info { "Ignite container '$dockerImageName' started on port $port" }
    }
}
