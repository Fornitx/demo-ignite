package com.example.demoignite

import com.example.demoignite.properties.DemoProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableConfigurationProperties(DemoProperties::class)
@EnableScheduling
class DemoIgniteApplication

fun main(args: Array<String>) {
    runApplication<DemoIgniteApplication>(*args)
}
