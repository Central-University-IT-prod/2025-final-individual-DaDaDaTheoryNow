package com.dadadadev.social_feed.domain.model

data class SocialFeedPost(
    val id: String = "",
    val webUrl: String? = null,
    val title: String,
    val abstract: String,
    val imageUrl: String,
    val userDescription: String,
    val timestamp: Long = System.currentTimeMillis(),
    val localImagesUris: List<String> = listOf(),
    val tags: List<String> = listOf(),
    val isFavorite: Boolean = false,
)