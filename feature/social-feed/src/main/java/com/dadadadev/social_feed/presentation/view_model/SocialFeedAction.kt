package com.dadadadev.social_feed.presentation.view_model

import com.dadadadev.NewsArticle
import com.dadadadev.social_feed.domain.model.SocialFeedPost

sealed interface SocialFeedAction {
    data class ShareArticle(
        val article: NewsArticle,
        val userDescription: String,
        val listOfUris: List<String>,
        val listOfTags: List<String>,
    ) : SocialFeedAction

    data class TogglePostToFavorite(val post: SocialFeedPost) : SocialFeedAction
}