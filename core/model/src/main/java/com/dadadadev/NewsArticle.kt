package com.dadadadev

import kotlinx.serialization.Serializable

@Serializable
data class NewsArticle(
    val url: String,
    val title: String?,
    val abstract: String,
    val source: String,
    val publishedDate: String?,
    val image: String?,
)