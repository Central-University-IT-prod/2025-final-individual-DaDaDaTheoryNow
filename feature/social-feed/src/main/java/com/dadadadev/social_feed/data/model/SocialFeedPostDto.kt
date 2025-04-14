package com.dadadadev.social_feed.data.model

import androidx.annotation.Keep
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.Exclude
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class SocialFeedPostDto(
    @DocumentId val id: String = "",
    val webUrl: String? = null,
    val title: String = "",
    val abstract: String = "",
    val imageUrl: String = "",
    val userDescription: String = "",
    val tags: List<String> = listOf(),
    val timestamp: Long = System.currentTimeMillis(),
    @get:Exclude val localImagesUris: List<String> = listOf(),
    @get:Exclude val isFavorite: Boolean = false
)