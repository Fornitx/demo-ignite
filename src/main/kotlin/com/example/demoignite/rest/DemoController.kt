package com.example.demoignite.rest

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class DemoController(private val service: DemoService) {
    @GetMapping("/foo")
    suspend fun call(): String {
        return service.call()
    }
}
