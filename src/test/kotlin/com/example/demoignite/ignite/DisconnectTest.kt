package com.example.demoignite.ignite

import io.github.oshai.kotlinlogging.KotlinLogging
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody
import java.util.concurrent.TimeUnit

private val log = KotlinLogging.logger {}

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("local")
class DisconnectTest {
    @Autowired
    private lateinit var client: WebTestClient

    @Test
    fun test() {
        val key = "KEY"

        repeat(Int.MAX_VALUE) { i ->
            client.post()
                .uri("/putAsync")
                .bodyValue(key to i.toString())
                .exchange()
                .expectStatus()
                .is2xxSuccessful

            val responseBody = client.post()
                .uri("/getAsync")
                .bodyValue(key)
                .exchange()
                .expectStatus()
                .is2xxSuccessful
                .expectBody<String>()
                .returnResult()
                .responseBody

            log.info { "call $i - response $responseBody" }
//            assertEquals(i.toString(), responseBody)

            TimeUnit.SECONDS.sleep(1)
        }
    }
}
