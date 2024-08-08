package com.example.demoignite.util

import com.github.dockerjava.api.model.ExposedPort
import com.github.dockerjava.api.model.PortBinding
import com.github.dockerjava.api.model.Ports
import org.testcontainers.containers.GenericContainer

class IgniteContainer(dockerImageName: String) : GenericContainer<IgniteContainer>(dockerImageName) {
    init {
        withReuse(true)
//        portBindings = listOf("0:10800")
        withCreateContainerCmdModifier { createContainerCmd ->
            createContainerCmd.hostConfig!!.withPortBindings(
                PortBinding(
                    Ports.Binding.bindPort(0), ExposedPort(10800)
                )
            )
        }
    }

    val port: String
        get() {
            return containerInfo.networkSettings.ports.bindings[ExposedPort.tcp(10800)]!![0].hostPortSpec
        }
}
