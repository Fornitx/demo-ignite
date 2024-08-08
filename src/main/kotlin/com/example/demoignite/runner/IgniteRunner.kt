package com.example.demoignite.runner

import com.example.demoignite.ignite.IgniteService
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

private val log = KotlinLogging.logger {}

@Component
class IgniteRunner(
    private val igniteService: IgniteService
) {
    @Scheduled(cron = "*/2 * * * * *")
    fun run() {
        log.info("igniteService.get = '{}'", igniteService.get("abc"))
    }
}
