package com.dadadadev.superfinancer.data.news

import com.dadadadev.result.ResponseResult
import com.dadadadev.result.NetworkError
import com.dadadadev.superfinancer.data.news.model.NetworkNews
import com.dadadadev.superfinancer.data.news.model.NetworkSearchNews

interface NewsDataSource {
    suspend fun getPopularNews(): ResponseResult<NetworkNews, NetworkError>
    suspend fun searchNews(query: String): ResponseResult<NetworkSearchNews, NetworkError>
}