package com.dadadadev.superfinancer.domain.usecases.news

import com.dadadadev.result.ResponseResult
import com.dadadadev.result.NetworkError
import com.dadadadev.common.formatDate
import com.dadadadev.superfinancer.data.news.NewsDataSource
import com.dadadadev.NewsArticle

class SearchNewsUseCase(
    private val newsDataSource: NewsDataSource
) {
    suspend operator fun invoke(query: String): ResponseResult<List<NewsArticle>, NetworkError> {
        return when (val result = newsDataSource.searchNews(query)) {
            is ResponseResult.Success -> ResponseResult.Success(
                result.data.docs.articles.map { item ->
                    NewsArticle(
                        url = item.url,
                        abstract = item.abstract,
                        source = item.source ?: "Unknown",
                        image = item.getImageUrl(),
                        publishedDate = item.publishedDate?.let {
                            formatDate(it)
                        },
                        title = null
                    )
                }
            )
            is ResponseResult.Error -> ResponseResult.Error(result.error)
        }
    }
}