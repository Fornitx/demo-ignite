package com.example.demoignite

import com.example.demoignite.base.AbstractIgniteTest
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import java.util.concurrent.TimeUnit

@Disabled
@SpringBootTest
class IgniteLongRunningTest : AbstractIgniteTest() {
    @Test
    fun test() {
        TimeUnit.HOURS.sleep(1)
    }
}