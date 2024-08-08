package com.example.demoignite.runner

import com.example.demoignite.ignite.IgniteService
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Component

private val log = KotlinLogging.logger {}

@Component
class IgniteRunner(
    private val igniteService: IgniteService
) {
//    @EventListener(ApplicationReadyEvent::class)
//    fun run() {
//        Thread.ofPlatform().start {
//            while (true) {
//                repeat(3) {
//                    val key1 = MsgKey("123", UUID.randomUUID())
//                    val key2 = MsgKey("234", UUID.randomUUID())
//                    val key3 = MsgKey("345", UUID.randomUUID())
//
//                    log.info { cacheService.get(key1) }
//                    log.info { cacheService.get(key2) }
//                    log.info { cacheService.get(key3) }
//
//                    cacheService.putAsync(key1, MsgValue("", mapOf()))
//                    cacheService.putAsync(key2, MsgValue("", mapOf()))
//                    cacheService.putAsync(key3, MsgValue("", mapOf()))
//
//                    TimeUnit.SECONDS.sleep(1)
//
//                    log.info { cacheService.get(key1) }
//                    log.info { cacheService.get(key2) }
//                    log.info { cacheService.get(key3) }
//                }
//                TimeUnit.SECONDS.sleep(2)
//            }
//        }
//    }
}
