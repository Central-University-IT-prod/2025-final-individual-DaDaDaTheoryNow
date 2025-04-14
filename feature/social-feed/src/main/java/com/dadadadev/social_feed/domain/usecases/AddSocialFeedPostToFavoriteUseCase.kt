package com.dadadadev.social_feed.domain.usecases

import com.dadadadev.social_feed.data.SocialFeedLocalDataSource
import com.dadadadev.social_feed.data.model.SocialFeedPostDto
import com.dadadadev.social_feed.domain.model.SocialFeedPost

class AddSocialFeedPostToFavoriteUseCase(
    private val socialFeedLocalDataSource: SocialFeedLocalDataSource,
) {
    suspend operator fun invoke(post: SocialFeedPost) {
        socialFeedLocalDataSource.saveFavoritePost(
            SocialFeedPostDto(
                id = post.id,
                tags = post.tags,
                webUrl = post.webUrl,
                title = post.title,
                abstract = post.abstract,
                imageUrl = post.imageUrl,
                userDescription = post.userDescription,
                localImagesUris = post.localImagesUris,
                timestamp = post.timestamp
            )
        )
    }
}