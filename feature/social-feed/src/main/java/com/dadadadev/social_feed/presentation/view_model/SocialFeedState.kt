package com.dadadadev.social_feed.presentation.view_model

import com.dadadadev.social_feed.domain.model.SocialFeedPost

data class SocialFeedState(
    val isLoading: Boolean = true,
    val posts: List<SocialFeedPost> = listOf(),
)