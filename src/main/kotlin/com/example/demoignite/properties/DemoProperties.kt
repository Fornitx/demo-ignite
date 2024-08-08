package com.example.demoignite.properties

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import org.springframework.boot.context.properties.ConfigurationProperties

const val PREFIX = "demo"

@ConfigurationProperties(prefix = PREFIX, ignoreUnknownFields = false)
data class DemoProperties(
    val ignite: IgniteProperties,
)

data class IgniteProperties(
    @field:NotEmpty
    val addresses: List<@NotBlank String>,
    @field:NotBlank
    val cacheName: String,
    val healthEnabled: Boolean,
)