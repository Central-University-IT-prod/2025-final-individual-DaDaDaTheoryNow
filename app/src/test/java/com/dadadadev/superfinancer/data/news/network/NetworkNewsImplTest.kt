package com.dadadadev.superfinancer.data.news.network

import com.dadadadev.result.ResponseResult
import com.dadadadev.superfinancer.core.http_client.HttpClientFactory
import com.dadadadev.superfinancer.data.news.NewsDataSource
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.get


class NetworkNewsImplTest : KoinTest {
    private val testModule = module {
        single { HttpClientFactory.create(null) }
        single<NewsDataSource> { NetworkNewsImpl(get()) }
    }

    @Before
    fun setup() {
        startKoin { modules(testModule) }
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `test popular news request`() = runBlocking {
        val result = get<NewsDataSource>().getPopularNews()
        assertTrue(result is ResponseResult.Success)
    }

    @Test
    fun `test search news request`() = runBlocking {
        val firstResult = get<NewsDataSource>().searchNews("Russia")
        val secondResult = get<NewsDataSource>().searchNews("China")
        assertTrue(firstResult is ResponseResult.Success && secondResult is ResponseResult.Success)
    }
}