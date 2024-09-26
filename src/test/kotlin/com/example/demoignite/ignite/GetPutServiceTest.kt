package com.example.demoignite.ignite

import com.example.demoignite.base.AbstractIgniteTest
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class GetPutServiceTest : AbstractIgniteTest() {
    @Test
    fun test() = runTest {
        var result = igniteService.getAsync("abc")
        log.info { "result: $result" }

        igniteService.putAsync("abc", "123")

        result = igniteService.getAsync("abc")
        log.info { "result: $result" }

        igniteService.removeAsync("abc")

        result = igniteService.getAsync("abc")
        log.info { "result: $result" }
    }
}
