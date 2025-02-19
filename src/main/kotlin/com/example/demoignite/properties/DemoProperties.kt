package com.example.demoignite.properties

import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import org.apache.ignite.configuration.ClientConfiguration
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.validation.annotation.Validated

const val PREFIX = "demo"

@ConfigurationProperties(prefix = PREFIX, ignoreUnknownFields = false)
@Validated
data class DemoProperties(
    @field:Valid
    val ignite: IgniteProperties,
)

data class IgniteProperties(
    val enabled: Boolean,
    val clientConfiguration: ClientConfiguration,
    @field:NotBlank
    val cacheName: String,
    val healthEnabled: Boolean,
)
