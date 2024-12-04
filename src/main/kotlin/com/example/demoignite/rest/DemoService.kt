package com.example.demoignite.rest

import com.example.demoignite.ignite.IgniteService
import org.springframework.stereotype.Service

@Service
class DemoService(private val igniteService: IgniteService) {
    suspend fun call(): String = "123"

    suspend fun getAsync(string: String): String? = igniteService.getAsync(string)
    suspend fun putAsync(key: String, value: String) = igniteService.putAsync(key, value)
    suspend fun removeAsync(string: String) = igniteService.removeAsync(string)
}
