package com.example.demoignite.base

import com.example.demoignite.DemoIgniteApplication
import com.example.demoignite.util.TestcontainersHelper
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer
import org.springframework.boot.test.context.assertj.AssertableReactiveWebApplicationContext
import org.springframework.boot.test.context.runner.ContextConsumer
import org.springframework.boot.test.context.runner.ReactiveWebApplicationContextRunner
import kotlin.collections.isNotEmpty
import kotlin.collections.joinToString
import kotlin.jvm.java

abstract class AbstractContextTest : AbstractTest() {
    companion object {
        protected val igniteContainer = TestcontainersHelper.IGNITE_CONTAINER
    }

    protected fun contextRunner(): ReactiveWebApplicationContextRunner {
        return ReactiveWebApplicationContextRunner()
            .withInitializer(ConfigDataApplicationContextInitializer())
            .withUserConfiguration(DemoIgniteApplication::class.java)
            .withSystemProperties("TC_IGNITE=${igniteContainer.port}")
    }

    protected fun ReactiveWebApplicationContextRunner.withProfiles(vararg profiles: String): ReactiveWebApplicationContextRunner {
        require(profiles.isNotEmpty())
        return this.withPropertyValues("spring.profiles.active=${profiles.joinToString()}")
    }

    protected fun ReactiveWebApplicationContextRunner.runLogging(
        contextConsumer: ContextConsumer<AssertableReactiveWebApplicationContext>
    ): Unit = this.run { context ->
        if (context.startupFailure != null) {
            log.error(context.startupFailure) { }
        }
        contextConsumer.accept(context)
    }.let { }
}
