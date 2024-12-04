package com.example.demoignite.rest

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class DemoController(private val service: DemoService) {
    @GetMapping("/foo")
    suspend fun call(): String = service.call()

    @PostMapping("/getAsync")
    suspend fun getAsync(@RequestBody key: String): String? = service.getAsync(key)

    @PostMapping("/putAsync")
    suspend fun putAsync(@RequestBody pair: Pair<String, String>) = service.putAsync(pair.first, pair.second)

    @PostMapping("/removeAsync")
    suspend fun removeAsync(@RequestBody key: String) = service.removeAsync(key)
}
