package com.dadadadev.superfinancer.data.news.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkNewsMediaMetadata(
    val url: String,
    val format: String,
    val height: Int,
    val width: Int
)

@Serializable
data class NetworkNewsMedia(
    val type: String,
    val subtype: String,
    val caption: String,
    val copyright: String,
    @SerialName("media-metadata") val mediaMetadata: List<NetworkNewsMediaMetadata>
)

@Serializable
data class NetworkNewsArticle(
    val url: String,
    val title: String,
    val abstract: String,
    val source: String,
    @SerialName("published_date") val publishedDate: String,
    val media: List<NetworkNewsMedia> = emptyList()
) {
    fun getImageUrl(): String? {
        return media.firstOrNull()?.mediaMetadata?.lastOrNull()?.url
    }
}

@Serializable
data class NetworkNews(
    @SerialName("results") val articles: List<NetworkNewsArticle>
)