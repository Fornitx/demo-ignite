package com.example.demoignite.properties

import com.example.demoignite.base.AbstractContextTest
import com.example.demoignite.ignite.DisabledIgniteHealthIndicator
import com.example.demoignite.ignite.IgniteHealthIndicator
import org.apache.ignite.client.IgniteClient
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.context.properties.bind.validation.BindValidationException

class DemoPropertiesTest : AbstractContextTest() {
    @Test
    fun success_allEnabled() = contextRunner().runLogging { context ->
        assertThat(context).hasNotFailed()
            .hasSingleBean(IgniteClient::class.java)
            .hasSingleBean(IgniteHealthIndicator::class.java)
    }

    @Test
    fun success_disableIgnite() = contextRunner()
        .withPropertyValues("$PREFIX.ignite.enabled=false")
        .runLogging { context ->
            assertThat(context).hasNotFailed()
                .doesNotHaveBean(IgniteClient::class.java)
                .hasSingleBean(DisabledIgniteHealthIndicator::class.java)
        }

    @Test
    fun success_disableHealth() = contextRunner()
        .withPropertyValues("$PREFIX.ignite.healthEnabled=false")
        .runLogging { context ->
            // TODO fail
            assertThat(context).hasNotFailed()
                .hasSingleBean(IgniteClient::class.java)
                .doesNotHaveBean(IgniteHealthIndicator::class.java)
                .doesNotHaveBean(DisabledIgniteHealthIndicator::class.java)
        }

    @Test
    fun success_disableAll() = contextRunner()
        .withPropertyValues("$PREFIX.ignite.enabled=false", "$PREFIX.ignite.healthEnabled=false")
        .runLogging { context ->
            // TODO fail
            assertThat(context).hasNotFailed()
                .doesNotHaveBean(IgniteClient::class.java)
                .doesNotHaveBean(IgniteHealthIndicator::class.java)
                .doesNotHaveBean(DisabledIgniteHealthIndicator::class.java)
        }

    @Test
    fun error_emptyCacheName() = contextRunner()
        .withPropertyValues("$PREFIX.ignite.cacheName= ")
        .runLogging { context ->
            assertThat(context).hasFailed()
                .failure.hasRootCauseInstanceOf(BindValidationException::class.java)
        }
}
