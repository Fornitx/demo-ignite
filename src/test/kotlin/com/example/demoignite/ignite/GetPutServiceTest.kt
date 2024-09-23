package com.example.demoignite.ignite

import com.example.demoignite.base.AbstractIgniteTest
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class GetPutServiceTest : AbstractIgniteTest() {
    @Autowired
    private lateinit var service: IgniteService

    @Test
    fun test() = runTest {
        var result = service.getAsync("abc")
        log.info { "result: $result" }

        service.putAsync("abc", "123")

        result = service.getAsync("abc")
        log.info { "result: $result" }

        service.removeAsync("abc")

        result = service.getAsync("abc")
        log.info { "result: $result" }
    }
}
