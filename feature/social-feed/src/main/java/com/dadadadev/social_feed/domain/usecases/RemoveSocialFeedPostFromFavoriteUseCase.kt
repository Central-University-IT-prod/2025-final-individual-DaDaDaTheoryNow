package com.dadadadev.social_feed.domain.usecases

import com.dadadadev.social_feed.data.SocialFeedLocalDataSource

class RemoveSocialFeedPostFromFavoriteUseCase(
    private val socialFeedLocalDataSource: SocialFeedLocalDataSource
) {
    suspend operator fun invoke(postId: String) {
        socialFeedLocalDataSource.removeFavoritePostWithId(postId)
    }
}