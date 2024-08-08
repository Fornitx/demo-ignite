package com.example.demoignite.base

import io.github.oshai.kotlinlogging.KotlinLogging
import kotlin.reflect.jvm.jvmName

abstract class AbstractTest {
    protected val log = KotlinLogging.logger(this::class.jvmName)
}
