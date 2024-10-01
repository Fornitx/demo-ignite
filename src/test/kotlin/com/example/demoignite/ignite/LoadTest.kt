package com.example.demoignite.ignite

import com.example.demoignite.base.AbstractIgniteTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.future.await
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import org.apache.commons.lang3.RandomStringUtils
import org.apache.ignite.client.IgniteClient
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

private const val count = 100

@SpringBootTest
class LoadTest : AbstractIgniteTest() {
    @Autowired
    private lateinit var client: IgniteClient

    @Test
    fun test() = runTest {
        val cache = client.getOrCreateCacheAsync<Int, String>("loadTestCacheName").await()

        withContext(Dispatchers.IO) {
            foreach { n ->
                log.info { "assertNullBefore - $n" }
                assertNull(cache.getAsync(n).await())
            }

            foreach { n ->
                log.info { "putAsync - $n" }
                cache.putAsync(n, RandomStringUtils.randomAlphanumeric(5)).await()
            }

            foreach { n ->
                log.info { "assertNotNull - $n" }
                assertNotNull(cache.getAsync(n).await())
            }

            foreach { n ->
                log.info { "removeAsync - $n" }
                assertTrue(cache.removeAsync(n).await())
            }

            foreach { n ->
                log.info { "assertNullAfter - $n" }
                assertNull(cache.getAsync(n).await())
            }
        }
    }

    suspend fun foreach(block: suspend (Int) -> Unit) {
        coroutineScope {
            repeat(count) { n ->
                async {
                    block(n)
                }
            }
        }
    }
}
