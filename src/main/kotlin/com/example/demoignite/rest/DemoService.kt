package com.example.demoignite.rest

import org.springframework.stereotype.Service

@Service
class DemoService {
    suspend fun call(): String {
        return "123"
    }
}
