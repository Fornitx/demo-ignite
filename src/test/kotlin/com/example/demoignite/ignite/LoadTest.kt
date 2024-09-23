package com.example.demoignite.ignite

import com.example.demoignite.base.AbstractIgniteTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
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

private const val count = 100

@SpringBootTest
class LoadTest : AbstractIgniteTest() {
    @Autowired
    private lateinit var client: IgniteClient

    @Test
    fun test() = runTest {
        val cache = client.getOrCreateCacheAsync<Int, String>("loadTestCacheName").await()

        withContext(Dispatchers.IO) {
            repeat(count) { n ->
                async {
                    cache.putAsync(n, RandomStringUtils.randomAlphanumeric(5)).await()
                }
            }
        }

        repeat(count) { n ->
            assertNotNull(cache.get(n))
        }


        withContext(Dispatchers.IO) {
            repeat(count) { n ->
                async {
                    cache.removeAsync(n).await()
                }
            }
        }

        repeat(count) { n ->
            assertNull(cache.get(n))
        }
    }
}
