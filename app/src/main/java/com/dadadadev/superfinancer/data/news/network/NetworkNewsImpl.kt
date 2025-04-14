package com.dadadadev.superfinancer.data.news.network

import com.dadadadev.result.ResponseResult
import com.dadadadev.superfinancer.core.Constants
import com.dadadadev.superfinancer.core.http_client.safeCall
import com.dadadadev.result.NetworkError
import com.dadadadev.superfinancer.data.news.NewsDataSource
import com.dadadadev.superfinancer.data.news.model.NetworkNews
import com.dadadadev.superfinancer.data.news.model.NetworkSearchNews
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class NetworkNewsImpl(
    private val httpClient: HttpClient,
) : NewsDataSource {
    override suspend fun getPopularNews(): ResponseResult<NetworkNews, NetworkError> {
        return safeCall<NetworkNews> {
            httpClient.get(
                "${Constants.BASE_NEWS_API}/viewed/1.json"
            ) {
                parameter("api-key", Constants.NEWS_API_KEY)
            }
        }
    }

    override suspend fun searchNews(query: String): ResponseResult<NetworkSearchNews, NetworkError> {
        return safeCall<NetworkSearchNews> {
            httpClient.get(
                "${Constants.SEARCH_NEWS_API}/articlesearch.json"
            ) {
                parameter("api-key", Constants.NEWS_API_KEY)
                parameter("q", query)
            }
        }
    }
}