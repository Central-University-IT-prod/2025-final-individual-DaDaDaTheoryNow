package com.dadadadev.superfinancer.domain.usecases.news

import com.dadadadev.result.ResponseResult
import com.dadadadev.result.NetworkError
import com.dadadadev.superfinancer.data.news.NewsDataSource
import com.dadadadev.NewsArticle

class GetPopularNewsUseCase(
    private val newsDataSource: NewsDataSource
) {
    suspend operator fun invoke(): ResponseResult<List<NewsArticle>, NetworkError> {
        return when (val result = newsDataSource.getPopularNews()) {
            is ResponseResult.Success -> ResponseResult.Success(
                result.data.articles.map { item ->
                    NewsArticle(
                        url = item.url,
                        title = item.title,
                        abstract = item.abstract,
                        source = item.source,
                        publishedDate = item.publishedDate,
                        image = item.getImageUrl()
                    )
                }
            )
            is ResponseResult.Error -> ResponseResult.Error(result.error)
        }
    }
}