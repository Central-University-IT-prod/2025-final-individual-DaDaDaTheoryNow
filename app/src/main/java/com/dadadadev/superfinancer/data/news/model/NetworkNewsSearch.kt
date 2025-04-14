package com.dadadadev.superfinancer.data.news.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkSearchedNewsMedia(
    val url: String
)

@Serializable
data class NetworkSearchedNewsArticle(
    @SerialName("web_url") val url: String,
    val abstract: String,
    val source: String? = null,
    @SerialName("pub_date") val publishedDate: String?,
    val multimedia: List<NetworkSearchedNewsMedia> = emptyList()
) {
    fun getImageUrl(): String? {
        return multimedia.lastOrNull()?.let {
             "https://static01.nyt.com/${it.url}"
        }
    }
}

@Serializable
data class NewsDocsResponse(
    @SerialName("docs")
    val articles: List<NetworkSearchedNewsArticle>
)

@Serializable
data class NetworkSearchNews(
    @SerialName("response") val docs: NewsDocsResponse
)